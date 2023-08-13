package tunght.toby.notification.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tunght.toby.common.entity.BaseEntity;
import tunght.toby.common.enums.ENotifications;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class NotificationEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ENotifications type;

    @Column
    private String postId;

    @Column
    private String commentId;

    @Column(nullable = false)
    private String fromUserId;

    @Column(nullable = false)
    private String toUserId;

    @Column(nullable = false)
    private String message;

    @Column
    private Boolean isRead;

    @Builder
    public NotificationEntity(ENotifications type, String postId, String commentId, String fromUserId, String toUserId, String message, Boolean isRead, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.type = type;
        this.postId = postId;
        this.commentId = commentId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
