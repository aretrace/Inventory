package com.example.inventory.controller.traits;

import com.example.inventory.model.Product;
import javafx.event.ActionEvent;

import java.io.IOException;

// This is glue code that allows Navigation interface default methods to be used as
// controller method event handlers defined in FXML documents, this has to be done
// because of a limitation of the current implementation of FXMLLoader.
// See: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8259916
// TODO: perhaps contact jonathan.giles@oracle.com for a fix, https://tinyurl.com/39dj86xy

public abstract class AbstractNavigation implements Navigation {

    public void toMainPanelViewHandler(ActionEvent event) throws IOException {
        Navigation.super.toMainPanelViewHandler(event);
    }

    /* Part stuff */

    public void toAddPartFromOutsourcedViewHandler(ActionEvent event) throws IOException {
        Navigation.super.toAddPartFromOutsourcedViewHandler(event);
    }

    public void toAddPartInHouseViewHandler(ActionEvent event) throws IOException {
        Navigation.super.toAddPartInHouseViewHandler(event);
    }

    public void toModifyPartInHouseViewHandler(ActionEvent event) throws IOException {
        Navigation.super.toModifyPartInHouseViewHandler(event);
    }

    public void toModifyPartOutsourcedViewHandler(ActionEvent event) throws IOException {
        Navigation.super.toModifyPartOutsourcedViewHandler(event);
    }

    /* Product stuff */

    public void toAddProductViewHandler(ActionEvent event) throws IOException {
        Navigation.super.toAddProductViewHandler(event);
    }

    public void toModifyProductViewHandler(ActionEvent event) throws IOException {
        Navigation.super.toModifyProductViewHandler(event);
    }
}
