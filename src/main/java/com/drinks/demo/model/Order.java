package com.drinks.demo.model;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private int customerId;
    private int branchId;
    private Timestamp orderDate;
    private double totalAmount;

    public Order() {}

    public Order(int orderId, int customerId, int branchId, Timestamp orderDate, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.branchId = branchId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
