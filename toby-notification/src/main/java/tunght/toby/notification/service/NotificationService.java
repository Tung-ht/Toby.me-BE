package tunght.toby.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tunght.toby.notification.entity.NotificationEntity;
import tunght.toby.notification.repository.NotificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public List<NotificationEntity> getNotificationsByUserId(String userId) {
        return notificationRepository.findAllByToUserIdOrderByCreatedAtDesc(userId);
    }

    public Long countUnreadNotifications(String userId) {
        return notificationRepository.countByToUserIdAndIsRead(userId, false);
    }

    @Transactional
    public void readNotification(Long notificationId) {
        notificationRepository.readByNotificationId(notificationId);
    }

    @Transactional
    public void readAllNotificationsByUserId(String userId) {
        notificationRepository.readAllByToUserId(userId);
    }
}
