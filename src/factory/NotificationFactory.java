package factory;

import entities.NotificationType;
import services.EmailNotification;
import services.Notification;
import services.SmsNotification;

public class NotificationFactory {

    public static Notification createNotification(NotificationType type) {
        switch (type) {
            case EMAIL:
                return new EmailNotification();
            case SMS:
                return new SmsNotification();
            default:
                throw new IllegalArgumentException("Unsupported notification type");
        }
    }
}
