package co.istad.dao.impl;

import co.istad.dao.UserDao;
import co.istad.entity.User;
import static co.istad.utils.PrintUtils.*;

import java.io.*;
import java.util.Optional;

public class UserFileDao implements UserDao {

    private static final String FILE_PATH = "data/users.csv";

    @Override
    public Optional<User> selectByUsername(String username) {
        File file = new File(FILE_PATH);
        if (!file.exists()) return Optional.empty();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; } // Skip Header

                String[] parts = line.split(",");

                if (parts.length >= 5) {
                    String fileUser = parts[1].trim();


                    if (fileUser.equals(username)) {
                        User user = new User(
                                Integer.parseInt(parts[0].trim()),
                                parts[1].trim(),
                                parts[2].trim(),
                                parts[3].trim(),
                                parts[4].trim()
                        );
                        //use optional prevent error
                        return Optional.of(user);
                    }
                }
            }
        } catch (IOException e) {
            printErr("Error reading Users file: " + e.getMessage());
        }

        return Optional.empty();
    }
}