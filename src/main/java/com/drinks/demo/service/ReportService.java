package com.drinks.demo.service;

import com.drinks.demo.model.Order;

import java.util.List;

public interface ReportService {
    List<Order> getOrdersByCustomer(int customerId);
    double getBranchSales(int branchId);
    double getTotalSales();
}
