package entitiy;


import entitiy.enums.BorrowState;

import java.time.LocalDateTime;

public class Borrow {
    private String id;
    private User user;
    private Book book;
    private BorrowState borrowState;
    private LocalDateTime borrowedTime;
    private LocalDateTime returnTime;
    private LocalDateTime plannedReturnTime;

    public Borrow() {
    }

    public Borrow(String id, User user, Book book, BorrowState borrowState, LocalDateTime borrowedTime, LocalDateTime returnTime, LocalDateTime plannedReturnTime) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.borrowState = borrowState;
        this.borrowedTime = borrowedTime;
        this.returnTime = returnTime;
        this.plannedReturnTime = plannedReturnTime;
    }

    public LocalDateTime getPlannedReturnTime() {
        return plannedReturnTime;
    }

    public void setPlannedReturnTime(LocalDateTime plannedReturnTime) {
        this.plannedReturnTime = plannedReturnTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BorrowState getBorrowState() {
        return borrowState;
    }

    public void setBorrowState(BorrowState borrowState) {
        this.borrowState = borrowState;
    }

    public LocalDateTime getBorrowedTime() {
        return borrowedTime;
    }

    public void setBorrowedTime(LocalDateTime borrowedTime) {
        this.borrowedTime = borrowedTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    @Override
    public String toString() {
        return String.format(
                "📄 Borrow ID: [%s] | 📖 Book: %s | 👤 User: %s | 📅 Borrowed: %s | 🗓️ Planned Return: %s | 🔄 State: %s | 📆 Actual Return: %s",
                id,
                book.getTitle(),
                user.getName() + " " + user.getSurname(),
                borrowedTime,
                plannedReturnTime,
                borrowState,
                (returnTime != null ? returnTime : "❌ Not Returned")
        );
    }

}
