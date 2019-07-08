// RandomNumberApp.java

package com.thecodinginterface.randomnumber;

import com.thecodinginterface.randomnumber.controllers.FrontController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RandomNumberApp extends Application {

    @Override
    public void start(Stage stage) {
        FrontController frontController = FrontController.getInstance();
        frontController.setStage(stage);
        frontController.showNumberGeneratorView();
        frontController.showStage();
    }

    public static void main(String[] args) {
        launch();
    }

}
