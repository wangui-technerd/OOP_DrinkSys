package com.drinks.demo.model;

public class User {
    private String name;
    private String email;
    private String password;
    private String contact;
    private String role;

    public User(String name,String contact, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.password = password;
        this.role = role;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getContact(){return contact;}

    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; } // ✅ Add this
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return name + "," + email + "," + password + "," + role;
    }

    public static User fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length == 5) {
            return new User(parts[0], parts[1], parts[2], parts[3], parts[4]);
        }
        return null;
    }
}
