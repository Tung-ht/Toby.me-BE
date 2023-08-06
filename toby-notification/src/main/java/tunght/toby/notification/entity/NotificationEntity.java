package tunght.toby.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tunght.toby.common.entity.BaseEntity;
import tunght.toby.common.enums.ENotifications;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class NotificationEntity extends BaseEntity {

    @Column(nullable = false)
    private String toUserId;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    private ENotifications type;

    @Column(nullable = false)
    private String fromUserId;

    @Column
    private String postId;

    @Column
    private String commentId;

    @Column
    private Boolean isRead;

}
