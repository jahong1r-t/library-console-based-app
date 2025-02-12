import service.AuthService;
import service.NotificationService;

public class Main {
    public static void main(String[] args) {
        try {
            NotificationService notificationService = new NotificationService();
            notificationService.service();
            new AuthService().service();
            notificationService.stopService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
