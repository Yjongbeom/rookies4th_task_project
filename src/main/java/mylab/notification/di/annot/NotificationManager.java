package mylab.notification.di.annot;

public class NotificationManager {
    private NotificationService emailService;
    private NotificationService smsService;
    
    public NotificationManager(NotificationService emailService, NotificationService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
    }
    
    public NotificationService getEmailService() { return emailService; }
    public NotificationService getSmsService() { return smsService; }
    
    public void sendNotificationByEmail(String message) {
        emailService.sendNotification(message);
    }
    
    public void sendNotificationBySms(String message) {
        smsService.sendNotification(message);
    }
}