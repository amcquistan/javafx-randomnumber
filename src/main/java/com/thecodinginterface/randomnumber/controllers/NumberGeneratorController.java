package com.thecodinginterface.randomnumber.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NumberGeneratorController extends BaseController {

    private TextField minValTextField = new TextField();
    private TextField maxValTextField = new TextField();
    private Label resultLbl = new Label("#");
    
    @Override
    AnchorPane getContentPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        gridPane.addRow(0, new Label("Min Value"), minValTextField);
        gridPane.addRow(1, new Label("Max Value"), maxValTextField);
        
        Button resultBtn = new Button("Generate Number");
        Button clearBtn = new Button("Clear");
        
        resultBtn.setOnAction(evt -> {
            System.out.println("Result Button Clicked");
        });
        
        clearBtn.setOnAction(evt -> {
            minValTextField.setText(null);
            maxValTextField.setText(null);
            resultLbl.setText("#");
        });
        
        gridPane.addRow(2, resultBtn, clearBtn);
        
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(50, 20, 50, 20));
        vbox.setAlignment(Pos.CENTER);
        HBox hbox = new HBox(resultLbl);
        hbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hbox, gridPane);
        
        return new AnchorPane(vbox);
    }

}
