// FrontController.java

package com.thecodinginterface.randomnumber.controllers;

import java.net.URL;

import com.thecodinginterface.randomnumber.RandomNumberApp;
import com.thecodinginterface.randomnumber.repository.RandomNumberDAO;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FrontController {
    public static final double APP_WIDTH = 500;
    public static final double APP_HEIGHT = 350;

    private static final FrontController INSTANCE = new FrontController();

    private RandomNumberDAO randomNumberDAO;

    private Stage primaryStage;
    private BorderPane rootBorderPane;

    private FrontController() {
        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(makeNavBar());
    }

    public static FrontController getInstance() {
        return INSTANCE;
    }

    private HBox makeNavBar() {
        var generateNumbersBtn = new ToggleButton("Generate Number");
        var viewNumbersBtn = new ToggleButton("View Numbers");
        var toggleGroup = new ToggleGroup();
        generateNumbersBtn.setToggleGroup(toggleGroup);
        viewNumbersBtn.setToggleGroup(toggleGroup);
        generateNumbersBtn.setSelected(true);
        
        generateNumbersBtn.getStyleClass().add("navbar-btn");
        viewNumbersBtn.getStyleClass().add("navbar-btn");
        generateNumbersBtn.setOnAction(evt -> showNumberGeneratorView());
        viewNumbersBtn.setOnAction(evt -> showNumbersListView());
        
        var imageView = new ImageView(new Image(RandomNumberApp.class.getResourceAsStream("images/TCI-Logo.png")));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(58);
        var logoLbl = new Label("", imageView);
        logoLbl.getStyleClass().add("navbar-logo");
        var hbox = new HBox(logoLbl, generateNumbersBtn, viewNumbersBtn);
        hbox.getStyleClass().add("navbar");
        
        return hbox;
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
        updateContent(new NumberGeneratorController());
    }

    public void showNumbersListView() {
        updateContent(new NumbersViewController());
    }
    
    private void updateContent(BaseController ctrl) {
        ctrl.setFrontController(this);
        rootBorderPane.setCenter(ctrl.getContentPane());
    }

    public void showStage() {
        if (!primaryStage.isShowing()) {
            primaryStage.show();
        }
    }
}
