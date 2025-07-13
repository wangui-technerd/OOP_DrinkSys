package com.drinks.demo.service;

import com.drinks.demo.database.OrderDao;
import com.drinks.demo.model.Order;
import java.sql.SQLException;
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
    public double getTotalSales() throws ReportServiceException {
        try {
            return orderDAO.getTotalSales();
        } catch (SQLException e) {
            throw new ReportServiceException("Failed to calculate total sales", e);
        }
    }
}