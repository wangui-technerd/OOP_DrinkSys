package com.drinks.demo.model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

public class Inventory {
    private final IntegerProperty inventoryId = new SimpleIntegerProperty();
    private final IntegerProperty branchId = new SimpleIntegerProperty();
    private final IntegerProperty drinkId = new SimpleIntegerProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final IntegerProperty minThreshold = new SimpleIntegerProperty();
    private final StringProperty branchName = new SimpleStringProperty();
    private final StringProperty drinkName = new SimpleStringProperty();

    public Inventory() {}

    public Inventory(int inventoryId, int branchId, int drinkId, int quantity, int minThreshold) {
        this.inventoryId.set(inventoryId);
        this.branchId.set(branchId);
        this.drinkId.set(drinkId);
        this.quantity.set(quantity);
        this.minThreshold.set(minThreshold);
    }

    // Getters and Setters

    public int getInventoryId() { return inventoryId.get(); }
    public void setInventoryId(int inventoryId) { this.inventoryId.set(inventoryId); }
    public IntegerProperty inventoryIdProperty() { return inventoryId; }

    public int getBranchId() { return branchId.get(); }
    public void setBranchId(int branchId) { this.branchId.set(branchId); }
    public IntegerProperty branchIdProperty() { return branchId; }

    public int getDrinkId() { return drinkId.get(); }
    public void setDrinkId(int drinkId) { this.drinkId.set(drinkId); }
    public IntegerProperty drinkIdProperty() { return drinkId; }

    public int getQuantity() { return quantity.get(); }
    public void setQuantity(int quantity) { this.quantity.set(quantity); }
    public IntegerProperty quantityProperty() { return quantity; }

    public int getMinThreshold() { return minThreshold.get(); }
    public void setMinThreshold(int minThreshold) { this.minThreshold.set(minThreshold); }
    public IntegerProperty minThresholdProperty() { return minThreshold; }

    public String getBranchName() { return branchName.get(); }
    public void setBranchName(String branchName) { this.branchName.set(branchName); }
    public StringProperty branchNameProperty() { return branchName; }

    public String getDrinkName() { return drinkName.get(); }
    public void setDrinkName(String drinkName) { this.drinkName.set(drinkName); }
    public StringProperty drinkNameProperty() { return drinkName; }

    // Observable method (what you requested)
    public ObservableValue<String> getDrinkNameObservable() {
        return drinkName;
    }
}
