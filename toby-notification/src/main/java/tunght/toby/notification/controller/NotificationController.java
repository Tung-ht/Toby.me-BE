package tunght.toby.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tunght.toby.notification.entity.NotificationEntity;
import tunght.toby.notification.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping(value = "/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationEntity> getNotificationsByUserId(@RequestParam(name = "userId") String userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    @GetMapping("/count-unread")
    public Long countUnreadNotifications(@RequestParam(name = "userId") String userId) {
        return notificationService.countUnreadNotifications(userId);
    }

    @PutMapping("read/{id}")
    public void readNotification(@PathVariable(name = "id") Long notificationId) {
        notificationService.readNotification(notificationId);
    }

    @PutMapping("read-all")
    public void readAllNotificationsByUserId(@RequestParam(name = "userId") String userId) {
        notificationService.readAllNotificationsByUserId(userId);
    }
}
