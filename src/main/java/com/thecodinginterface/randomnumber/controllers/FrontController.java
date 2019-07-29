// FrontController.java

package com.thecodinginterface.randomnumber.controllers;

import java.net.URL;

import com.thecodinginterface.randomnumber.RandomNumberApp;
import com.thecodinginterface.randomnumber.repository.RandomNumberDAO;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FrontController {
    public static final double APP_WIDTH = 500;
    public static final double APP_HEIGHT = 400;

    private static final FrontController INSTANCE = new FrontController();

    private RandomNumberDAO randomNumberDAO;

    private Stage primaryStage;
    private BorderPane rootBorderPane;

    private FrontController() {
        rootBorderPane = new BorderPane();
//        rootBorderPane.setTop(makeMenuBar());
        rootBorderPane.setTop(makeNavBar());
    }

    public static FrontController getInstance() {
        return INSTANCE;
    }

    private ToolBar makeNavBar() {
        var generateNumbersBtn = new Button("Generate Number");
        var viewNumbersBtn = new Button("View Numbers");
        
        generateNumbersBtn.getStyleClass().add("dark-blue-btn");
        viewNumbersBtn.getStyleClass().add("dark-blue-btn");
        generateNumbersBtn.setOnAction(evt -> showNumberGeneratorView());
        viewNumbersBtn.setOnAction(evt -> showNumbersListView());
        
        var imageView = new ImageView(new Image(RandomNumberApp.class.getResourceAsStream("images/TCI-Logo.png")));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(80);
        
        return new ToolBar(new Label("", imageView), generateNumbersBtn, viewNumbersBtn);
    }
    
    private MenuBar makeMenuBar() {
        var menuBar = new MenuBar();
       
        var numGeneratorMenu = new Menu("Generate Number");
        var numListingMenu = new Menu("View Numbers");

        numGeneratorMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> {
            System.out.println("numGeneratorMenu clicked");
        });
        numListingMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> {
            System.out.println("numListingMenu clicked");
        });
        
//        numGeneratorMenuItem.setOnAction(evt -> { System.out.println("numGeneratorMenuItem clicked"); });
//        numListingMenuItem.setOnAction(evt -> { showNumbersListView(); });

        menuBar.getMenus().addAll(numGeneratorMenu, numListingMenu);

        return menuBar;
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
        System.out.println("showNumbersListView clicked");
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
