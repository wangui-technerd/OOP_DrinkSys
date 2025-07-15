module com.drinks.demo {
    requires javafx.controls;
    requires javafx.fxml;

//    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    requires jasperreports;

    opens com.drinks.demo.controller to javafx.fxml;
    opens com.drinks.demo.model to javafx.base;

    exports com.drinks.demo;
    exports com.drinks.demo.controller;
}

