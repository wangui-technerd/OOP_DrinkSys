package com.drinks.demo.service;

import com.drinks.demo.database.OrderDao;
import com.drinks.demo.model.Order;

import java.util.List;

public class ReportServiceImpl implements ReportService {
    private final OrderDao orderDAO = new OrderDao();

    @Override
    public List<Order> getOrdersByCustomer(int customerId) {
        return orderDAO.getOrdersByCustomer(customerId);
    }

    @Override
    public double getBranchSales(int branchId) {
        return orderDAO.getBranchSales(branchId);
    }

    @Override
    public double getTotalSales() {
        return orderDAO.getTotalSales();
    }
}
