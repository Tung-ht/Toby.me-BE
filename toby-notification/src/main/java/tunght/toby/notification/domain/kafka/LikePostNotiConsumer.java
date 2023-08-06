package tunght.toby.notification.domain.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tunght.toby.common.utils.JsonConverter;
import tunght.toby.notification.config.CmdDefs;
import tunght.toby.notification.domain.websocket.manager.UserManager;
import tunght.toby.notification.domain.websocket.network.IPacket;
import tunght.toby.notification.domain.websocket.network.IUser;
import tunght.toby.notification.domain.websocket.network.UserPacket;
import tunght.toby.notification.dto.NotificationDto;
import tunght.toby.notification.entity.NotificationEntity;
import tunght.toby.notification.repository.NotificationRepository;

@Component
@RequiredArgsConstructor
public class LikePostNotiConsumer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NotificationRepository notificationRepository;

    @KafkaListener(topics = "${spring.kafka.like-post-noti-topic}", groupId = "${spring.kafka.group-noti-id}", containerFactory = "notiListenerContainerFactory")
    public void receiveMessage(@Payload String notification) {
        logger.info("receive payload: {}", notification);
        NotificationDto notificationDto = JsonConverter.deserializeObject(notification, NotificationDto.class);

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .type(notificationDto.getType())
                .postId(notificationDto.getPostId())
                .fromUserId(notificationDto.getFromUserId())
                .toUserId(notificationDto.getToUserId())
                .message(notificationDto.getMessage())
                .isRead(false)
                .build();

        IUser user = UserManager.getUser(notificationDto.getToUserId());
        if (user == null) {
            logger.info("user {} not online", notificationDto.getToUserId());
        } else {
            IPacket packet = new UserPacket(CmdDefs.LIKE_POST_NOTI_CMD);
            NotificationPackageSender.sendDataPackage(notificationEntity, user, packet);
        }

        notificationRepository.save(notificationEntity);
    }


}
