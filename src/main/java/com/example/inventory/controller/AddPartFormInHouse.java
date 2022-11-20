package com.example.inventory.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.example.inventory.model.*;
import com.example.inventory.controller.traits.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartFormInHouse extends AbstractNavigation implements Initializable, BindingUtils, CustomDialogs {

    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField machineIdTextField;
    @FXML private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void addPartFromInHouseHandler(ActionEvent event) throws IOException {
        try {
            int inv = (int) Long.parseLong(invTextField.getText().strip());
            double price = Double.parseDouble(priceTextField.getText().strip());
            int min = (int) Long.parseLong(minTextField.getText().strip());
            int max = (int) Long.parseLong(maxTextField.getText().strip());
            int machineId = (int) Long.parseLong(machineIdTextField.getText().strip());
            if (!(min < max)) {
                displayInputErrorDialog("Min must be LESS than Max");
            } else if (!(min < inv)) {
                displayInputErrorDialog("Inv must be GRATER THAN Min!");
            } else if (!(max > inv)) {
                displayInputErrorDialog("Inv must be LESS THAN Max!");
            } else {
                InHouse newInHousePart = new InHouse(
                        MainPanel.partIndexCounter++,
                        nameTextField.getText().strip(),
                        price,
                        inv,
                        min,
                        max,
                        machineId
                );
                Inventory.addPart(newInHousePart);
                changeView(event, "MainPanel", "Inventory Management System");
            }
        } catch (NumberFormatException e) {
            displayInputErrorDialog("Error: " + e.getMessage(), "Plz enter appropriate value types");
        }
    }
}
