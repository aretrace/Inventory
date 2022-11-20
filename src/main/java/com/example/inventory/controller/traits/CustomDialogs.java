package com.example.inventory.controller.traits;

// If you do not specify that the interface is public,
// then your interface is accessible only to classes defined in the same package as the interface.

import javafx.scene.control.Alert;

// TODO: implement non thread blocking dialogs for JPro port!
// https://stackoverflow.com/questions/33839272/javafx-create-alert-and-get-result
// https://stackoverflow.com/questions/53853156/javafx-non-blocking-dialog-to-process-result-when-ok-clicked
public interface CustomDialogs {

    // TODO: implement and use
    default void coreDialogAdapter(Alert.AlertType alertType) {

    }

    // optional and/or default parameters would be nice Java
    default void displayInputErrorDialog(String contentMessage) {
        System.out.println(contentMessage);
        /*Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("For Your Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(contentMessage);
        alert.showAndWait();*/
    }

    default void displayInputErrorDialog(String contentMessage, String headerMessage) {
        System.out.println(headerMessage + " " + contentMessage);
        /*Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("For Your Information Dialog");
        alert.setHeaderText(headerMessage);
        alert.setContentText(message);
        alert.showAndWait();*/
    }

    default void displayWarningDialog(String contentMessage) {
        System.out.println(contentMessage);
        /*Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Caution!");
        alert.setHeaderText(null);
        alert.setContentText(contentMessage);
        alert.showAndWait();*/
    }

    // TODO: implement and place
    default void displayConformationDialog() {
        // Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ...
    }

}
