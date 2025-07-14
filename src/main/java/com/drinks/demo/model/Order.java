package com.drinks.demo.model;

import javafx.beans.property.*;

import java.sql.Timestamp;

public class Order {
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private IntegerProperty customerId = new SimpleIntegerProperty();
    private IntegerProperty branchId = new SimpleIntegerProperty();
    private ObjectProperty<java.sql.Timestamp> orderDate = new SimpleObjectProperty<>();
    private DoubleProperty totalAmount = new SimpleDoubleProperty();

    // Extra display data (for SalesController)
    private StringProperty branch = new SimpleStringProperty();
    private StringProperty drink = new SimpleStringProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();
    private DoubleProperty amount = new SimpleDoubleProperty();

    public Order() {}

    // Constructor for Sales display
    public Order(String branch, String drink, java.sql.Timestamp orderDate, int quantity, double amount) {
        this.branch.set(branch);
        this.drink.set(drink);
        this.orderDate.set(orderDate);
        this.quantity.set(quantity);
        this.amount.set(amount);
    }

    public Order(int orderId, int userId, int branchId, Timestamp orderDate, double totalAmount) {
    }

    // --- Getters and Setters for DB access ---
    public int getOrderId() {
        return orderId.get();
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public int getBranchId() {
        return branchId.get();
    }

    public void setBranchId(int branchId) {
        this.branchId.set(branchId);
    }

    public java.sql.Timestamp getOrderDate() {
        return orderDate.get();
    }

    public void setOrderDate(java.sql.Timestamp orderDate) {
        this.orderDate.set(orderDate);
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount.set(totalAmount);
    }

    // --- Properties for JavaFX TableView ---
    public StringProperty branchProperty() {
        return branch;
    }

    public String getBranch() {
        return branch.get();
    }

    public void setBranch(String branch) {
        this.branch.set(branch);
    }

    public StringProperty drinkProperty() {
        return drink;
    }

    public String getDrink() {
        return drink.get();
    }

    public void setDrink(String drink) {
        this.drink.set(drink);
    }

    public ObjectProperty<java.sql.Timestamp> dateProperty() {
        return orderDate;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public double getAmount() {
        return amount.get();
    }
}
