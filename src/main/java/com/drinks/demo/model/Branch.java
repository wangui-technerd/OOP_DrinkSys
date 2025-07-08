package com.drinks.demo.model;

public class Branch {
    private int branchId;
    private String name;
    private String location;

    public Branch() {}

    public Branch(int branchId, String name, String location) {
        this.branchId = branchId;
        this.name = name;
        this.location = location;
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
}