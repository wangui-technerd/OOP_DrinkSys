package com.drinks.demo.controller;

import com.drinks.demo.model.Branch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class BranchController {
    @FXML private AnchorPane rootPane;
    @FXML private TextField branchNameField;
    @FXML private TextField locationField;
    @FXML private Button addButton;
    @FXML private TableView<Branch> branchTable;
    @FXML private TableColumn<Branch, Integer> idColumn;
    @FXML private TableColumn<Branch, String> nameColumn;
    @FXML private TableColumn<Branch, String> locationColumn;

    private final ObservableList<Branch> branchData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Configure table columns to use regular getters
        idColumn.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        // Load sample data
        loadSampleBranches();

        // Set up add button action
        addButton.setOnAction(event -> handleAddBranch());
    }

    private void loadSampleBranches() {
        branchData.add(new Branch(1, "Main Branch", "123 Main St"));
        branchData.add(new Branch(2, "Downtown Branch", "456 Center Ave"));
        branchTable.setItems(branchData);
    }

    private void handleAddBranch() {
        String name = branchNameField.getText().trim();
        String location = locationField.getText().trim();

        if (!name.isEmpty() && !location.isEmpty()) {
            int newId = branchData.isEmpty() ? 1 :
                    branchData.getLast().getBranchId() + 1;
            branchData.add(new Branch(newId, name, location));

            // Clear input fields
            branchNameField.clear();
            locationField.clear();
        }
    }
}