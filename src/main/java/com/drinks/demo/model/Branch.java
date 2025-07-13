package com.drinks.demo.model;
import java.util.ArrayList;
import java.util.List;
import com.drinks.demo.model.Order;


public class Branch {
    private int branchId;
    private String name;
    private String location;
    private double totalSales;
    private List<Order> orders = new ArrayList<>();


    public Branch() {}

    public Branch(int branchId, String name, String location) {
        this.branchId = branchId;
        this.name = name;
        this.location = location;

    }


    public Branch(int branchId, String name, String location, double totalSales) {
        this(branchId, name, location); // Call the other constructor
        this.totalSales = totalSales;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public Double gettotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }


    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public String toString() {
        return location;
    }
}