package tunght.toby.common.enums;

public enum ENotifications {
//    param_1 là username
    FOLLOW("%s đã theo dõi bạn."),
    LIKE_POST("%s đã thích một bài viết của bạn."),
    COMMENT("%s đã bình luận vào một bài viết của bạn.");

    public final String notificationTemplate;

    ENotifications(String notificationTemplate) {
        this.notificationTemplate = notificationTemplate;
    }

    public static String getNotificationMessage(ENotifications type, String username) {
        return String.format(type.notificationTemplate, username);
    }
}
