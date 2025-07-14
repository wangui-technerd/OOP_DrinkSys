package com.drinks.demo.model;

import javafx.beans.property.*;
import java.sql.Timestamp;

public class Order {
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private IntegerProperty customerId = new SimpleIntegerProperty();
    private IntegerProperty branchId = new SimpleIntegerProperty();
    private ObjectProperty<Timestamp> orderDate = new SimpleObjectProperty<>();
    private DoubleProperty totalAmount = new SimpleDoubleProperty();

    private String customerName;
    private String drinkName;
    private String branchName;
    private double price;

    // For SalesController
    private StringProperty branch = new SimpleStringProperty();
    private StringProperty drink = new SimpleStringProperty();
    private IntegerProperty quantity = new SimpleIntegerProperty();
    private DoubleProperty amount = new SimpleDoubleProperty();

    public Order() {}

    public Order(int orderId, int customerId, String customerName, String drinkName,
                 int branchId, String branchName, int quantity, double price,
                 Timestamp orderDate, double totalAmount) {
        setOrderId(orderId);
        setCustomerId(customerId);
        setCustomerName(customerName);
        setDrinkName(drinkName);
        setBranchId(branchId);
        setBranchName(branchName);
        setQuantity(quantity);
        setPrice(price);
        setOrderDate(orderDate);
        setTotalAmount(totalAmount);
    }

    public Order(String branch, String drink, Timestamp orderDate, int quantity, double amount) {
        setBranch(branch);
        setDrink(drink);
        setOrderDate(orderDate);
        setQuantity(quantity);
        setAmount(amount);
    }

    // --- Getters and Setters ---
    public int getOrderId() { return orderId.get(); }
    public void setOrderId(int id) { this.orderId.set(id); }
    public IntegerProperty orderIdProperty() { return orderId; }

    public int getCustomerId() { return customerId.get(); }
    public void setCustomerId(int id) { this.customerId.set(id); }
    public IntegerProperty customerIdProperty() { return customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String name) { this.customerName = name; }

    public String getDrinkName() { return drinkName; }
    public void setDrinkName(String name) { this.drinkName = name; }

    public int getBranchId() { return branchId.get(); }
    public void setBranchId(int id) { this.branchId.set(id); }
    public IntegerProperty branchIdProperty() { return branchId; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String name) { this.branchName = name; }

    public Timestamp getOrderDate() { return orderDate.get(); }
    public void setOrderDate(Timestamp date) { this.orderDate.set(date); }
    public ObjectProperty<Timestamp> orderDateProperty() { return orderDate; }

    public double getTotalAmount() { return totalAmount.get(); }
    public void setTotalAmount(double amt) { this.totalAmount.set(amt); }
    public DoubleProperty totalAmountProperty() { return totalAmount; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // --- For SalesController (TableView display) ---
    public String getBranch() { return branch.get(); }
    public void setBranch(String b) { this.branch.set(b); }
    public StringProperty branchProperty() { return branch; }

    public String getDrink() { return drink.get(); }
    public void setDrink(String d) { this.drink.set(d); }
    public StringProperty drinkProperty() { return drink; }

    public int getQuantity() { return quantity.get(); }
    public void setQuantity(int q) { this.quantity.set(q); }
    public IntegerProperty quantityProperty() { return quantity; }

    public double getAmount() { return amount.get(); }
    public void setAmount(double a) { this.amount.set(a); }
    public DoubleProperty amountProperty() { return amount; }
}
