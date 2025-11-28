package co.istad.service;
import co.istad.dao.UserDao;
import co.istad.dao.impl.UserFileDao;
import co.istad.entity.User;
import static co.istad.utils.InputUtils.*;
import static co.istad.utils.PrintUtils.*;

import java.util.Optional;

public class AuthService {

    private final UserDao userDao = new UserFileDao();

    private static User currentUser;

    public User login() {
        printHead("SYSTEM LOGIN");

        while (true) {
            String username = readValidText("Username");
            if (username.equals("0") || username.equalsIgnoreCase("exit")) {
                printInfo("Exiting System.....");
                return null;
            }
            String password = readValidText("Password");

            Optional<User> userOpt = userDao.selectByUsername(username);

            if (userOpt.isPresent()) {
                User user = userOpt.get();

                if (user.getPassword().equals(password)) {

                    if (user.getStatus().equalsIgnoreCase("Inactive")) {
                        printErr("Account locked. Please contact Admin.");
                        continue;
                    }

                    currentUser = user;
                    printTrue("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
                    return user;
                }
            }

            printErr("Invalid Username or Password. Try again.");
        }
    }

    public void logout() {
        currentUser = null;
        printInfo("Logged out successfully.");
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}