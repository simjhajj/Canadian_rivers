// Package declaration indicating the package in which the class belongs
package com.example.carivers;

// Import statements for required JavaFX classes and collections
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

// Class definition for handling the creation and functionality of the Table Scene
public class TableScene {

    // Reference to the main application
    private final MainApp mainApp;

    // TableView and ObservableList to manage river data
    private TableView<River> tableView;
    private ObservableList<River> riverData;

    // Currently selected province
    private String selectedProvince;

    // Constructor taking the main application as a parameter
    public TableScene(MainApp mainApp) {
        // Initialize mainApp reference and riverData ObservableList
        this.mainApp = mainApp;
        riverData = FXCollections.observableArrayList();

        // Initialize TableView with columns
        tableView = new TableView<>();
        tableView.setEditable(false);

        TableColumn<River, String> provinceColumn = new TableColumn<>("Province");
        provinceColumn.setCellValueFactory(new PropertyValueFactory<>("province"));

        TableColumn<River, String> riverNameColumn = new TableColumn<>("River Name");
        riverNameColumn.setCellValueFactory(new PropertyValueFactory<>("riverName"));

        TableColumn<River, Integer> lengthColumn = new TableColumn<>("Length (km)");
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("lengthKm"));

        tableView.getColumns().addAll(provinceColumn, riverNameColumn, lengthColumn);

        // Create radio buttons for provinces and switch-to-graph button
        ToggleGroup provinceToggleGroup = new ToggleGroup();
        VBox radioBox = createRadioButtons();

        Button switchToGraphButton = new Button("Switch to Graph View");
        switchToGraphButton.setOnAction(event -> switchToGraphScene());
        HBox buttonBox = new HBox(switchToGraphButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Create the root VBox for the Table Scene
        VBox root = new VBox(10, radioBox, tableView, buttonBox);  // Added spacing between elements

        // Create the Scene and set it in the main application
        Scene scene = new Scene(root);
        mainApp.setTableScene(scene);

        // By default, display data for Ontario
        updateTableForOntario();
    }

    // Method to display static data for Ontario in the table
    private void updateTableForOntario() {
        ObservableList<River> ontarioData = getStaticDataForOntario();
        tableView.setItems(ontarioData);
        selectedProvince = "Ontario";
    }

    // Method to display static data for British Columbia in the table
    private void updateTableForBritishColumbia() {
        ObservableList<River> bcData = getStaticDataForBritishColumbia();
        tableView.setItems(bcData);
        selectedProvince = "British Columbia";
    }

    // Method to display static data for Alberta in the table
    private void updateTableForAlberta() {
        ObservableList<River> albertaData = getStaticDataForAlberta();
        tableView.setItems(albertaData);
        selectedProvince = "Alberta";
    }

    // Method to create and return a VBox containing radio buttons for provinces
    private VBox createRadioButtons() {
        ToggleGroup provinceToggleGroup = new ToggleGroup();
        VBox radioBox = new VBox();

        List<String> provinces = Arrays.asList("Ontario", "British Columbia", "Alberta");

        // Create radio buttons for each province
        for (String province : provinces) {
            RadioButton radioButton = new RadioButton(province);
            radioButton.setToggleGroup(provinceToggleGroup);
            radioButton.setOnAction(event -> handleProvinceSelection(province));
            radioBox.getChildren().add(radioButton);
        }

        // Select the first radio button by default
        if (!provinces.isEmpty()) {
            ((RadioButton) radioBox.getChildren().get(0)).fire();
        }

        return radioBox;
    }

    // Method to handle province selection
    private void handleProvinceSelection(String selectedProvince) {
        this.selectedProvince = selectedProvince;

        // Update the table based on the selected province
        if ("Ontario".equals(selectedProvince)) {
            updateTableForOntario();
        } else if ("British Columbia".equals(selectedProvince)) {
            updateTableForBritishColumbia();
        } else if ("Alberta".equals(selectedProvince)) {
            updateTableForAlberta();
        }
    }

    // Method to get static data for Ontario
    private ObservableList<River> getStaticDataForOntario() {
        return FXCollections.observableArrayList(
                new River("Ontario", "St Lawrence River", 3058),
                new River("Ontario", "Ottawa River", 1271),
                new River("Ontario", "Albany River", 982),
                new River("Ontario", "Severn River", 982),
                new River("Ontario", "Winnipeg River", 813)
        );
    }

    // Method to get static data for British Columbia
    private ObservableList<River> getStaticDataForBritishColumbia() {
        return FXCollections.observableArrayList(
                new River("British Columbia", "Yukon River", 3185),
                new River("British Columbia", "Columbia River", 2000),
                new River("British Columbia", "Peace River", 1923),
                new River("British Columbia", "Fraser River", 1375),
                new River("British Columbia", "Liard River", 1115)
        );
    }

    // Method to get static data for Alberta
    private ObservableList<River> getStaticDataForAlberta() {
        return FXCollections.observableArrayList(
                new River("Alberta", "Slave River", 2338),
                new River("Alberta", "Saskatchewan River", 1939),
                new River("Alberta", "Churchill River", 1609),
                new River("Alberta", "Athabasca River", 1231),
                new River("Alberta", "Milk River", 1005)
        );
    }

    // Method to create the Table Scene for a specific province
    public Scene createTableScene(String selectedProvince) {
        this.selectedProvince = selectedProvince;
        return new Scene(new VBox(createRadioButtons(), tableView));
    }

    // Setter method to set the river data
    public void setRiverData(ObservableList<River> riverData) {
        this.riverData = riverData;
        updateTable(selectedProvince);  // Update the table when riverData changes
    }

    // Method to update the table to display data from different provinces
    private void updateTable(String selectedProvince) {
        if (riverData == null || riverData.isEmpty()) {
            System.out.println("Error: River data is null or empty.");
            return;
        }

        tableView.getItems().clear();

        // Filter river data for the selected province
        ObservableList<River> filteredData = FXCollections.observableArrayList();
        for (River river : riverData) {
            if (selectedProvince == null || river.getProvince().equals(selectedProvince)) {
                filteredData.add(river);
            }
        }

        tableView.setItems(filteredData);
    }

    // Method to switch the scene to the graph view
    private void switchToGraphScene() {
        mainApp.switchToGraphScene();
    }

    // Placeholder method for additional logic to switch to the graph view within the table scene
    private void switchToGraphView() {
        System.out.println("Switching to Graph View within Table Scene");
    }

}
