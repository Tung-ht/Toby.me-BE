package tunght.toby.be.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tunght.toby.be.dto.CommentDto;
import tunght.toby.be.dto.NotificationDto;
import tunght.toby.be.dto.ProfileDto;
import tunght.toby.be.entity.ArticleEntity;
import tunght.toby.be.entity.CommentEntity;
import tunght.toby.be.repository.ArticleRepository;
import tunght.toby.be.repository.CommentRepository;
import tunght.toby.be.service.CommentService;
import tunght.toby.be.service.ProfileService;
import tunght.toby.common.entity.BaseEntity;
import tunght.toby.common.entity.UserEntity;
import tunght.toby.common.enums.ENotifications;
import tunght.toby.common.enums.ERole;
import tunght.toby.common.exception.AppException;
import tunght.toby.common.exception.Error;
import tunght.toby.common.security.AuthUserDetails;
import tunght.toby.common.utils.JsonConverter;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final ProfileService profileService;

    @Value("${spring.kafka.comment-noti-topic}")
    private String commentTopic;
    private final KafkaTemplate<String, Object> notiKafkaTemplate;

    @Transactional
    @Override
    public CommentDto addCommentsToAnArticle(String slug, CommentDto comment, AuthUserDetails authUserDetails) {
        ArticleEntity articleEntity = articleRepository.findBySlug(slug).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));
        CommentEntity commentEntity = CommentEntity.builder()
                .body(comment.getBody())
                .author(UserEntity.builder()
                        .id(authUserDetails.getId())
                        .username(authUserDetails.getUsername())
                        .build())
                .article(articleEntity)
                .build();
        commentRepository.save(commentEntity);

        String username = profileService.getUsernameById(authUserDetails.getId());

        NotificationDto notificationDto = NotificationDto.builder()
                .type(ENotifications.COMMENT)
                .fromUserId(commentEntity.getAuthor().getId().toString())
                .toUserId(commentEntity.getArticle().getAuthor().getId().toString())
                .commentId(commentEntity.getId().toString())
                .postId(commentEntity.getArticle().getId().toString())
                .message(ENotifications.getNotificationMessage(ENotifications.COMMENT, username))
                .isRead(false)
                .build();

        notiKafkaTemplate.send(commentTopic, JsonConverter.serializeObject(notificationDto));

        return convertToDTO(authUserDetails, commentEntity);
    }

    @Transactional
    @Override
    public void delete(String slug, Long commentId, AuthUserDetails authUserDetails) throws AccessDeniedException {
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
        } else {
            throw new AccessDeniedException(null);
        }
    }

    @Override
    public List<CommentDto> getCommentsBySlug(String slug, AuthUserDetails authUserDetails) {
        Long articleId = articleRepository.findBySlug(slug).map(BaseEntity::getId).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));

        List<CommentEntity> commentEntities = commentRepository.findByArticleIdOrderByCreatedAtAsc(articleId);
        return commentEntities.stream().map(commentEntity -> convertToDTO(authUserDetails, commentEntity)).collect(Collectors.toList());
    }

    @Override
    public CommentDto update(String slug, Long commentId, CommentDto.Update newComment, AuthUserDetails authUserDetails) throws AccessDeniedException {
        ArticleEntity article = articleRepository.findBySlug(slug).orElseThrow(() -> new AppException(Error.ARTICLE_NOT_FOUND));

        CommentEntity commentEntity = commentRepository.findById(commentId)
                .filter(comment -> comment.getArticle().getId().equals(article.getId()))
                .orElseThrow(() -> new AppException(Error.COMMENT_NOT_FOUND));
        var isCommentAuthor = commentEntity.getAuthor().getId().equals(authUserDetails.getId());

        if (isCommentAuthor) {
            commentEntity.setBody(newComment.getBody());
            commentEntity = commentRepository.save(commentEntity);
            return convertToDTO(authUserDetails, commentEntity);
        } else {
            throw new AccessDeniedException(null);
        }
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

    private NotificationDto createNotiDto(CommentEntity commentEntity) {
        return NotificationDto.builder()
                .type(ENotifications.COMMENT)
                .fromUserId(commentEntity.getAuthor().getId().toString())
                .toUserId(commentEntity.getArticle().getAuthor().getId().toString())
                .commentId(commentEntity.getId().toString())
                .postId(commentEntity.getArticle().getId().toString())
                .message(ENotifications.getNotificationMessage(ENotifications.COMMENT, commentEntity.getAuthor().getUsername()))
                .isRead(false)
                .build();
    }

}
