package com.drinks.demo.service;

import com.drinks.demo.database.AdminDAO;

public class AdminServiceImpl implements AdminService {
    private final AdminDAO adminDAO = new AdminDAO();

    @Override
    public boolean validateAdmin(String username, String password) {
        return adminDAO.validateAdmin(username, password);
    }
}
