package uz.library.db;

import uz.library.entity.*;
import uz.library.entity.enums.BorrowState;
import uz.library.entity.enums.Role;
import uz.library.entity.enums.SectionState;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class DataSource {
    public static Scanner strScanner = new Scanner(System.in);
    public static Scanner intScanner = new Scanner(System.in);
    public static ArrayList<User> users = new ArrayList<>();
    public static User currentUser;
    public static ArrayList<Section> sections = new ArrayList<>();

    public static void refresh(){
        strScanner = new Scanner(System.in);
        intScanner = new Scanner(System.in);
    }

    static {
        users.add(new User(UUID.randomUUID().toString(), Role.ADMIN, "admin", "admin", "admin", "admin", new ArrayList<>(), new ArrayList<>(), 500.0, new ArrayList<>()));
        users.add(new User(UUID.randomUUID().toString(), Role.USER, "John", "Doe", "1", "1", new ArrayList<>(), new ArrayList<>(), 500.0, new ArrayList<>()));
        users.add(new User(UUID.randomUUID().toString(), Role.USER, "Bob", "Smith", "2", "2", new ArrayList<>(), new ArrayList<>(), 500.0, new ArrayList<>()));


        users.add(new User(UUID.randomUUID().toString(), Role.USER, "Alice", "Smith", "alice@gmail.com", "alice123", new ArrayList<>(), new ArrayList<>(), 500.0, new ArrayList<>()));
        users.add(new User(UUID.randomUUID().toString(), Role.USER, "Bob", "Brown", "bob@gmail.com", "bob123", new ArrayList<>(), new ArrayList<>(), 500.0, new ArrayList<>()));
        users.add(new User(UUID.randomUUID().toString(), Role.USER, "Emma", "Johnson", "emma@gmail.com", "emma123", new ArrayList<>(), new ArrayList<>(), 500.0, new ArrayList<>()));
    }

    static {
        // 1-seksiya: Classic
        ArrayList<Book> classicBooks = new ArrayList<>();
        Section classic = new Section(UUID.randomUUID().toString(), "Classic", classicBooks, SectionState.ENABLED);
        classicBooks.add(new Book("Gone with the Wind", "Margaret Mitchell", classic, 1));
        classicBooks.add(new Book("Moby-Dick", "Herman Melville", classic, 2));
        classicBooks.add(new Book("War and Peace", "Leo Tolstoy", classic, 8));
        sections.add(classic);

        // 2-seksiya: Modern
        ArrayList<Book> modernBooks = new ArrayList<>();
        Section modern = new Section(UUID.randomUUID().toString(), "Modern", modernBooks, SectionState.ENABLED);
        modernBooks.add(new Book("To Kill a Mockingbird", "Harper Lee", modern, 1));
        modernBooks.add(new Book("The Catcher in the Rye", "J.D. Salinger", modern, 6));
        modernBooks.add(new Book("Beloved", "Toni Morrison", modern, 5));
        modernBooks.add(new Book("The Road", "Cormac McCarthy", modern, 2));
        sections.add(modern);

        // 3-seksiya: SciFi
        ArrayList<Book> sciFiBooks = new ArrayList<>();
        Section sciFi = new Section(UUID.randomUUID().toString(), "SciFi", sciFiBooks, SectionState.ENABLED);
        sciFiBooks.add(new Book("1984", "George Orwell", sciFi, 9));
        sciFiBooks.add(new Book("Brave New World", "Aldous Huxley", sciFi, 5));
        sciFiBooks.add(new Book("Fahrenheit 451", "Ray Bradbury", sciFi, 1));
        sciFiBooks.add(new Book("Dune", "Frank Herbert", sciFi, 8));
        sciFiBooks.add(new Book("The Martian", "Andy Weir", sciFi, 2));
        sections.add(sciFi);

        // 4-seksiya: Adventure
        ArrayList<Book> adventureBooks = new ArrayList<>();
        Section adventure = new Section(UUID.randomUUID().toString(), "Adventure", adventureBooks, SectionState.ENABLED);
        adventureBooks.add(new Book("Robinson Crusoe", "Daniel Defoe", adventure, 1));
        sections.add(adventure);
    }

    static {
        User user1 = users.get(1);
        User user2 = users.get(2);

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime baseTime = now.minusMinutes(10);

        // User 1
        user1.getBorrowList().add(new Borrow(UUID.randomUUID().toString(), user1, sections.get(0).getBooks().get(0), BorrowState.BORROWED, baseTime.plusMinutes(1), null, baseTime.plusMinutes(12))); // 6 → 12
        user1.getHistories().add(new History(UUID.randomUUID().toString(), sections.get(0).getBooks().get(0), baseTime, null));
        sections.get(0).getBooks().get(0).setAvailableBook(sections.get(0).getBooks().get(0).getAvailableBook() - 1);

        user1.getBorrowList().add(new Borrow(UUID.randomUUID().toString(), user1, sections.get(0).getBooks().get(1), BorrowState.BORROWED, baseTime.plusMinutes(3), null, baseTime.plusMinutes(16))); // 8 → 16
        user1.getHistories().add(new History(UUID.randomUUID().toString(), sections.get(0).getBooks().get(1), baseTime.plusMinutes(2), null));
        sections.get(0).getBooks().get(1).setAvailableBook(sections.get(0).getBooks().get(1).getAvailableBook() - 1);

        user1.getBorrowList().add(new Borrow(UUID.randomUUID().toString(), user1, sections.get(2).getBooks().get(3), BorrowState.BORROWED, baseTime.plusMinutes(5), null, baseTime.plusMinutes(20))); // 10 → 20
        user1.getHistories().add(new History(UUID.randomUUID().toString(), sections.get(2).getBooks().get(3), baseTime.plusMinutes(4), null));
        sections.get(2).getBooks().get(3).setAvailableBook(sections.get(2).getBooks().get(3).getAvailableBook() - 1);

        // User 2
        user2.getBorrowList().add(new Borrow(UUID.randomUUID().toString(), user2, sections.get(1).getBooks().get(2), BorrowState.BORROWED, baseTime.plusMinutes(6), null, baseTime.plusMinutes(22))); // 11 → 22
        user2.getHistories().add(new History(UUID.randomUUID().toString(), sections.get(1).getBooks().get(2), baseTime.plusMinutes(5), null));
        sections.get(1).getBooks().get(2).setAvailableBook(sections.get(1).getBooks().get(2).getAvailableBook() - 1);

        user2.getBorrowList().add(new Borrow(UUID.randomUUID().toString(), user2, sections.get(3).getBooks().get(0), BorrowState.BORROWED, baseTime.plusMinutes(7), null, baseTime.plusMinutes(24))); // 12 → 24
        user2.getHistories().add(new History(UUID.randomUUID().toString(), sections.get(3).getBooks().get(0), baseTime.plusMinutes(6), null));
        sections.get(3).getBooks().get(0).setAvailableBook(sections.get(3).getBooks().get(0).getAvailableBook() - 1);
    }

}
