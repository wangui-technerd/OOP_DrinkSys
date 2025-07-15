package com.drinks.demo.service;

import com.drinks.demo.database.OrderDao;
import com.drinks.demo.model.Order;

import java.sql.SQLException;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private final OrderDao orderDAO;

    public ReportServiceImpl() {
        this.orderDAO = new OrderDao(); // Default constructor
    }

    public ReportServiceImpl(OrderDao orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public List<Order> getOrdersByCustomer(int customerId) {
        return orderDAO.getOrdersByCustomer(customerId);
    }

    @Override
    public double getBranchSales(int branchId) {
        return orderDAO.getBranchSales(branchId);  // ✅ Corrected method call
    }

    @Override
    public double getTotalSales() throws ReportServiceException {
        try {
            return orderDAO.getTotalSales();  // ✅ This already matches your DAO method
        } catch (SQLException e) {
            throw new ReportServiceException("Failed to calculate total sales", e);
        }
    }
}
