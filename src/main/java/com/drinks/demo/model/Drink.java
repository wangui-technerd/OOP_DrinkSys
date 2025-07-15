package com.drinks.demo.model;

public class Drink {
    private int drinkId;
    private String name;
    private String brand;
    private double price;
    private int quantity;

    public Drink(int drinkId, String name, String brand, double price) {
        this.drinkId = drinkId;
        this.name = name;
        this.brand = brand;
        this.price = price;

    }
    public Drink(int drinkId, String name, double price, int quantity) {
        this.drinkId = drinkId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters...
    public int getDrinkId() { return drinkId; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public double getQuantity() { return quantity; }

    public void setDrinkId(int drinkId) { this.drinkId = drinkId; }
    public void setName(String name) { this.name = name; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return name + " (" + brand + ")";
    }
}
