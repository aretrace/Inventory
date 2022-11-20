package com.example.inventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.example.inventory.model.*;
import com.example.inventory.controller.traits.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartFormOutsourced extends AbstractNavigation implements Initializable, BindingUtils, CustomDialogs {

    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField companyNameTextField;
    @FXML private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateTextFields();
        formButtonEnabilizationFieldDependencies(
            saveButton,
                nameTextField,
                priceTextField,
                invTextField,
                maxTextField,
                minTextField,
                companyNameTextField
        );
    }

    private void populateTextFields() {
        Part part = Inventory.lookupPart(MainPanel.selectedPart.getId());
        idTextField.setText(Integer.toString(MainPanel.selectedPart.getId()));
        if (part instanceof Outsourced) {
            Outsourced outsourcedPart = (Outsourced) part;
            companyNameTextField.setText(outsourcedPart.getCompanyName());
        }
        nameTextField.setText(part.getName());
        priceTextField.setText(String.valueOf(part.getPrice()));
        invTextField.setText(String.valueOf(part.getStock()));
        minTextField.setText(String.valueOf(part.getMin()));
        maxTextField.setText(String.valueOf(part.getMax()));
    }

    public void modifyOutsourcedPartHandler(ActionEvent event) throws IOException {
        try {
            int inv = (int) Long.parseLong(invTextField.getText().strip());
            double price = Double.parseDouble(priceTextField.getText().strip());
            int min = (int) Long.parseLong(minTextField.getText().strip());
            int max = (int) Long.parseLong(maxTextField.getText().strip());
            if (!(min < max)) {
                displayInputErrorDialog("Min must be LESS THAN Max!");
            } else if (!(min < inv)) {
                displayInputErrorDialog("Inv must be GRATER THAN Min!");
            } else if (!(max > inv)) {
                displayInputErrorDialog("Inv must be LESS THAN Max!");
            } else {
                Outsourced modifiedOutsourcedPart = new Outsourced(
                        MainPanel.selectedPart.getId(),
                        nameTextField.getText().strip(),
                        price,
                        inv,
                        min,
                        max,
                        companyNameTextField.getText().strip()
                );
                Inventory.updatePart(MainPanel.selectedPart.getId(), modifiedOutsourcedPart);
                changeView(event, "MainPanel", "Inventory Management System");
            }
        } catch (NumberFormatException e) {
            displayInputErrorDialog("Error: " + e.getMessage(), "Plz enter appropriate value types");
        }
    }
}
