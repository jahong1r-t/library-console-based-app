package service;

import entity.*;
import entity.Book;
import entity.enums.BorrowState;
import entity.enums.SectionState;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static db.DataSource.*;

public class UserService {
    public void service() {
        while (true) {
            System.out.println("""
                    ===== USER MENU =====
                    0. Log out
                    1. Show sections
                    2. Show section books
                    3. Borrow book
                    4. Return book
                    5. My borrowed books
                    6. History
                    7. Notifications
                    =====================
                    """);
            System.out.print("Select option: ");

            switch (intScanner.nextInt()) {
                case 0 -> {
                    System.out.println("Log out...");
                    return;
                }
                case 1 -> showSections();
                case 2 -> showSectionBook();
                case 3 -> borrow();
                case 4 -> returnBook();
                case 5 -> borrowedBooks();
                case 6 -> history();
                case 7 -> new NotificationService().showNotifications(currentUser);
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void showSections() {
        new Section().showSectionsUI(sections, currentUser);
    }

    private void showSectionBook() {

        System.out.print(" Enter section name or id: ");
        String sectionIdOrName = strScanner.nextLine();

        boolean isSection = false;

        for (Section section : sections) {
            if (section.getStatus().equals(SectionState.ENABLED)) {
                if (Objects.equals(section.getId(), sectionIdOrName) || Objects.equals(section.getName(), sectionIdOrName)) {
                    isSection = true;
                    for (Book book : section.getBooks()) {
                        System.out.println(book);
                    }
                }
            }
        }

        if (!isSection) {
            System.out.println("Section not found!");
        }
    }

    private void borrow() {
        if (currentUser.getBorrowList().size() != 5) {
            new Section().showSectionsUI(sections, currentUser);

            System.out.print("Enter the section id: ");
            Optional<Section> sectionById = getSectionById(strScanner.nextLine());

            if (sectionById.isPresent()) {
                for (Book book : sectionById.get().getBooks()) {
                    System.out.println(book.toString(currentUser));
                }

                System.out.print("Enter book id: ");
                Optional<Book> bookById = getBookById(sectionById.get().getId(), strScanner.nextLine());

                if (bookById.isPresent()) {
                    if (bookById.get().getAvailableBook() > 0) {
                        System.out.println("Enter your book return schedule (in minutes)");
                        int returnPlanTime = intScanner.nextInt();

                        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

                        bookById.get().setAvailableBook(bookById.get().getAvailableBook() - 1);

                        currentUser.getBorrowList().add(new Borrow(
                                UUID.randomUUID().toString(),
                                currentUser,
                                bookById.get(),
                                BorrowState.BORROWED,
                                time,
                                null,
                                time.plusMinutes(returnPlanTime)
                        ));

                        currentUser.getHistories().add(new History(UUID.randomUUID().toString(), bookById.get(), time, null));

                        System.out.println("The book was borrowed successfully");
                    } else {
                        System.out.println("Sorry, this book has expired. Please try again later.");
                    }
                } else {
                    System.err.println("Book not found!");
                }


            } else {
                System.err.println("Section not found!");
            }
        } else {
            System.err.println("Sorry, you cannot borrow a book right now.\nThe maximum number of borrowed books has been reached.");
        }
    }

    private void returnBook() {

        if (currentUser.getBorrowList().isEmpty()) {
            System.out.println("The list of borrowed books is empty!");
            return;
        } else {
            for (Borrow borrow : currentUser.getBorrowList()) {
                if (borrow.getBorrowState().equals(BorrowState.BORROWED)||borrow.getBorrowState().equals(BorrowState.TIME_OVER)) {
                    System.out.println(borrow);
                }
            }
        }

        System.out.print("Enter id of the borrow you want to return: ");
        Optional<Borrow> borrowById = getBorrowById(strScanner.nextLine());

        if (borrowById.isPresent()) {
            if (currentUser.getBorrowList().remove(borrowById.get())) {
                borrowById.get().getBook().setAvailableBook(borrowById.get().getBook().getAvailableBook() + 1);
                borrowById.get().setBorrowState(BorrowState.RETURNED);

                LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

                for (History history : currentUser.getHistories()) {
                    if (Objects.equals(history.getBorrowedDate(), borrowById.get().getBorrowedTime())) {
                        history.setReturnDate(now);
                    }
                }

                System.out.println("Borrowed book successfully returned");
            } else {
                System.err.println("The borrowed book was not returned.");
            }
        } else {
            System.out.println("Borrowed book not found!");
        }
    }


    private void borrowedBooks() {

        if (currentUser.getBorrowList().isEmpty()) {
            System.out.println("The history list is empty! ");
        } else {
            for (Borrow borrow : currentUser.getBorrowList()) {
                if (borrow.getBorrowState().equals(BorrowState.BORROWED) || borrow.getBorrowState().equals(BorrowState.TIME_OVER)) {
                    System.out.println(borrow);
                }
            }
        }
    }

    private void history() {

        if (currentUser.getHistories().isEmpty()) {
            System.out.println("The history list is empty! ");
        } else {
            for (History history : currentUser.getHistories()) {
                System.out.println(history);
            }
        }
    }

    private Optional<Borrow> getBorrowById(String id) {
        for (Borrow borrow : currentUser.getBorrowList()) {
            if (Objects.equals(borrow.getId(), id)) {
                return Optional.of(borrow);
            }
        }
        return Optional.empty();
    }

    private Optional<Section> getSectionById(String id) {
        for (Section section : sections) {
            if (section.getStatus().equals(SectionState.ENABLED) && Objects.equals(section.getId(), id)) {
                return Optional.of(section);
            }
        }
        return Optional.empty();
    }

    private Optional<Book> getBookById(String sectionId, String bookId) {
        for (Section section : sections) {
            if (Objects.equals(section.getId(), sectionId)) {
                for (Book book : section.getBooks()) {
                    if (Objects.equals(book.getId(), bookId)) {
                        return Optional.of(book);
                    }
                }
            }
        }
        return Optional.empty();
    }

}