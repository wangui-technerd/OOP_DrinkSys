package com.drinks.demo.service;

import com.drinks.demo.model.Inventory;

import java.util.List;

public interface InventoryService {
    List<Inventory> getLowStockItems(int branchId);
}
