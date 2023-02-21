package com.example.interprsmr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import view.gui.MainWindowController;
import view.gui.SelectWindowController;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage firstStage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("C:\\Users\\rasni\\Desktop\\Interprsmr\\src\\main\\java\\view\\gui\\MainWindow.fxml"));
        Parent mainWindow = mainLoader.load();

        MainWindowController mainWindowController = mainLoader.getController();

        firstStage.setTitle("Welcome!!This is the main window.");
        firstStage.setScene(new Scene(mainWindow));
        firstStage.show();

        FXMLLoader secondaryLoader = new FXMLLoader();
        secondaryLoader.setLocation(getClass().getResource("C:\\Users\\rasni\\Desktop\\Interprsmr\\src\\main\\java\\view\\gui\\SelectWindow.fxml"));
        Parent secondaryWindow = secondaryLoader.load();

        SelectWindowController selectWindowController = secondaryLoader.getController();
        selectWindowController.setMainWindowController(mainWindowController);

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Select Window");
        secondaryStage.setScene(new Scene(secondaryWindow));
        secondaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

