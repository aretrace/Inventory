package com.example.inventory.controller;

import javafx.application.Platform;
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

public class MainPanel extends AbstractNavigation implements Initializable, CustomDialogs {

    @FXML private TextField partSearchField;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn; // Bug 4487555, (println "Choose freedom, choose Clojure!\n")
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TableColumn<Part, Integer> partStockColumn;
    protected static Part selectedPart;
    @FXML private Button searchPartButton;
    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button deletePartButton;
    public static int partIndexCounter = 1;

    @FXML private TextField productSearchField;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private TableColumn<Product, Integer> productStockColumn;
    protected static Product selectedProduct;
    @FXML private Button searchProductButton;
    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;
    public static int productIndexCounter = 1;

    private void setSelectedPart() {
        MainPanel.selectedPart = partTableView.getSelectionModel().getSelectedItem();
    }

    private void setSelectedProduct() {
         MainPanel.selectedProduct = productTableView.getSelectionModel().getSelectedItem();
    }

    // First searches for names in Part, if non found then checks if search text consists of all digits,
    // if so, searches for ID in Part.
    /**
     * TODO: Make generic search method for both parts and products
     * [RUNTIME ERROR] Fixed search results in cases where string consisted of only whitespace
     */
    public void getPartSearch() {
        if (partSearchField.getText().isBlank()) {
            partTableView.setItems(Inventory.getAllParts());
            partSearchField.setText("");
            return;
        }
        partTableView.setItems(Inventory.lookupPart(partSearchField.getText().strip()));
        if (partTableView.getItems().isEmpty()) {
            if (partSearchField.getText().chars().allMatch(Character::isDigit)) {
                ObservableList<Part> partIDSearch = FXCollections.observableArrayList();
                Part returnedPart = Inventory.lookupPart((int) Long.parseLong(partSearchField.getText()));
                if (returnedPart != null) {
                    partIDSearch.add(returnedPart);
                    partTableView.setItems(partIDSearch);
                } else {
                    /*Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Search Result");
                    alert.setHeaderText("No Part of that ID found!");
                    alert.setContentText("\"One’s own self is well hidden from one’s own self;\n" +
                            " of all mines of treasure, one’s own is the last to be dug up.\"\n – Friedrich Nietzsche");
                    alert.showAndWait();*/
                    partTableView.setItems(Inventory.getAllParts());
                }
            } else {
                /*Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Search Result");
                alert.setHeaderText("No Part of that NAME found!");
                alert.setContentText("\"One’s own self is well hidden from one’s own self;\n" +
                        " of all mines of treasure, one’s own is the last to be dug up.\"\n – Friedrich Nietzsche");
                alert.showAndWait();*/
                partTableView.setItems(Inventory.getAllParts());
            }
        }
        partSearchField.setText("");
    }

    /**
     * TODO: Make generic search method for both parts and products
     */
    public void getProductSearch() {
        if (productSearchField.getText().isBlank()) {
            productTableView.setItems(Inventory.getAllProducts());
            productSearchField.setText("");
            return;
        }
        productTableView.setItems(Inventory.lookupProduct(productSearchField.getText().strip()));
        if (productTableView.getItems().isEmpty()) {
            if (productSearchField.getText().chars().allMatch(Character::isDigit)) {
                ObservableList<Product> productIDSearch = FXCollections.observableArrayList();
                Product returnedProduct = Inventory.lookupProduct((int) Long.parseLong(productSearchField.getText()));
                if (returnedProduct != null) {
                    productIDSearch.add(returnedProduct);
                    productTableView.setItems(productIDSearch);
                } else {
                    /*Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Search Result");
                    alert.setHeaderText("No Product of that ID found!");
                    alert.setContentText("\"One’s own self is well hidden from one’s own self;\n" +
                            " of all mines of treasure, one’s own is the last to be dug up.\"\n – Friedrich Nietzsche");
                    alert.showAndWait();*/
                    productTableView.setItems(Inventory.getAllProducts());
                }
            } else {
                /*Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Search Result");
                alert.setHeaderText("No Product of that NAME found!");
                alert.setContentText("\"One’s own self is well hidden from one’s own self;\n" +
                        " of all mines of treasure, one’s own is the last to be dug up.\"\n – Friedrich Nietzsche");
                alert.showAndWait();*/
                productTableView.setItems(Inventory.getAllProducts());
            }
        }
        productSearchField.setText("");
    }

