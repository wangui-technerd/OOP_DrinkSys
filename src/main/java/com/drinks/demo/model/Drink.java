package com.drinks.demo.model;

public class Drink {
    private int drinkId;
    private String name;
    private String brand;
    private double price;

    public Drink(int drinkId, String name, String brand, double price) {
        this.drinkId = drinkId;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }
    // Getters and setters...
    public int getDrinkId() { return drinkId; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    @Override
    public String toString() {
        return name + " (" + brand + ")";
    }
}
