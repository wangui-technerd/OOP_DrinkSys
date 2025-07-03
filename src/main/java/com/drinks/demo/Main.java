package com.drinks.demo;

import com.drinks.demo.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Login");
        showWelcomeScreen();
    }

    public void showWelcomeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/welcome.fxml"));
            Parent welcomeView = loader.load();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/LoginForm.fxml"));
            Parent loginView = loader.load();
            LoginController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(loginView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/RegistrationForm.fxml"));
            Parent registerView = loader.load();
            RegistrationController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(registerView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAdminDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/Admin.fxml"));
            Parent view = loader.load();
            AdminController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(view));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCustomerDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/Customer.fxml"));
            Parent view = loader.load();
            CustomerController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(new Scene(view));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
