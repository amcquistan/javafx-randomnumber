// FrontController.java

package com.thecodinginterface.randomnumber.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FrontController {
    public static final double APP_WIDTH = 350;
    public static final double APP_HEIGHT = 400;

    private static final FrontController INSTANCE = new FrontController();

    private Stage primaryStage;
    private BorderPane rootBorderPane;

    private FrontController() {
        rootBorderPane = new BorderPane();
        rootBorderPane.setTop(makeNavMenu());
    }

    public static FrontController getInstance() {
        return INSTANCE;
    }

    private MenuBar makeMenuBar() {
        var menuBar = new MenuBar();
        var numGeneratorMenuItem = new MenuItem("Generate Number");
        var numListingMenuItem = new MenuItem("View Numbers");


        numGeneratorMenuItem.setOnAction(evt -> showNumberGeneratorView());
        numListingMenuItem.setOnAction(evt -> showNumbersListView());

        menuBar.getItems().addAll(numGeneratorMenuItem, numListingMenuItem)

        hbox.getChildren().addAll(generateNumbers, viewNumbers);

        return hbox;
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.stage.setScene(new Scene(rootBorderPane, APP_WIDTH, APP_HEIGHT));
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
