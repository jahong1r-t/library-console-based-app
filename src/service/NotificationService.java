package service;


import entity.Borrow;
import entity.User;
import entity.enums.BorrowState;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static db.DataSource.users;

public class NotificationService {
    private final ScheduledExecutorService service = new ScheduledThreadPoolExecutor(2);

    public void service() {
        service.scheduleAtFixedRate(this::checkBorrows, 0, 1, TimeUnit.MINUTES);
        service.scheduleAtFixedRate(this::timeOverBorrowCheck, 0, 15, TimeUnit.MINUTES);
    }

    public void stopService() {
        service.shutdown();
        try {
            if (!service.awaitTermination(3, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            service.shutdownNow();
        }
    }

    private void checkBorrows() {
        LocalDateTime now = LocalDateTime.now();

        for (User user : users) {
            for (Borrow borrow : user.getBorrowList()) {
                if (now.isAfter(borrow.getPlannedReturnTime()) && borrow.getReturnTime() == null
                        && borrow.getBorrowState().equals(BorrowState.BORROWED)) {

                    borrow.setBorrowState(BorrowState.TIME_OVER);

                    double remainingBalance = user.getBalance() - 15;
                    if (remainingBalance < 0) remainingBalance = 0;

                    user.setBalance(remainingBalance);
                    sendNotification(borrow);
                }
            }
        }
    }

    private void timeOverBorrowCheck() {
        LocalDateTime now = LocalDateTime.now();

        for (User user : users) {
            for (Borrow borrow : user.getBorrowList()) {
                if (borrow.getBorrowState().equals(BorrowState.TIME_OVER)) {
                    if (ChronoUnit.MINUTES.between(borrow.getPlannedReturnTime(), now) >= 15) {
                        double remainingBalance = user.getBalance() - 15;
                        if (remainingBalance < 0) remainingBalance = 0;

                        user.setBalance(remainingBalance);
                        sendNotification(borrow);
                    }
                }
            }
        }
    }

    private void sendNotification(Borrow borrow) {
        User user = borrow.getUser();
        String message = String.format("""
                        📢 Overdue Notice!\s
                        📄 Borrow ID   : %s
                        📖 Book        : %s
                        ⏳ Due Date    : %s
                        ❌ Status      : TIME_OVER (%d minutes late)
                        💰 Fine Amount : $%.2f
                        💳 Balance Left: $%.2f
                        """, borrow.getId(), borrow.getBook().toString(user), borrow.getPlannedReturnTime(),
                ChronoUnit.MINUTES.between(borrow.getPlannedReturnTime(), LocalDateTime.now()), 15d, user.getBalance());

        user.getNotification().add(message);
    }

    public void showNotifications(User user) {
        if (user.getNotification().isEmpty()) {
            System.out.println("\n✅ No new notifications.\n");
            return;
        }

        System.out.println("\n========== 📢 Notifications 📢 ==========");
        for (String message : user.getNotification()) {
            System.out.println(message);
            System.out.println("=====================================");
        }
        user.getNotification().clear();
    }
}
