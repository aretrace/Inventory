package com.example.inventory.controller;

import com.example.inventory.controller.traits.AbstractNavigation;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.inventory.model.*;
import com.example.inventory.controller.traits.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProduct extends AbstractNavigation implements Initializable, BindingUtils, CustomDialogs {

    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private Button saveButton;

    @FXML private TextField partSearchField;
    @FXML private TableView<Part> allPartTableView;
    @FXML private TableColumn<Part, Integer> allPartIdColumn;
    @FXML private TableColumn<Part, String> allPartNameColumn;
    @FXML private TableColumn<Part, Double> allPartPriceColumn;
    @FXML private TableColumn<Part, Integer> allPartStockColumn;
    private static ObservableList<Part> selectedPartForAssociation = FXCollections.observableArrayList();
    @FXML private Button searchAllPartButton;
    @FXML private Button addAssociatedPartButton;


    @FXML private TableView<Part> associatedPartTableView;
    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;
    @FXML private TableColumn<Part, Integer> associatedPartStockColumn;
    @FXML private Button deleteAssociatedPartButton;

    private void setSelectedPartForAssociation() {
        if (selectedPartForAssociation.size() == 0) {
            AddProduct.selectedPartForAssociation.add(allPartTableView.getSelectionModel().getSelectedItem());
        } else {
            AddProduct.selectedPartForAssociation.set(0, allPartTableView.getSelectionModel().getSelectedItem());
        }
    }

    public void getPartSearch() {
        if (partSearchField.getText().isBlank()) {
            allPartTableView.setItems(Inventory.getAllParts());
            partSearchField.setText("");
            return;
        }
        allPartTableView.setItems(Inventory.lookupPart(partSearchField.getText().strip()));
        if (allPartTableView.getItems().isEmpty()) {
            if (partSearchField.getText().chars().allMatch(Character::isDigit)) {
                ObservableList<Part> partIDSearch = FXCollections.observableArrayList();
                Part returnedPart = Inventory.lookupPart((int) Long.parseLong(partSearchField.getText()));
                if (returnedPart != null) {
                    partIDSearch.add(returnedPart);
                    allPartTableView.setItems(partIDSearch);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Search Result");
                    alert.setHeaderText("No Part of that ID found!");
                    alert.setContentText("\"One’s own self is well hidden from one’s own self;\n" +
                    " of all mines of treasure, one’s own is the last to be dug up.\"\n – Friedrich Nietzsche");
                    alert.showAndWait();
                    allPartTableView.setItems(Inventory.getAllParts());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Search Result");
                alert.setHeaderText("No Part of that NAME found!");
                alert.setContentText("\"One’s own self is well hidden from one’s own self;\n" +
                " of all mines of treasure, one’s own is the last to be dug up.\"\n – Friedrich Nietzsche");
                alert.showAndWait();
                allPartTableView.setItems(Inventory.getAllParts());
            }
        }
        partSearchField.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allPartTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        allPartIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>( "id" ));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        allPartStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));

        allPartTableView.setItems(Inventory.getAllParts());

        searchAllPartButton.disableProperty().bind(Bindings.isEmpty(allPartTableView.getItems()));
        partSearchField.disableProperty().bind(Bindings.isEmpty(allPartTableView.getItems()));
        addAssociatedPartButton.disableProperty().bind(Bindings.isNull(allPartTableView.getSelectionModel().selectedItemProperty()));


        associatedPartTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>( "id" ));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));

        deleteAssociatedPartButton.disableProperty().bind(Bindings.isNull(associatedPartTableView.getSelectionModel().selectedItemProperty()));

        formButtonEnabilizationFieldDependencies(
            saveButton,
                nameTextField,
                priceTextField,
                invTextField,
                maxTextField,
                minTextField
        );
    }

    public void addProductHandler(ActionEvent event) throws IOException {
        try {
            int inv = (int) Long.parseLong(invTextField.getText().strip());
            double price = Double.parseDouble(priceTextField.getText().strip());
            int min = (int) Long.parseLong(minTextField.getText().strip());
            int max = (int) Long.parseLong(maxTextField.getText().strip());
            if (!(min < max)) {
                displayInputErrorDialog("Min must be LESS than Max");
            } else if (!(min < inv)) {
                displayInputErrorDialog("Inv must be GRATER THAN Min!");
            } else if (!(max > inv)) {
                displayInputErrorDialog("Inv must be LESS THAN Max!");
            } else {
                Product newProduct = new Product(
                        MainPanel.productIndexCounter++,
                        nameTextField.getText().strip(),
                        price,
                        inv,
                        min,
                        max
                );
                Inventory.addProduct(newProduct);
                if (!associatedPartTableView.getItems().isEmpty()) {
                    for (Part part : associatedPartTableView.getItems()) {
                        newProduct.addAssociatedPart(part);
                    }
                }
                changeView(event, "MainPanel", "Inventory Management System");
            }
        } catch (NumberFormatException e) {
            displayInputErrorDialog("Error: " + e.getMessage(), "Plz enter appropriate value types");
        }
    }

    /**
     * TODO: Delete associated part if part is deleted from main inventory
     */
    public void addAssociatedPartHandler() {
        setSelectedPartForAssociation();
        ObservableList<Part> tableItems = associatedPartTableView.getItems();
        for (Part part : associatedPartTableView.getItems()) {
            if (selectedPartForAssociation.get(0).getId() == part.getId()) {
                displayInputErrorDialog("Part has already been associated!");
                return;
            }
        }
        tableItems.add(selectedPartForAssociation.get(0));
        associatedPartTableView.setItems(tableItems);
        allPartTableView.setItems(Inventory.getAllParts());
    }

    public void deleteAssociatedPartHandler() {
        // displayConformationDialog(...);
        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Caution!");
        alert.setHeaderText(null);
        alert.setContentText("DISASSOCIATE " + associatedPartTableView.getSelectionModel().getSelectedItem().getName() + " ?!?");*/
        //if (alert.showAndWait().get() == ButtonType.OK) {
            associatedPartTableView.getItems().remove(associatedPartTableView.getSelectionModel().getSelectedItem());
        //}
    }
}
