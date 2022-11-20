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

public class AddPartFormOutsourced extends AbstractNavigation implements Initializable, BindingUtils, CustomDialogs {

    @FXML private TextField nameTextField;
    @FXML private TextField invTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField companyNameTextField;
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
                companyNameTextField
        );
    }

    public void addPartFromOutsourcedHandler(ActionEvent event) throws IOException {
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
                displayInputErrorDialog("Inv must be LESS THAN Max!"); // TODO: company name can be all digits if whitespace is present!
            } else if (companyNameTextField.getText().strip().chars().allMatch(Character::isDigit)) {
                displayInputErrorDialog("A Part name can be all numbers (why not), but a company name can't be all numbers!");
            } else {
                Outsourced newOutsourcedPart = new Outsourced(
                        MainPanel.partIndexCounter++,
                        nameTextField.getText().strip(),
                        price,
                        inv,
                        min,
                        max,
                        companyNameTextField.getText().strip()
                );
                Inventory.addPart(newOutsourcedPart);
                changeView(event, "MainPanel", "Inventory Management System");
            }
        } catch (NumberFormatException e) {
            displayInputErrorDialog("Error: " + e.getMessage(), "Plz enter appropriate value types");
        }
    }
}
