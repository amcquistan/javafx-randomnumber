// RandomNumberApp.java

package com.thecodinginterface.randomnumber;

import java.io.IOException;

import com.thecodinginterface.randomnumber.controllers.FrontController;
import com.thecodinginterface.randomnumber.repository.LocalRandomNumberDAO;
import com.thecodinginterface.randomnumber.repository.RandomNumberDAO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class RandomNumberApp extends Application {

    @Override
    public void start(Stage stage) {
        // Leaving this line for reference
        // FrontController frontController = FrontController.getInstance();
        
        var fxmlLoader = new FXMLLoader(getClass().getResource("views/Base.fxml"));
        try {
            // load can throw an IOException if their is an issue with the FXML file
            fxmlLoader.load();
            var frontController = (FrontController) fxmlLoader.getController();
            frontController.setStage(stage);
            RandomNumberDAO randomNumberDAO = new LocalRandomNumberDAO();
            randomNumberDAO.loadNumbers();
            frontController.setRandomNumberDAO(randomNumberDAO);
            frontController.showNumberGeneratorView();
            frontController.showStage();
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
