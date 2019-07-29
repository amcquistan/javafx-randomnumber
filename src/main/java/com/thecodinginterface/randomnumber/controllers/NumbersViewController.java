package com.thecodinginterface.randomnumber.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.thecodinginterface.randomnumber.viewmodels.RandomNumberViewModel;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class NumbersViewController extends BaseController {

    @Override
    AnchorPane getContentPane() {
        
        List<RandomNumberViewModel> numbers = frontController.getRandomNumberDAO()
                .getNumbers()
                .stream()
                .map(RandomNumberViewModel::new)
                .collect(Collectors.toList());
        
        var table = new TableView<RandomNumberViewModel>(FXCollections.observableArrayList(numbers));
        
        var numberColumn = new TableColumn<RandomNumberViewModel, Integer>("Number");
        var lowerBoundsColumn = new TableColumn<RandomNumberViewModel, Integer>("Min Value");
        var upperBoundsColumn = new TableColumn<RandomNumberViewModel, Integer>("Max Value");
        var createdAtColumn = new TableColumn<RandomNumberViewModel, LocalDate>("Created");
        
        numberColumn.setCellValueFactory(cell -> cell.getValue().numberProperty().asObject());
        lowerBoundsColumn.setCellValueFactory(cell -> cell.getValue().lowerBoundsProperty().asObject());
        upperBoundsColumn.setCellValueFactory(cell -> cell.getValue().upperBoundsProperty().asObject());
        createdAtColumn.setCellValueFactory(cell -> cell.getValue().createdAtProperty());
        
        createdAtColumn.setCellFactory(column -> {
            return new TableCell<RandomNumberViewModel, LocalDate>() {
                @Override
                public void updateItem(final LocalDate item, boolean empty) {
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(RandomNumberViewModel.formatDate(item));
                    }
                }
            };
        });
        
        table.getColumns().add(numberColumn);
        table.getColumns().add(lowerBoundsColumn);
        table.getColumns().add(upperBoundsColumn);
        table.getColumns().add(createdAtColumn);
        
        var vbox = new VBox();
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setAlignment(Pos.CENTER);
        
        vbox.getChildren().add(table);
        
        return new AnchorPane(vbox);
    }
}
