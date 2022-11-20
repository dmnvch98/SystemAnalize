module com.example.systemanalize {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.systemanalize to javafx.fxml;
    exports com.example.systemanalize;
    exports com.example.systemanalize.controllers;
    opens com.example.systemanalize.controllers to javafx.fxml;
    exports com.example.systemanalize.configs;
    opens com.example.systemanalize.configs to javafx.fxml;
}