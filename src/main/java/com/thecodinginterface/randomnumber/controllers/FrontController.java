// FrontController.java

package com.thecodinginterface.randomnumber.controllers;

import com.thecodinginterface.randomnumber.repository.RandomNumberDAO;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FrontController {
    public static final double APP_WIDTH = 300;
    public static final double APP_HEIGHT = 250;

    private static final FrontController INSTANCE = new FrontController();

    private RandomNumberDAO randomNumberDAO;

    private Stage primaryStage;
    private BorderPane rootBorderPane;

    private FrontController() {
        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(makeMenuBar());
    }

    public static FrontController getInstance() {
        return INSTANCE;
    }

    private MenuBar makeMenuBar() {
        var menuBar = new MenuBar();
        var numGeneratorMenuItem = new Menu("Generate Number");
        var numListingMenuItem = new Menu("View Numbers");

        numGeneratorMenuItem.setOnAction(evt -> { showNumberGeneratorView(); });
        numListingMenuItem.setOnAction(evt -> { showNumbersListView(); });

        menuBar.getMenus().addAll(numGeneratorMenuItem, numListingMenuItem);

        return menuBar;
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setScene(new Scene(rootBorderPane, APP_WIDTH, APP_HEIGHT));
    }

    public void setRandomNumberDAO(RandomNumberDAO randomNumberDAO) {
        this.randomNumberDAO = randomNumberDAO;
    }

    public RandomNumberDAO getRandomNumberDAO() {
        return randomNumberDAO;
    }

    public void showNumberGeneratorView() {
        NumberGeneratorController ctrl = new NumberGeneratorController();
        ctrl.setFrontController(this);
        rootBorderPane.setCenter(ctrl.getContentPane());
    }

    public void showNumbersListView() {
        NumbersViewController ctrl = new NumbersViewController();
        ctrl.setFrontController(this);
        rootBorderPane.setCenter(ctrl.getContentPane());
    }

    public void showStage() {
        if (!primaryStage.isShowing()) {
            primaryStage.show();
        }
    }
}
