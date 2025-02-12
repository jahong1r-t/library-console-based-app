package service;

import entity.*;
import entity.enums.SectionState;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static db.DataSource.*;

public class AdminService {

    public void service() {
        while (true) {
            System.out.println("""
                    ==================================
                                ADMIN MENU
                    ==================================
                    0. Log out
                    1. Add section
                    2. Show sections
                    3. Show section books
                    4. Section state
                    5. Add book
                    6. Remove book
                    7. Show users
                    8. Show user
                    ==================================
                    """);
            System.out.print("Select option: ");
            switch (intScanner.nextInt()) {
                case 0 -> {
                    return;
                }
                case 1 -> addSection();
                case 2 -> showSections();
                case 3 -> showSection();
                case 4 -> sectionState();
                case 5 -> addBook();
                case 6 -> removeBook();
                case 7 -> showUsers();
                case 8 -> showUser();
                default -> System.out.println("Invalid option! Please try again.");

            }

        }
    }

    private void addSection() {
        System.out.print("Enter section name: ");
        String name = strScanner.nextLine();
        if (checkSection(name).isEmpty()) {
            Section section = new Section(UUID.randomUUID().toString(), name, new ArrayList<>(), SectionState.ENABLED);
            sections.add(section);
        } else {
            System.err.println("Section already exist!");
        }
    }

    private void showSections() {
        new Section().showSectionsUI(sections, currentUser);
    }

    private void showSection() {
        System.out.println("Enter section name or id: ");
        String nameOrId = strScanner.nextLine();
        Optional<Section> section = checkSection(nameOrId);

        if (section.isPresent()) {
            System.out.println("\n========== 📚 Books in Section: " + section.get().getName() + " 📚 ==========");
            for (Book book : section.get().getBooks()) {
                System.out.println(book);
            }
        } else {
            System.out.println("Section not found!");
        }
    }

    private void sectionState() {
        System.out.println("Enter section name or id: ");
        String nameOrId = strScanner.nextLine();
        Optional<Section> section = checkSection(nameOrId);
        if (section.isPresent()) {
            System.out.println("""
                    Section state:
                    0.Back🔙
                    1.Disabled
                    2.Enabled
                    """);
            System.out.print("Select option: ");
            switch (intScanner.nextInt()) {
                case 1 -> section.get().setStatus(SectionState.DISABLED);
                case 2 -> section.get().setStatus(SectionState.ENABLED);
                default -> System.out.println("Invalid option! Please try again.");
            }
        } else {
            System.out.println("Section not found!");
        }
    }

    private void addBook() {
        new Section().showSectionsUI(sections, currentUser);
        System.out.print("Enter section name or id: ");
        Optional<Section> section = checkSection(strScanner.nextLine());
        if (section.isPresent()) {
            Book book = new Book();

            System.out.println("Enter book name: ");
            book.setTitle(strScanner.nextLine());
            System.out.println("Enter book author: ");
            book.setAuthor(strScanner.nextLine());
            System.out.println("Enter book total Book: ");
            book.setTotalBook(intScanner.nextInt());
            book.setAvailableBook(book.getTotalBook());

            section.get().getBooks().add(book);

            System.out.println("The book successfully added to section!");
        } else {
            System.out.println("Section not found!");
        }
    }

    private void removeBook() {
        new Section().showSectionsUI(sections, currentUser);
        System.out.print("Enter section name or id: ");
        Optional<Section> section = checkSection(strScanner.nextLine());

        int index = 0;

        if (section.isPresent()) {
            for (Book book : section.get().getBooks()) {
                System.out.println(index + 1 + ". " + book);
                index++;
            }

            System.out.print("Enter book index: ");
            section.get().getBooks().remove(intScanner.nextInt() - 1);
            System.out.println("Book successfully removed!");
        } else {
            System.out.println("Section not found!");
        }
    }

    private void showUser() {
        System.out.print("Enter user Id: ");

        Optional<User> userById = getUserById(strScanner.nextLine());

        if (userById.isPresent()) {
            User user = userById.get();

            System.out.println("\n=====================================");
            System.out.print("         👤 USER DETAILS\n");
            System.out.println("=====================================");
            System.out.printf("🔹 ID        : %s\n", user.getId());
            System.out.printf("🔹 Name      : %s %s\n", user.getName(), user.getSurname());
            System.out.printf("🔹 Role      : %s\n", user.getRole());
            System.out.printf("🔹 Email     : %s\n", user.getEmail());
            System.out.printf("🔹 Balance   : $%.2f\n", user.getBalance());

            System.out.println("\n📚 Borrowed Books:");
            if (user.getBorrowList().isEmpty()) {
                System.out.println("   ❌ No borrowed books.");
            } else {
                for (Borrow borrow : user.getBorrowList()) {
                    System.out.println("   - " + borrow);
                }
            }

            System.out.println("\n📖 Borrow History:");
            if (user.getHistories().isEmpty()) {
                System.out.println("   ❌ No history available.");
            } else {
                for (History history : user.getHistories()) {
                    System.out.println("   - " + history);
                }
            }

            System.out.println("=====================================\n");

        } else {
            System.err.println("User not found!");
        }
    }

    private void showUsers() {
        if (users.isEmpty()) {
            System.out.println("   ❌ No users available.");
        } else {
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                System.out.printf(" %d️⃣  ID: %s | Name: %s %s\n", i + 1, user.getId(), user.getName(), user.getSurname());
            }
        }

        System.out.println("=====================================\n");
    }

    private Optional<Section> checkSection(String nameOrId) {
        for (Section section : sections) {
            if (Objects.equals(section.getName(), nameOrId) || Objects.equals(section.getId(), nameOrId)) {
                return Optional.of(section);
            }
        }

        return Optional.empty();
    }

    private Optional<User> getUserById(String id) {
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

}
