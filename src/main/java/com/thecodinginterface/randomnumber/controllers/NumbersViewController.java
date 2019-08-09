package com.thecodinginterface.randomnumber.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.thecodinginterface.randomnumber.viewmodels.RandomNumberViewModel;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class NumbersViewController extends BaseController {
    
    @FXML
    private TableView<RandomNumberViewModel> table;
    
    @FXML
    private TableColumn<RandomNumberViewModel, Integer> numberColumn;
    
    @FXML
    private TableColumn<RandomNumberViewModel, Integer> lowerBoundsColumn;
    
    @FXML
    private TableColumn<RandomNumberViewModel, Integer> upperBoundsColumn;
    
    @FXML
    private TableColumn<RandomNumberViewModel, LocalDate> createdAtColumn;
    
    public void initialize() {
        numberColumn.setCellValueFactory(cell -> 
                cell.getValue().numberProperty().asObject());
        lowerBoundsColumn.setCellValueFactory(cell ->
                cell.getValue().lowerBoundsProperty().asObject());
        upperBoundsColumn.setCellValueFactory(cell ->
                cell.getValue().upperBoundsProperty().asObject());
        createdAtColumn.setCellValueFactory(cell ->
                cell.getValue().createdAtProperty());
        
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
    }
    
    @Override
    public void setFrontController(FrontController ctrl) {
        super.setFrontController(ctrl);
        List<RandomNumberViewModel> numbers = frontController.getRandomNumberDAO()
              .getNumbers()
              .stream()
              .map(RandomNumberViewModel::new)
              .collect(Collectors.toList());
        table.setItems(FXCollections.observableArrayList(numbers));
    }
    
    @Override
    AnchorPane getContentPane() {
        
//        List<RandomNumberViewModel> numbers = frontController.getRandomNumberDAO()
//                .getNumbers()
//                .stream()
//                .map(RandomNumberViewModel::new)
//                .collect(Collectors.toList());
//        
//        var table = new TableView<RandomNumberViewModel>(
//                FXCollections.observableArrayList(numbers));
//        
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        
//        var numberColumn = new TableColumn<RandomNumberViewModel, Integer>("Number");
//        var lowerBoundsColumn = new TableColumn<RandomNumberViewModel, Integer>("Min Value");
//        var upperBoundsColumn = new TableColumn<RandomNumberViewModel, Integer>("Max Value");
//        var createdAtColumn = new TableColumn<RandomNumberViewModel, LocalDate>("Created");
//        
//        numberColumn.setCellValueFactory(cell -> 
//                cell.getValue().numberProperty().asObject());
//        lowerBoundsColumn.setCellValueFactory(cell ->
//                cell.getValue().lowerBoundsProperty().asObject());
//        upperBoundsColumn.setCellValueFactory(cell ->
//                cell.getValue().upperBoundsProperty().asObject());
//        createdAtColumn.setCellValueFactory(cell ->
//                cell.getValue().createdAtProperty());
//        
//        createdAtColumn.setCellFactory(column -> {
//            return new TableCell<RandomNumberViewModel, LocalDate>() {
//                @Override
//                public void updateItem(final LocalDate item, boolean empty) {
//                    if (item == null || empty) {
//                        setText(null);
//                    } else {
//                        setText(RandomNumberViewModel.formatDate(item));
//                    }
//                }
//            };
//        });
//        
//        table.getColumns().add(numberColumn);
//        table.getColumns().add(lowerBoundsColumn);
//        table.getColumns().add(upperBoundsColumn);
//        table.getColumns().add(createdAtColumn);
//        
//        var vbox = new VBox();
//        vbox.setPadding(new Insets(10, 5, 10, 5));
//        vbox.setAlignment(Pos.CENTER);
//        
//        vbox.getChildren().add(table);
//        
//        var stackPane = new StackPane(vbox);
//        StackPane.setAlignment(vbox, Pos.CENTER);
//        
//        var anchorPane = new AnchorPane(stackPane);
//        
//        AnchorPane.setTopAnchor(stackPane, 10.0);
//        AnchorPane.setBottomAnchor(stackPane, 10.0);
//        AnchorPane.setLeftAnchor(stackPane, 10.0);
//        AnchorPane.setRightAnchor(stackPane, 10.0);
        
        return null;
    }
}
