package tunght.gr2.domain.article.servie;

import org.springframework.stereotype.Component;
import tunght.gr2.domain.article.dto.ArticleDto;
import tunght.gr2.domain.article.entity.ArticleEntity;
import tunght.gr2.domain.article.entity.CommentEntity;
import tunght.gr2.domain.article.entity.FavoriteEntity;
import tunght.gr2.domain.article.model.ArticleQueryParam;
import tunght.gr2.domain.article.model.FeedParams;
import tunght.gr2.domain.article.repository.ArticleRepository;
import tunght.gr2.domain.article.repository.CommentRepository;
import tunght.gr2.domain.article.repository.FavoriteRepository;
import tunght.gr2.domain.common.entity.BaseEntity;
import tunght.gr2.domain.profile.dto.ProfileDto;
import tunght.gr2.domain.profile.entity.FollowEntity;
import tunght.gr2.domain.profile.repository.FollowRepository;
import tunght.gr2.domain.profile.service.ProfileService;
import tunght.gr2.domain.tag.entity.ArticleTagRelationEntity;
import tunght.gr2.domain.user.entity.UserEntity;
import tunght.gr2.exception.AppException;
import tunght.gr2.exception.Error;
import tunght.gr2.security.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CommentRepository commentRepository;

    private final ProfileService profileService;

    @Transactional
    @Override
    public ArticleDto createArticle(ArticleDto article, AuthUserDetails authUserDetails) {
        String slug = String.join("-", article.getTitle().split(" ")) + "-" + LocalDateTime.now().toString();
        UserEntity author = UserEntity.builder()
                .id(authUserDetails.getId())
                .build();

        ArticleEntity articleEntity = ArticleEntity.builder()
                .slug(slug)
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .author(author)
                .build();
        List<ArticleTagRelationEntity> tagList = new ArrayList<>();
        for (String tag: article.getTagList()) {
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
            String newSlug = String.join("-", article.getTitle().split(" ")) + "-" + LocalDateTime.now().toString();
            found.setTitle(article.getTitle());
            found.setSlug(newSlug);
        }

        if (article.getDescription() != null) {
            found.setDescription(article.getDescription());
        }

        if (article.getBody() != null) {
            found.setBody(article.getBody());
        }

        articleRepository.save(found);

        return getArticle(found.getSlug(), authUserDetails);
    }

    @Transactional
    @Override
    public void deleteArticle(String slug, AuthUserDetails authUserDetails) {
        ArticleEntity found = articleRepository.findBySlug(slug).filter(entity -> entity.getAuthor().getId().equals(authUserDetails.getId())).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));
        List<CommentEntity> comments = commentRepository.findByArticleId(found.getId());
        articleRepository.delete(found);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ArticleDto> feedArticles(AuthUserDetails authUserDetails, FeedParams feedParams) {
        List<Long> feedAuthorIds = followRepository.findByFollowerId(authUserDetails.getId()).stream().map(FollowEntity::getFollowee).map(BaseEntity::getId).collect(Collectors.toList());
        return articleRepository.findByAuthorIdInOrderByCreatedAtDesc(feedAuthorIds, PageRequest.of(feedParams.getOffset(), feedParams.getLimit())).stream().map(entity -> {
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
                .ifPresent(favoriteEntity -> { throw new AppException(Error.ALREADY_FAVORITED_ARTICLE);});

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
    public List<ArticleDto> listArticle(ArticleQueryParam articleQueryParam, AuthUserDetails authUserDetails) {
        Pageable pageable = null;
        if (articleQueryParam.getOffset() != null) {
            pageable = PageRequest.of(articleQueryParam.getOffset(), articleQueryParam.getLimit());
        }

        List<ArticleEntity> articleEntities;
        if (articleQueryParam.getTag() != null) {
            articleEntities = articleRepository.findByTag(articleQueryParam.getTag(), pageable);
        } else if  (articleQueryParam.getAuthor() != null) {
            articleEntities = articleRepository.findByAuthorName(articleQueryParam.getAuthor(), pageable);
        } else if (articleQueryParam.getFavorited() != null) {
            articleEntities = articleRepository.findByFavoritedUsername(articleQueryParam.getFavorited(), pageable);
        } else {
            articleEntities = articleRepository.findListByPaging(pageable);
        }

        return convertToArticleList(articleEntities, authUserDetails);
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

    private List<ArticleDto> convertToArticleList(List<ArticleEntity> articleEntities, AuthUserDetails authUserDetails) {
        return articleEntities.stream().map(entity -> {
            List<FavoriteEntity> favorites = entity.getFavoriteList();
            long favoriteCount = favorites.size();
            boolean favorited = false;
            if (authUserDetails != null) {
                favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getUser().getId().equals(authUserDetails.getId()));
            }
            return convertEntityToDto(entity, favorited, favoriteCount, authUserDetails);
        }).collect(Collectors.toList());
    }
}
