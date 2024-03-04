// Package declaration indicating the package in which the class belongs
package com.example.carivers;

// Import statements for required JavaFX classes
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

// Class definition for handling the creation of a bar chart and radio buttons for provinces
public class GraphScene {

    // ObservableList to store River data
    private ObservableList<River> riverData;

    // Setter method to set River data
    public void setRiverData(ObservableList<River> riverData) {
        this.riverData = riverData;
    }

    // Method to initialize and return the bar chart with River data
    public Node initializeGraph() {
        // Creating a simple bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Adding data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        if (riverData != null) {
            for (River river : riverData) {
                series.getData().add(new XYChart.Data<>(river.getRiverName(), river.getLengthKm()));
            }
        }
        barChart.getData().add(series);

        // Create radio buttons for provinces
         VBox radioBox = createRadioButtons();

        // Creating an HBox to hold the chart without radio buttons
        HBox root = new HBox(barChart);

        return root;  // Return the root node containing the chart
    }

    // Method to create and return a VBox containing radio buttons for provinces
    private VBox createRadioButtons() {
        ToggleGroup provinceToggleGroup = new ToggleGroup();
        VBox radioBox = new VBox();

        // Replace this with your actual list of provinces
        List<String> provinces = Arrays.asList("Ontario", "British Columbia", "Alberta");

        // Create radio buttons for each province
        for (String province : provinces) {
            RadioButton radioButton = new RadioButton(province);
            radioButton.setToggleGroup(provinceToggleGroup);
            radioButton.setOnAction(event -> handleProvinceSelection(province));
            radioBox.getChildren().add(radioButton);
        }

        return radioBox;  // Return the VBox containing radio buttons
    }

    // Method to handle province selection (to be implemented)
    private void handleProvinceSelection(String selectedProvince) {
        // Handle the selected province, e.g., update the graph based on the selection

    }

    public void updateGraphScene() {
    }

}