    /**
     * TODO: Properly implement and support multiple selection and implement binding utilities
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // -- Part Table --
        partTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        partSearchField.setFocusTraversable(false);

        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>( "id" )); // Names are from Object property fields
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));

        partTableView.setItems(Inventory.getAllParts());

        searchPartButton.disableProperty().bind(Bindings.isEmpty(partTableView.getItems()));
        partSearchField.disableProperty().bind(Bindings.isEmpty(partTableView.getItems()));
        modifyPartButton.disableProperty().bind(Bindings.isNull(partTableView.getSelectionModel().selectedItemProperty()));
        deletePartButton.disableProperty().bind(Bindings.isNull(partTableView.getSelectionModel().selectedItemProperty()));

        // -- Product Table --
        productTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>( "id" ));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));

        productTableView.setItems(Inventory.getAllProducts());

        searchProductButton.disableProperty().bind(Bindings.isEmpty(productTableView.getItems()));
        productSearchField.disableProperty().bind(Bindings.isEmpty(productTableView.getItems()));
        modifyProductButton.disableProperty().bind(Bindings.isNull(productTableView.getSelectionModel().selectedItemProperty()));
        deleteProductButton.disableProperty().bind(Bindings.isNull(productTableView.getSelectionModel().selectedItemProperty()));

        // TODO: Make no nodes in focus by default
        // Perhaps setFocusTraversable(false) on all nodes?
        // https://stackoverflow.com/a/29058225
        Platform.runLater(() -> addPartButton.requestFocus());
    }

    // ------------------------------------------------------ Part ------------------------------------------------------

    /**
     * TODO: Make more concise?
     */
    public void modifyPartSelectionTypeHandler(ActionEvent event) throws IOException {
        setSelectedPart();
        if (partTableView.getSelectionModel().getSelectedItem() instanceof InHouse) {
            toModifyPartInHouseViewHandler(event);
        }
        if (partTableView.getSelectionModel().getSelectedItem() instanceof Outsourced) {
            toModifyPartOutsourcedViewHandler(event);
        }
    }

    /**
     * TODO: Make deleting a part who has been assigned to a product not possible
     */
    public void deleteSelectedPartHandler() {
        setSelectedPart();
        // TODO: Properly abstract Alerts via a trait, use non thread blocking dialogs for JPro port!
        for (Product product : Inventory.getAllProducts()) {
            for (Part part : product.getAllAssociatedParts()) {
                if (MainPanel.selectedPart.getId() == part.getId()) {
                    displayInputErrorDialog("Accepting to invoke a nonsensical application state ehh?\n" +
                            "A Product happens to have this Part associated with it, but\nsince \"A product's associated " +
                            "parts can exist independent of\ncurrent inventory of parts\" your delete will be respected.", "Nice Try");
                }
            }
        }
        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Caution!");
        alert.setHeaderText(null);
        alert.setContentText("ELIMINATE " + MainPanel.selectedPart.getName() + " ?!?");*/
        //if (alert.showAndWait().get() == ButtonType.OK) {
            if (MainPanel.selectedPart != null) {
                Inventory.deletePart(MainPanel.selectedPart);
                partTableView.setItems(Inventory.getAllParts());
            }
        //}
    }


    // ------------------------------------------------------ Product ------------------------------------------------------

    // TODO: Find a better way to call a function before invoking a method inherited from a abstract superclass
    // (or a default method in an implemented interface as it is in this case)
    public void modifyProductSelectionTypeHandler(ActionEvent event) throws IOException {
        setSelectedProduct();
        toModifyProductViewHandler(event);
    }

    /**
     * TODO: Disable deleteProductButton for a product who has an associated part
     * TODO: Part deletion and Product deletion are not handled the same way, why?
     * TODO: Could material deletion functions be abstracted into a trait?
     */
    public void deleteSelectedProductHandler() {
        Product selectedRow = productTableView.getSelectionModel().getSelectedItem();
        // TODO: Properly abstract Alerts via a trait, use non thread blocking dialogs for JPro port!
        if (!selectedRow.getAllAssociatedParts().isEmpty()) {
            displayWarningDialog(selectedRow.getName() + " has associated Parts!");
            return;
        }
        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Caution!");
        alert.setHeaderText(null);
        alert.setContentText("ELIMINATE " + selectedRow.getName() + " ?!?");*/
        //if (alert.showAndWait().get() == ButtonType.OK) {
            if (selectedRow != null) {
                Inventory.deleteProduct(selectedRow);
                productTableView.setItems(Inventory.getAllProducts());
            } else {
                productTableView.setItems(Inventory.getAllProducts());
                // TODO: some dialog here
            }
        //}
    }
}