 package com.drinks.demo.service;

import com.drinks.demo.database.*;
import com.drinks.demo.model.*;
import com.drinks.demo.utilities.DBConnection;

import java.sql.Timestamp;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDAO = new    OrderDao();
    private final OrderDetailDao orderDetailDAO = new OrderDetailDao();
    private final BranchDao branchDAO = new BranchDao();
    private final DrinkDao drinkDAO = new DrinkDao();
    private final PaymentDao paymentDAO = new PaymentDao();
    private final CustomerDao customerDAO = new CustomerDao();

    @Override
    public int placeOrder(Order order, List<OrderDetail> orderDetails) {
        if (order.getOrderDate() == null) {
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        }

        int orderId = orderDAO.addOrder(order);
        if (orderId > 0) {
            for (OrderDetail detail : orderDetails) {
                detail.setOrderId(orderId);
                orderDetailDAO.addOrderDetail(detail);
            }
        }
        return orderId;
    }

    @Override
    public boolean savePayment(int orderId, String method, String code) {
        return paymentDAO.save(orderId, method, code);
    }

    @Override
    public Order getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    @Override
    public String getCustomerNameById(int customerId) {
        return customerDAO.getCustomerNameById(customerId);
    }

    @Override
    public List<Drink> getAllDrinks() {
        return drinkDAO.getAllDrinks();
    }

    @Override
    public List<Branch> getAllBranches() {
        return branchDAO.getAllBranches();
    }

    @Override
    public List<String> getAllBranchNames() {
        return branchDAO.getAllBranchLocations();
    }

    @Override
    public List<String> getDrinkTypesByCategory(String keyword) {
        return drinkDAO.getDrinkNamesByKeyword(keyword);
    }
}