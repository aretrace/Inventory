package com.example.inventory.controller;

import com.example.inventory.controller.traits.AbstractNavigation;
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

public class ModifyPartFormInHouse extends AbstractNavigation implements Initializable, BindingUtils, CustomDialogs {

    @FXML private TextField idTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField machineIdTextField;
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
                machineIdTextField
        );
    }

    /**
     * TODO: Should this be moved to initialize()?
     */
    private void populateTextFields() {
        Part part = Inventory.lookupPart(MainPanel.selectedPart.getId());
        idTextField.setText(Integer.toString(MainPanel.selectedPart.getId()));
        if (part instanceof InHouse) {
            InHouse inHousePart = (InHouse) part; // TODO: What is a pattern variable?
            machineIdTextField.setText(String.valueOf(inHousePart.getMachineId()));
        }
        nameTextField.setText(part.getName()); // TODO: Fix NullPointerException warning
        priceTextField.setText(String.valueOf(part.getPrice()));
        invTextField.setText(String.valueOf(part.getStock()));
        minTextField.setText(String.valueOf(part.getMin()));
        maxTextField.setText(String.valueOf(part.getMax()));
    }

    public void modifyInHousePartHandler(ActionEvent event) throws IOException {
        try {
            int inv = (int) Long.parseLong(invTextField.getText().strip());
            double price = Double.parseDouble(priceTextField.getText().strip());
            int min = (int) Long.parseLong(minTextField.getText().strip());
            int max = (int) Long.parseLong(maxTextField.getText().strip());
            int machineId = (int) Long.parseLong(machineIdTextField.getText().strip());
            if (!(min < max)) {
                displayInputErrorDialog("Min must be LESS THAN Max!");
            } else if (!(min < inv)) {
                displayInputErrorDialog("Inv must be GRATER THAN Min!");
            } else if (!(max > inv)) {
                displayInputErrorDialog("Inv must be LESS THAN Max!");
            } else {
                InHouse modifiedInHousePart = new InHouse(
                        MainPanel.selectedPart.getId(),
                        nameTextField.getText().strip(),
                        price,
                        inv,
                        min,
                        max,
                        machineId
                );
                Inventory.updatePart(MainPanel.selectedPart.getId(), modifiedInHousePart);
                changeView(event, "MainPanel", "Inventory Management System");
            }
        } catch (NumberFormatException e) {
            displayInputErrorDialog("Error: " + e.getMessage(), "Plz enter appropriate value types");
        }
    }
}
