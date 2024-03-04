// Package declaration indicating the package in which the class belongs
package com.example.carivers;

// Import statements for required JavaFX classes
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

// Controller class for the main application window
public class MainController {

    // FXML annotations to inject UI elements defined in the corresponding FXML file
    @FXML
    private ToggleGroup provinceToggleGroup;  // ToggleGroup for radio buttons representing provinces

    @FXML
    private Button showTableButton;  // Button to switch to the table view

    private MainApp mainApp;  // Reference to the MainApp instance

    // Setter method to set the reference to the MainApp
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    // Event handler for the province radio buttons' selection change
    @FXML
    public void onProvinceSelected() {
        RadioButton selectedRadioButton = (RadioButton) provinceToggleGroup.getSelectedToggle();
        if (selectedRadioButton != null && mainApp != null) {
            String selectedProvince = selectedRadioButton.getText();
            mainApp.updateGraphScene(selectedProvince);  // Call the MainApp method to update the graph scene
        }
    }

    // Event handler for the "Show Table" button click
    @FXML
    public void onShowTableButtonClicked() {
        if (mainApp != null) {
            String selectedProvince = getSelectedProvince();
            // Call the MainApp method to switch to the table scene
            if (selectedProvince != null) mainApp.switchToTableScene(selectedProvince);
        }
    }

    // Private method to get the text of the selected province radio button
    private String getSelectedProvince() {
        RadioButton selectedRadioButton = (RadioButton) provinceToggleGroup.getSelectedToggle();
        return selectedRadioButton != null ? selectedRadioButton.getText() : null;
    }
}
