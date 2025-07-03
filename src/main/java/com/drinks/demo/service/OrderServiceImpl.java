package com.drinks.demo.service;

import com.drinks.demo.database.PaymentDao;
import com.drinks.demo.database.OrderDao;
import com.drinks.demo.database.OrderDetailDao;
import com.drinks.demo.database.BranchDao;
import com.drinks.demo.database.DrinkDao;
import com.drinks.demo.database.CustomerDao;

import com.drinks.demo.model.Order;
import com.drinks.demo.model.OrderDetail;
import com.drinks.demo.model.Drink;
import com.drinks.demo.model.Branch;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDAO = new OrderDao();
    private final OrderDetailDao orderDetailDAO = new OrderDetailDao();
    private final BranchDao branchDAO = new BranchDao();
    private final DrinkDao drinkDAO = new DrinkDao();
    private final PaymentDao paymentDAO = new PaymentDao();
    private final CustomerDao customerDAO = new CustomerDao();


    @Override
    public int placeOrder(Order order, List<OrderDetail> orderDetails) {
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
    public List<String> getAllBranchNames() {
        return branchDAO.getAllBranchLocations();
    }

    @Override
    public List<String> getDrinkTypesByCategory(String keyword) {
        return drinkDAO.getDrinkNamesByKeyword(keyword);
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
    public Order getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    @Override
    public String getCustomerNameById(int customerId) {
        return customerDAO.getCustomerNameById(customerId);
    }

    @Override
    public boolean savePayment(int orderId, String method, String code) {
        return paymentDAO.save(orderId, method, code);
    }
}