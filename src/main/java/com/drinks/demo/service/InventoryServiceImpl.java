package com.drinks.demo.service;

import com.drinks.demo.database.InventoryDAO;
import com.drinks.demo.model.Inventory;

import java.util.List;

public class InventoryServiceImpl implements InventoryService {
    private final InventoryDAO inventoryDAO = new InventoryDAO();

    @Override
    public List<Inventory> getLowStockItems(int branchId) {
        return inventoryDAO.getLowStock(branchId);
    }
}
