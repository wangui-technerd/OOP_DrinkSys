package com.drinks.demo;

import com.drinks.demo.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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

    private Scene styledScene(AnchorPane view) {
        Scene scene = new Scene(view);
        scene.getStylesheets().add(getClass().getResource("/com/drinks/demo/css/style.css").toExternalForm());
        return scene;
    }
    private Scene styledScene(Parent view) {
        Scene scene = new Scene(view);
        scene.getStylesheets().add(getClass().getResource("/com/drinks/demo/css/style.css").toExternalForm());
        return scene;
    }

    public void showWelcomeScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/Welcome.fxml"));
            AnchorPane welcomeView = loader.load();
            WelcomeController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(styledScene(welcomeView));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/LoginForm.fxml"));
            AnchorPane loginView = loader.load();
            LoginController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(styledScene(loginView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/RegistrationForm.fxml"));
            AnchorPane registerView = loader.load();
            RegistrationController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(styledScene(registerView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCustomerView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/CustomerDash.fxml"));
            AnchorPane customerView = loader.load();
            CustomerDashController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(styledScene(customerView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showBranchView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/BranchDash.fxml"));
            AnchorPane branchView = loader.load();
            BranchDashController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(styledScene(branchView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAdminView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/AdminDash.fxml"));
            AnchorPane adminView = loader.load();
            AdminDashController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setScene(styledScene(adminView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showOrderView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/drinks/demo/fxml/OrderForm.fxml"));
            Parent orderView = loader.load();
            OrderFormController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.setScene(styledScene(orderView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAdminDash() {

    }
    public static void main(String[] args) {
        launch(args);
    }


}
