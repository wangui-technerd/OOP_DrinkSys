package com.drinks.demo.model;

public class Drink {
    private int drinkId;
    private String name;
    private String brand;
    private double price;

    public Drink() {}

    public Drink(int drinkId, String name, String brand, double price) {
        this.drinkId = drinkId;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
