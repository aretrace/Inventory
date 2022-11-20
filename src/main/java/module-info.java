module com.example.inventory.controller {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.inventory.model;
    opens com.example.inventory.model to javafx.fxml;
    exports com.example.inventory.controller;
    opens com.example.inventory.controller to javafx.fxml;
    exports com.example.inventory.controller.traits;
    opens com.example.inventory.controller.traits to javafx.fxml;
}