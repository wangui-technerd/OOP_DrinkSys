package com.drinks.demo.service;

import com.drinks.demo.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private final String userFile = "users.txt"; // stored in project root
    private final List<User> users;

    public AuthService() {
        users = loadUsers();
    }

    public boolean registerUser(User user) {
        if (emailExists(user.getEmail())) {
            return false;
        }
        users.add(user);
        saveUsers();
        return true;
    }

    public User login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean emailExists(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(userFile))) {
            for (User user : users) {
                writer.println(user.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<User> loadUsers() {
        List<User> list = new ArrayList<>();
        File file = new File(userFile);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = User.fromString(line);
                if (user != null) {
                    list.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
