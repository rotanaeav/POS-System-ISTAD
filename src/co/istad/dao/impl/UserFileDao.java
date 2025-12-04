package co.istad.dao.impl;

import co.istad.dao.UserDao;
import co.istad.entity.User;
import static co.istad.utils.PrintUtils.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserFileDao implements UserDao {

    private static final String PATH = "data/users.csv";

    @Override
    public Optional<User> selectByUsername(String username) {
        File file = new File(PATH);
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
                        return Optional.of(user);
                    }
                }
            }
        } catch (IOException e) {
            printErr("Error reading Users file: " + e.getMessage());
        }

        return Optional.empty();
    }
    @Override
    public User searchById(Integer id) {
        return selectAll().stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    @Override
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        File file = new File(PATH);

        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; } // Skip Header

                String[] cells = line.split(",");

                if (cells.length >= 5) {
                    User u = new User();
                    u.setId(Integer.parseInt(cells[0].trim()));
                    u.setUsername(cells[1].trim());
                    u.setPassword(cells[2].trim());
                    u.setRole(cells[3].trim());
                    u.setStatus(cells[4].trim());
                    users.add(u);
                }
            }
        } catch (IOException e) {
            printErr("Error reading data file: " + e.getMessage());
        }
        return users;
    }
    @Override
    public void insert(User user) {
        List<User> allUsers = selectAll();
        allUsers.removeIf(u -> u.getId().equals(user.getId()));
        allUsers.add(user);
        saveAll(allUsers);
    }
    private void saveAll(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
            writer.write("ID,Username,Password,Role,Status");
            writer.newLine();
            for (User u : users) {
                String line = String.format("%d,%s,%s,%s,%s",
                        u.getId(),
                        u.getUsername(),
                        u.getPassword(),
                        u.getRole(),
                        u.getStatus()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            printErr("Save Error: " + e.getMessage());
        }
    }
}