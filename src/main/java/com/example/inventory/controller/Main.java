package com.example.inventory.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/inventory/view/MainPanel.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root); // (root, width, height)
        stage.setScene(scene);
        stage.setTitle("Inventory");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}