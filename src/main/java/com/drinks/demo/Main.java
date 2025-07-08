package com.drinks.demo;

import com.drinks.demo.controller.WelcomeController;
import  javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Welcome");
        showWelcomeScreen(); // ✅ new entry point
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

//    public void showLoginView() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
//            AnchorPane loginView = loader.load();
//            LoginController controller = loader.getController();
//            controller.setMainApp(this);
//            primaryStage.setScene(new Scene(loginView));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void showRegisterView() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
//            AnchorPane registerView = loader.load();
//            RegisterController controller = loader.getController();
//            controller.setMainApp(this);
//            primaryStage.setScene(new Scene(registerView));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void showAdminDashboard() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_dashboard.fxml"));
//            AnchorPane view = loader.load();
//            AdminDashboardController controller = loader.getController();
//            controller.setMainApp(this);
//            primaryStage.setScene(new Scene(view));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void showCustomerDashboard() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/customer_dashboard.fxml"));
//            AnchorPane view = loader.load();
//            CustomerDashboardController controller = loader.getController();
//            controller.setMainApp(this);
//            primaryStage.setScene(new Scene(view));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}

}
