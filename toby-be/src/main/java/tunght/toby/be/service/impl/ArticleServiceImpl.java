package tunght.toby.be.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tunght.toby.be.consts.CommonConst;
import tunght.toby.be.dto.ArticleDto;
import tunght.toby.be.dto.ProfileDto;
import tunght.toby.be.dto.model.ArticleQueryParam;
import tunght.toby.be.dto.model.FeedParams;
import tunght.toby.be.entity.ArticleEntity;
import tunght.toby.be.entity.ArticleTagRelationEntity;
import tunght.toby.be.entity.FavoriteEntity;
import tunght.toby.be.entity.FollowEntity;
import tunght.toby.be.repository.*;
import tunght.toby.be.service.ArticleService;
import tunght.toby.be.service.ProfileService;
import tunght.toby.common.entity.BaseEntity;
import tunght.toby.common.entity.UserEntity;
import tunght.toby.common.enums.ERole;
import tunght.toby.common.enums.EStatus;
import tunght.toby.common.exception.AppException;
import tunght.toby.common.exception.Error;
import tunght.toby.common.security.AuthUserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final FollowRepository followRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final TagRepository tagRepository;

    @Transactional
    @Override
    public ArticleDto createArticle(ArticleDto article, AuthUserDetails authUserDetails) {
        String slug = String.join("-", article.getTitle().split(" ")) + "-" + System.currentTimeMillis();
        UserEntity author = UserEntity.builder()
                .id(authUserDetails.getId())
                .build();

        ArticleEntity articleEntity = ArticleEntity.builder()
                .slug(slug)
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .isApproved(0)
                .author(author)
                .build();
        List<ArticleTagRelationEntity> tagList = new ArrayList<>();
        for (String tag : article.getTagList()) {
            tagList.add(ArticleTagRelationEntity.builder().article(articleEntity).tag(tag).build());
        }
        articleEntity.setTagList(tagList);

        articleEntity = articleRepository.save(articleEntity);
        return convertEntityToDto(articleEntity, false, 0L, authUserDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDto getArticle(String slug, AuthUserDetails authUserDetails) {
        ArticleEntity found = articleRepository.findBySlug(slug).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));
        List<FavoriteEntity> favorites = found.getFavoriteList();
        boolean favorited = false;
        if (authUserDetails != null) {
            favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getUser().getId().equals(authUserDetails.getId()));
        }
        int favoriteCount = favorites.size();
        return convertEntityToDto(found, favorited, (long) favoriteCount, authUserDetails);
    }

    @Transactional
    @Override
    public ArticleDto updateArticle(String slug, ArticleDto.Update article, AuthUserDetails authUserDetails) {
        ArticleEntity found = articleRepository.findBySlug(slug).filter(entity -> entity.getAuthor().getId().equals(authUserDetails.getId())).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));

        if (article.getTitle() != null) {
            String newSlug = String.join("-", article.getTitle().split(" ")) + "-" + LocalDateTime.now();
            found.setTitle(article.getTitle());
            found.setSlug(newSlug);
        }

        if (article.getDescription() != null) {
            found.setDescription(article.getDescription());
        }

        if (article.getBody() != null) {
            found.setBody(article.getBody());
        }

        if (article.getTagList() != null) {
            var tagList = found.getTagList();
            tagList.clear();
            for (String tag : article.getTagList()) {
                tagList.add(ArticleTagRelationEntity.builder().article(found).tag(tag).build());
            }
        }

        articleRepository.save(found);

        return getArticle(found.getSlug(), authUserDetails);
    }

    @Transactional
    @Override
    public void deleteArticle(String slug, AuthUserDetails authUserDetails) {
        var isAdmin = authUserDetails.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals(ERole.ROLE_ADMIN.name()));

        ArticleEntity found = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));
        var isAuthor = found.getAuthor().getId().equals(authUserDetails.getId());

        if (isAdmin || isAuthor) {
            articleRepository.delete(found);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ArticleDto> feedArticles(AuthUserDetails authUserDetails, FeedParams feedParams) {
        if (authUserDetails == null) {
            return new ArrayList<>();
        }
        List<Long> feedAuthorIds = followRepository.findByFollowerId(authUserDetails.getId()).stream().map(FollowEntity::getFollowee).map(BaseEntity::getId).collect(Collectors.toList());
        return articleRepository.findByAuthorIdInOrderByCreatedAtDesc(feedAuthorIds, 1, PageRequest.of(feedParams.getOffset(), feedParams.getLimit())).stream().map(entity -> {
            List<FavoriteEntity> favorites = entity.getFavoriteList();
            Boolean favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getUser().getId().equals(authUserDetails.getId()));
            int favoriteCount = favorites.size();
            return convertEntityToDto(entity, favorited, (long) favoriteCount, authUserDetails);
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ArticleDto favoriteArticle(String slug, AuthUserDetails authUserDetails) {
        ArticleEntity found = articleRepository.findBySlug(slug).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));

        favoriteRepository.findByArticleIdAndUserId(found.getId(), authUserDetails.getId())
                .ifPresent(favoriteEntity -> {
                    throw new AppException(Error.ALREADY_FAVORITED_ARTICLE);
                });

        FavoriteEntity favorite = FavoriteEntity.builder()
                .article(found)
                .user(UserEntity.builder().id(authUserDetails.getId()).build())
                .build();
        favoriteRepository.save(favorite);

        return getArticle(slug, authUserDetails);
    }

    @Transactional
    @Override
    public ArticleDto unfavoriteArticle(String slug, AuthUserDetails authUserDetails) {
        ArticleEntity found = articleRepository.findBySlug(slug).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));
        FavoriteEntity favorite = found.getFavoriteList().stream()
                .filter(favoriteEntity -> favoriteEntity.getArticle().getId().equals(found.getId())
                        && favoriteEntity.getUser().getId().equals(authUserDetails.getId())).findAny()
                .orElseThrow(() -> new AppException(Error.FAVORITE_NOT_FOUND));
        found.getFavoriteList().remove(favorite); // cascade REMOVE
        articleRepository.save(found);
        return getArticle(slug, authUserDetails);
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleDto.MultipleArticle listPublicArticle(ArticleQueryParam articleQueryParam, AuthUserDetails authUserDetails) {
        Pageable pageable = null;
        if (articleQueryParam.getOffset() != null && articleQueryParam.getLimit() != null) {
            pageable = PageRequest.of(articleQueryParam.getOffset(), articleQueryParam.getLimit());
        }

        Page<ArticleEntity> articlePageObject;
        if (articleQueryParam.getTag() != null) {
            articlePageObject = articleRepository.findByTag(articleQueryParam.getTag(), 1, pageable);
        } else if (articleQueryParam.getAuthor() != null) {
            articlePageObject = articleRepository.findByAuthorName(articleQueryParam.getAuthor(), 1, pageable);
        } else if (articleQueryParam.getFavorited() != null) {
            articlePageObject = articleRepository.findByFavoritedUsername(articleQueryParam.getFavorited(), 1, pageable);
        } else {
            articlePageObject = articleRepository.findListByPaging(1, pageable);
        }

        var dtos = articlePageObject.stream().map(article -> {
            List<FavoriteEntity> favorites = article.getFavoriteList();
            long favoriteCount = favorites.size();
            boolean favorited = false;
            if (authUserDetails != null) {
                favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getUser().getId().equals(authUserDetails.getId()));
            }
            return convertEntityToDto(article, favorited, favoriteCount, authUserDetails);
        }).collect(Collectors.toList());
        return ArticleDto.MultipleArticle
                .builder()
                .articles(dtos)
                .articlesCount((int) articlePageObject.getTotalElements())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleDto.MultipleArticle listUnapprovedArticle(ArticleQueryParam articleQueryParam, AuthUserDetails authUserDetails) {
        Pageable pageable = null;
        if (articleQueryParam.getOffset() != null && articleQueryParam.getLimit() != null) {
            pageable = PageRequest.of(articleQueryParam.getOffset(), articleQueryParam.getLimit());
        }

        Page<ArticleEntity> articlePageObject;
        if (articleQueryParam.getTag() != null) {
            articlePageObject = articleRepository.findByTag(articleQueryParam.getTag(), 0, pageable);
        } else if (articleQueryParam.getAuthor() != null) {
            var author = userRepository.findAllByUsernameAndStatus(articleQueryParam.getAuthor(), EStatus.ACTIVE).get(0);
            if (author == null) {
                throw new AppException(Error.USER_NOT_FOUND);
            }
            var isAdmin = authUserDetails.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals(ERole.ROLE_ADMIN.name()));
            var isAuthor = author.getId().equals(authUserDetails.getId());

            if (isAdmin || isAuthor) {
                articlePageObject = articleRepository.findByAuthorName(articleQueryParam.getAuthor(), 0, pageable);
            } else {
                return null;
            }
        } else {
            articlePageObject = articleRepository.findListByPaging(0, pageable);
        }

        var dtos = articlePageObject.stream().map(article -> {
            List<FavoriteEntity> favorites = article.getFavoriteList();
            long favoriteCount = favorites.size();
            boolean favorited = false;
            if (authUserDetails != null) {
                favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getUser().getId().equals(authUserDetails.getId()));
            }
            return convertEntityToDto(article, favorited, favoriteCount, authUserDetails);
        }).collect(Collectors.toList());
        return ArticleDto.MultipleArticle
                .builder()
                .articles(dtos)
                .articlesCount((int) articlePageObject.getTotalElements())
                .build();
    }

    @Override
    public void approveArticle(String slug) {
        ArticleEntity found = articleRepository.findBySlug(slug).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));
        found.setIsApproved(1);
        articleRepository.save(found);
    }

    @Override
    public void pinArticle(String slug) {
        ArticleEntity found = articleRepository.findBySlug(slug).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));
        var tagList = found.getTagList();
        tagList.add(ArticleTagRelationEntity.builder()
                .article(found)
                .tag(CommonConst.FEATURE_TAG)
                .build());
        articleRepository.save(found);
    }

    @Override
    public void unpinArticle(String slug) {
        ArticleEntity found = articleRepository.findBySlug(slug).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));
        var tagList = found.getTagList();
        var tagNeedToBeDeleted = tagRepository.findAllByTag(CommonConst.FEATURE_TAG);
        found.getTagList().removeAll(tagNeedToBeDeleted);
        articleRepository.save(found);
    }

    private ArticleDto convertEntityToDto(ArticleEntity entity, Boolean favorited, Long favoritesCount, AuthUserDetails authUserDetails) {
        ProfileDto author = profileService.getProfileByUserId(entity.getAuthor().getId(), authUserDetails);
        return ArticleDto.builder()
                .slug(entity.getSlug())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .body(entity.getBody())
                .author(author)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .favorited(favorited)
                .favoritesCount(favoritesCount)
                .tagList(entity.getTagList().stream().map(ArticleTagRelationEntity::getTag).collect(Collectors.toList()))
                .build();
    }
}
