import db.DataSource;
import service.AuthService;
import service.NotificationService;

public class Main {
    public static void main(String[] args) {
        while (true) {
            DataSource.refresh();
            try {
                new NotificationService().service();
                new AuthService().service();

                Thread.sleep(5000);
                System.out.println("The program is closed...");
            } catch (Exception ignored) {
            }
        }
    }
}