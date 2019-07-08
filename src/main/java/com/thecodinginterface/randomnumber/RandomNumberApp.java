// RandomNumberApp.java

package com.thecodinginterface.randomnumber;

import com.thecodinginterface.randomnumber.controllers.FrontController;
import com.thecodinginterface.randomnumber.repository.LocalRandomNumberDAO;
import com.thecodinginterface.randomnumber.repository.RandomNumberDAO;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RandomNumberApp extends Application {

    @Override
    public void start(Stage stage) {
        FrontController frontController = FrontController.getInstance();
        frontController.setStage(stage);
        RandomNumberDAO randomNumberDAO = new LocalRandomNumberDAO();
        randomNumberDAO.loadNumbers();
        frontController.setRandomNumberDAO(randomNumberDAO);
        frontController.showNumberGeneratorView();
        frontController.showStage();
    }

    public static void main(String[] args) {
        launch();
    }

}
