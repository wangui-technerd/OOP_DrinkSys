module com.drinks.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.drinks.demo to javafx.fxml;
    exports com.drinks.demo;
}