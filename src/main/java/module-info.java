module com.example.systemanalize {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.systemanalize to javafx.fxml;
    exports com.example.systemanalize;
}