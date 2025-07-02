package com.drinks.demo.model;

public class Inventory {
    private int inventoryId;
    private int branchId;
    private int drinkId;
    private int quantity;
    private int minThreshold;

    public Inventory() {}

    public Inventory(int inventoryId, int branchId, int drinkId, int quantity, int minThreshold) {
        this.inventoryId = inventoryId;
        this.branchId = branchId;
        this.drinkId = drinkId;
        this.quantity = quantity;
        this.minThreshold = minThreshold;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
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

    public int getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(int minThreshold) {
        this.minThreshold = minThreshold;
    }
}
