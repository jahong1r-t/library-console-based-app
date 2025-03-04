package uz.library.service;


import uz.library.entity.User;
import uz.library.entity.enums.Role;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static uz.library.db.DataSource.*;

public class AuthService {
    public void service() {
        new NotificationService().service();
        while (true) {
            System.out.println("""
                    ======= MAIN MENU =======
                    0. Exit"
                    1. Sign Up
                    2. Sign In
                    =========================
                    """);
            System.out.print("Select option: ");

            switch (intScanner.nextInt()) {
                case 0 -> {
                    System.out.println("Program has been finished!");
                    return;
                }
                case 1 -> signUp();
                case 2 -> signIn();
                default -> System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private void signUp() {
        progress("Sign up progress...");

        System.out.print("Enter name: ");
        String name = strScanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = strScanner.nextLine();
        System.out.print("Enter email : ");
        String email = strScanner.nextLine();

        if (!checkEmail(email)) {
            System.out.print("Enter password: ");
            String pass = strScanner.nextLine();

            User user = new User(name, Role.USER, name, surname, email, pass, new ArrayList<>(), new ArrayList<>(), 100d, new ArrayList<>());
            users.add(user);

            System.out.println("✅ User registered successfully!");
        } else {
            System.out.println("❌ This email is already in use.");
        }
    }

    private void signIn() {
        progress("Sign in progress...");

        System.out.print("Enter email: ");
        String email = strScanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = strScanner.nextLine();

        Optional<User> login = login(email, pass);

        if (login.isPresent()) {
            currentUser = login.get();

            if (login.get().getRole().equals(Role.ADMIN)) {
                new AdminService().service();
            } else {
                new UserService().service();
            }
        } else {
            System.out.println("❌ Incorrect email or password. Try again!");
        }
    }

    private Optional<User> login(String email, String password) {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    private boolean checkEmail(String email) {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                return true;
            }
        }
        return false;
    }

    private void progress(String fieldName) {
        System.out.println("======================================");
        System.out.println("      " + fieldName + "      ");
        System.out.println("======================================");
    }
}
