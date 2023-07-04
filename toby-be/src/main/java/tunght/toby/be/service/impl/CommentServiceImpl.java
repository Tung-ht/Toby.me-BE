package tunght.toby.be.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tunght.toby.be.dto.CommentDto;
import tunght.toby.be.dto.ProfileDto;
import tunght.toby.be.entity.ArticleEntity;
import tunght.toby.be.entity.CommentEntity;
import tunght.toby.be.repository.ArticleRepository;
import tunght.toby.be.repository.CommentRepository;
import tunght.toby.be.service.CommentService;
import tunght.toby.be.service.ProfileService;
import tunght.toby.common.entity.BaseEntity;
import tunght.toby.common.entity.UserEntity;
import tunght.toby.common.enums.ERole;
import tunght.toby.common.exception.AppException;
import tunght.toby.common.exception.Error;
import tunght.toby.common.security.AuthUserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final ProfileService profileService;

    @Transactional
    @Override
    public CommentDto addCommentsToAnArticle(String slug, CommentDto comment, AuthUserDetails authUserDetails) {
        ArticleEntity articleEntity = articleRepository.findBySlug(slug).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));
        CommentEntity commentEntity = CommentEntity.builder()
                .body(comment.getBody())
                .author(UserEntity.builder()
                        .id(authUserDetails.getId())
                        .build())
                .article(articleEntity)
                .build();
        commentRepository.save(commentEntity);

        return convertToDTO(authUserDetails, commentEntity);
    }

    @Transactional
    @Override
    public void delete(String slug, Long commentId, AuthUserDetails authUserDetails) {
        var isAdmin = authUserDetails.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals(ERole.ROLE_ADMIN.name()));

        ArticleEntity article = articleRepository.findBySlug(slug).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));
        var isPostAuthor = article.getAuthor().getId().equals(authUserDetails.getId());

        CommentEntity commentEntity = commentRepository.findById(commentId)
                .filter(comment -> comment.getArticle().getId().equals(article.getId()))
                .orElseThrow(() -> new AppException(Error.COMMENT_NOT_FOUND));
        var isCommentAuthor = commentEntity.getAuthor().getId().equals(authUserDetails.getId());

        if (isAdmin || isPostAuthor || isCommentAuthor) {
            commentRepository.delete(commentEntity);
        }
    }

    @Override
    public List<CommentDto> getCommentsBySlug(String slug, AuthUserDetails authUserDetails) {
        Long articleId = articleRepository.findBySlug(slug).map(BaseEntity::getId).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));

        List<CommentEntity> commentEntities = commentRepository.findByArticleId(articleId);
        return commentEntities.stream().map(commentEntity -> convertToDTO(authUserDetails, commentEntity)).collect(Collectors.toList());
    }

    private CommentDto convertToDTO(AuthUserDetails authUserDetails, CommentEntity commentEntity) {
        ProfileDto author = profileService.getProfileByUserId(commentEntity.getAuthor().getId(), authUserDetails);
        return CommentDto.builder()
                .id(commentEntity.getId())
                .createdAt(commentEntity.getCreatedAt())
                .updatedAt(commentEntity.getUpdatedAt())
                .body(commentEntity.getBody())
                .author(author)
                .build();
    }
}
