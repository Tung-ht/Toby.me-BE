package tunght.toby.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tunght.toby.common.enums.ENotifications;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NotificationDto {

    private String toUserId;

    private String message;

    private ENotifications type;

    private String fromUserId;

    private String postId;

    private String commentId;

    private Boolean isRead;
}
