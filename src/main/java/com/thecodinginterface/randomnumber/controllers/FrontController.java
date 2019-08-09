// FrontController.java

package com.thecodinginterface.randomnumber.controllers;

import java.io.IOException;
import java.net.URL;

import com.thecodinginterface.randomnumber.RandomNumberApp;
import com.thecodinginterface.randomnumber.repository.RandomNumberDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FrontController {
    public static final double APP_WIDTH = 500;
    public static final double APP_HEIGHT = 350;

    private RandomNumberDAO randomNumberDAO;

    private Stage primaryStage;

    @FXML
    private BorderPane rootBorderPane;

    @FXML
    private Label logoLbl;

    @FXML
    private ImageView logoImgView;

    @FXML
    private ToggleButton generateNumbersBtn;

    @FXML
    private ToggleButton viewNumbersBtn;
    
    public void initialize() {
        var toggleGroup = new ToggleGroup();
        generateNumbersBtn.setToggleGroup(toggleGroup);
        viewNumbersBtn.setToggleGroup(toggleGroup);
        generateNumbersBtn.setSelected(true);
        
        generateNumbersBtn.setOnAction(evt -> showNumberGeneratorView());
        viewNumbersBtn.setOnAction(evt -> showNumbersListView());
        
        logoImgView.setImage(new Image(
            RandomNumberApp.class.getResourceAsStream("images/TCI-Logo.png")
        ));
        logoImgView.setPreserveRatio(true);
        logoImgView.setFitWidth(58);
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        var scene = new Scene(rootBorderPane, APP_WIDTH, APP_HEIGHT);
        URL url = RandomNumberApp.class.getResource("styles/styles.css");
        scene.getStylesheets().add(url.toExternalForm());
        this.primaryStage.setScene(scene);
    }

    public void setRandomNumberDAO(RandomNumberDAO randomNumberDAO) {
        this.randomNumberDAO = randomNumberDAO;
    }

    public RandomNumberDAO getRandomNumberDAO() {
        return randomNumberDAO;
    }

    public void showNumberGeneratorView() {
        var fxmlLoader = new FXMLLoader(RandomNumberApp.class.getResource("views/NumberGenerator.fxml"));
        try {
            var anchorPane = (AnchorPane) fxmlLoader.load();
            var ctrl = (NumberGeneratorController) fxmlLoader.getController();
            ctrl.setFrontController(this);
            rootBorderPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNumbersListView() {
        var fxmlLoader = new FXMLLoader(RandomNumberApp.class.getResource("views/NumbersView.fxml"));
        try {
            var anchorPane = (AnchorPane) fxmlLoader.load();
            var ctrl = (NumbersViewController) fxmlLoader.getController();
            ctrl.setFrontController(this);
            rootBorderPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        if (!primaryStage.isShowing()) {
            primaryStage.show();
        }
    }
}
