package com.drinks.demo;

import com.drinks.demo.controller.LoginController;
import com.drinks.demo.controller.WelcomeController;
import com.drinks.demo.controller.RegistrationController;
import com.drinks.demo.controller.AdminDashController;
import com.drinks.demo.controller.CustomerDashController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Welcome");
        showWelcomeScreen();
    }

    public void showWelcomeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
            AnchorPane welcomeView = loader.load();
            WelcomeController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(welcomeView));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            AnchorPane loginView = loader.load();
            LoginController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(loginView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
            AnchorPane registerView = loader.load();
            RegistrationController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(registerView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_dashboard.fxml"));
            AnchorPane view = loader.load();
            AdminDashController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(view));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCustomerDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/customer_dashboard.fxml"));
            AnchorPane view = loader.load();
            CustomerDashController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(view));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCustomerView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CustomerView.fxml"));
            AnchorPane customerView = loader.load();
            primaryStage.setScene(new Scene(customerView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showBranchView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BranchView.fxml"));
            AnchorPane branchView = loader.load();
            primaryStage.setScene(new Scene(branchView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showAdminView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_dashboard.fxml"));
            AnchorPane adminView = loader.load();
            AdminDashController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(adminView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}