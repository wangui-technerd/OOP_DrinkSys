package com.drinks.demo.model;

public class OrderDetail {
    private String brand;
    private int orderDetailId;
    private int orderId;
    private int drinkId;
    private int quantity;
    private double price;


    private String drinkName;

    public OrderDetail() {}

    public OrderDetail(int orderDetailId,String brand, int orderId, int drinkId, int quantity, double price) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.brand = brand;
        this.drinkId = drinkId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}