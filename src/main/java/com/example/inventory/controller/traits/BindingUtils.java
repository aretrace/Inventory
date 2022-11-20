package com.example.inventory.controller.traits;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import java.util.stream.Stream;

// TODO: implement
public interface BindingUtils {
    // TODO: make generic for any type of scene Control
     default void formButtonEnabilizationFieldDependencies(Button button, TextField ... textFields) {
        // This:
        ObservableBooleanValue anyAreEmpty = Bindings.createBooleanBinding(() ->
            Stream.of(textFields).anyMatch((textField) -> textField.textProperty().isEmpty().get()),
            Stream.of(textFields).map(TextInputControl::textProperty).toArray(Observable[]::new)
        );
        button.disableProperty().bind(anyAreEmpty);
        // Instead of this:
        /*button.disableProperty().bind(
                Bindings.createBooleanBinding(() -> nameTextField.getText().strip().isEmpty(), nameTextField.textProperty())
                        .or(Bindings.createBooleanBinding(() -> priceTextField.getText().strip().isEmpty(), priceTextField.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> invTextField.getText().strip().isEmpty(), invTextField.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> maxTextField.getText().strip().isEmpty(), maxTextField.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> maxTextField.getText().strip().isEmpty(), minTextField.textProperty()))
                        .or(Bindings.createBooleanBinding(() -> machineIdTextField.getText().strip().isEmpty(), machineIdTextField.textProperty()))
        );*/
        // Perhaps: https://stackoverflow.com/questions/32192963/multiple-boolean-binding-in-javafx
    }
}
