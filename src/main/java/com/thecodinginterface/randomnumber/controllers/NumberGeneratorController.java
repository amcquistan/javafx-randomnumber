package com.thecodinginterface.randomnumber.controllers;

import java.awt.Rectangle;
import java.time.LocalDate;
import java.util.Arrays;

import com.thecodinginterface.randomnumber.models.RandomNumber;
import com.thecodinginterface.randomnumber.utils.RandomNumberUtils;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NumberGeneratorController extends BaseController {
    private static final String NUMBER_PLACEHOLDER = "###";
    private TextField minValTextField = new TextField();
    private TextField maxValTextField = new TextField();
    private Label resultLbl = new Label(NUMBER_PLACEHOLDER);

    @Override
    AnchorPane getContentPane() {
        Rectangle original = new Rectangle(1,2,3,4);
        Rectangle[] rects = Arrays.stream(new Rectangle[3]).map($_ -> new Rectangle(original.getSize())).toArray(Rectangle[]::new);
        var gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.addRow(0, new Label("Min Value"), minValTextField);
        gridPane.addRow(1, new Label("Max Value"), maxValTextField);

        var resultBtn = new Button("Generate Number");
        var clearBtn = new Button("Clear");

        resultBtn.getStyleClass().add("rich-blue-btn");
        clearBtn.getStyleClass().add("rich-blue-btn");
        
        resultBtn.disableProperty().bind(
            minValTextField.textProperty().isEmpty().and(
                maxValTextField.textProperty().isEmpty()
            )
        );

        resultBtn.setOnAction(evt -> {
            int lowerBounds = 0;
            int upperBounds = 1;
            try {
                lowerBounds = Integer.valueOf(minValTextField.getText());
                upperBounds = Integer.valueOf(maxValTextField.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            int number = RandomNumberUtils.boundedRandomNumber(lowerBounds, upperBounds);
            var randomNumber = new RandomNumber(
                    LocalDate.now(),
                    number,
                    lowerBounds,
                    upperBounds
            );
            if (frontController.getRandomNumberDAO().save(randomNumber)) {
                resultLbl.setText(String.valueOf(number));
            }
        });

        clearBtn.setOnAction(evt -> {
            minValTextField.setText(null);
            maxValTextField.setText(null);
            resultLbl.setText(NUMBER_PLACEHOLDER);
        });

        var buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(resultBtn, clearBtn);
        
        gridPane.add(buttonBar, 0, 2, 2, 1);
        GridPane.setHalignment(buttonBar, HPos.CENTER);
        
        var vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setAlignment(Pos.CENTER);
        var hbox = new HBox(resultLbl);
        hbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(hbox, gridPane);

        resultLbl.getStyleClass().add("result-label");
        
        var group = new Group(vbox);
        var stackPane = new StackPane(group);
        StackPane.setAlignment(group, Pos.CENTER);
        
        var anchorPane = new AnchorPane(stackPane);
        
        AnchorPane.setTopAnchor(stackPane, 10.0);
        AnchorPane.setBottomAnchor(stackPane, 10.0);
        AnchorPane.setLeftAnchor(stackPane, 10.0);
        AnchorPane.setRightAnchor(stackPane, 10.0);
        
        
        return anchorPane;
    }
}
