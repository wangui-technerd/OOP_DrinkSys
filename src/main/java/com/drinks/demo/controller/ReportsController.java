package com.drinks.demo.controller;
import com.drinks.demo.utilities.DBHelper;
import com.drinks.demo.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.drinks.demo.Main;
import java.util.HashMap;
import java.util.List;




public class ReportsController {

    @FXML
    private Button reportButton;

    @FXML
    private Label statusLabel;


    @FXML
    public void initialize() {


    }
    @FXML
    private void generateReport() {
        try{
            List<Order> orders = DBHelper.getAllOrders();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orders);

            JasperPrint print = JasperFillManager.fillReport(
                    getClass().getResourceAsStream("/reports/order_report.jasper"),
                    new HashMap<>(),
                    dataSource
            );

            JasperViewer.viewReport(print, false);
            statusLabel.setText("Report generated successfully.");
        } catch (JRException e) {
            e.printStackTrace();
            statusLabel.setText("Failed to generate report.");
        }
    }
    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
        }

