package tunght.toby.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tunght.toby.notification.entity.NotificationEntity;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findAllByToUserIdOrderByCreatedAtDesc(String userId);

    Long countByToUserIdAndIsRead(String userId, Boolean isRead);

    @Modifying
    @Query(value = "UPDATE NotificationEntity n " +
            "SET n.isRead = true " +
            "WHERE n.toUserId LIKE :userId")
    void readAllByToUserId(String userId);

    @Modifying
    @Query(value = "UPDATE NotificationEntity n " +
            "SET n.isRead = true " +
            "WHERE n.id = :notificationId")
    void readByNotificationId(Long notificationId);
}
