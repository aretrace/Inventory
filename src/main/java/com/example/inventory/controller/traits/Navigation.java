package com.example.inventory.controller.traits;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// See AbstractNavigation class for glue code details
public interface Navigation {

    @FXML
    default void changeView(ActionEvent event, String viewName, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/inventory/view/" + viewName + ".fxml"));
        Parent view = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    @FXML
    default void toMainPanelViewHandler(ActionEvent event) throws IOException {
        changeView(event, "MainPanel", "Inventory Management System");
    }

    /* TODO: separate out tangible materials to their own interfaces and make respective class controllers implement them */

    /* Parts stuff */

    @FXML
    default void toAddPartFromOutsourcedViewHandler(ActionEvent event) throws IOException {
        changeView(event, "AddPartOutsourced", "Add Part (Outsourced)");
    }

    @FXML
    default void toAddPartInHouseViewHandler(ActionEvent event) throws IOException {
        changeView(event, "AddPartInHouse", "Add Part (In-House)");
    }

    @FXML
    default void toModifyPartInHouseViewHandler(ActionEvent event) throws IOException {
        changeView(event, "ModifyPartInHouse", "Modify Part (In-House)");
    }

    @FXML
    default void toModifyPartOutsourcedViewHandler(ActionEvent event) throws IOException {
        changeView(event, "ModifyPartOutsourced", "Modify Part (Outsourced)");
    }

    /* Products stuff */

    @FXML
    default void toAddProductViewHandler(ActionEvent event) throws IOException {
        changeView(event, "AddProduct", "Add Product");
    }

    @FXML
    default void toModifyProductViewHandler(ActionEvent event) throws IOException {
        changeView(event, "ModifyProduct", "Modify Product");
    }

}
