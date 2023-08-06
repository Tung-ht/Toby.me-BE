package tunght.toby.notification.domain.kafka;

import tunght.toby.notification.domain.websocket.network.IPacket;
import tunght.toby.notification.domain.websocket.network.IUser;
import tunght.toby.notification.entity.NotificationEntity;

public class NotificationPackageSender {
    static void sendDataPackage(NotificationEntity notificationEntity, IUser user, IPacket packet) {
        packet.addField("type", notificationEntity.getType().name());
        packet.addField("postId", notificationEntity.getPostId());
        packet.addField("commentId", notificationEntity.getCommentId());
        packet.addField("fromUserId", notificationEntity.getFromUserId());
        packet.addField("toUserId", notificationEntity.getToUserId());
        packet.addField("message", notificationEntity.getMessage());
        packet.addField("isRead", notificationEntity.getIsRead());
        packet.addField("createdAt", notificationEntity.getCreatedAt());
        user.sendMessageTo(packet);
    }
}
