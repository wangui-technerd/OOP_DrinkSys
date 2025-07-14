package com.drinks.demo.utilities;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.drinks.demo.model.Order;           // ✅ correct Order class
import com.drinks.demo.utilities.DBHelper;   // ✅ DB helper with getAllOrders()

import java.util.HashMap;
import java.util.List;

public class ReportCompiler {

    public static void main(String[] args) {
        try {
            // Compile the .jrxml file
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    ReportCompiler.class.getResourceAsStream("/reports/order_report.jrxml")
            );

            // ✅ This returns List<com.drinks.demo.model.Order>
            List<Order> orders = DBHelper.getAllOrders();

            // Wrap into data source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orders);

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    new HashMap<>(),
                    dataSource
            );

            // Export to PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, "compiled_order_report.pdf");

            System.out.println("✅ Report generated: compiled_order_report.pdf");

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
