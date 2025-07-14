package com.drinks.demo.controller;

import com.drinks.demo.model.Branch;
import com.drinks.demo.utilities.DBHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class viewBranchesController {

    @FXML private TableView<Branch> branchesTable;
    @FXML private TableColumn<Branch, Integer> idColumn;
    @FXML private TableColumn<Branch, String> nameColumn;
    @FXML private TableColumn<Branch, String> locationColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        branchesTable.setItems(FXCollections.observableArrayList(DBHelper.getAllBranches()));
    }
}
