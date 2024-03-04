// Package declaration indicating the package in which the class belongs
package com.example.carivers;

// Import statements for required JavaFX classes and collections
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

// Main application class extending JavaFX Application
public class MainApp extends Application {

    // ObservableList to manage river data
    private ObservableList<River> riverData;

    // Graph and Table Scene controllers
    private static GraphScene graphSceneController;
    private static TableScene tableSceneController;

    // DatabaseHandler for interacting with the database
    private static DatabaseHandler databaseHandler;

    // SceneTransitionUtil for handling scene transitions
    private SceneTransitionUtil sceneTransitionUtil;

    // Primary stage for the application
    private static Stage primaryStage;

    // Currently selected province
    private static String selectedProvince;

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }

    // Override method for initializing the primary stage and setting up the initial scenes
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Set the application icon using an absolute file path
        primaryStage.getIcons().add(new Image("file:/C:/Users/Simran/IdeaProjects/CaRivers/river.png"));

        // Initialize river data, GraphScene, TableScene, DatabaseHandler, and SceneTransitionUtil
        initializeRiverData();

        graphSceneController = new GraphScene();
        databaseHandler = new DatabaseHandler();
        graphSceneController.setRiverData(riverData);

        tableSceneController = new TableScene(this);
        tableSceneController.setRiverData(riverData);

        try {
            // Set river data for the first time
            setRiverDataForProvinces("Ontario");
            setGraphScene("Ontario");
            setTableScene("Ontario");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sceneTransitionUtil = new SceneTransitionUtil();

        primaryStage.setTitle("Canada Rivers App");

        // ToggleGroup and HBox for radio buttons to select provinces
        ToggleGroup provinceToggleGroup = new ToggleGroup();
        HBox radioBox = new HBox();
        for (String province : getDistinctProvinces()) {
            RadioButton radioButton = new RadioButton(province);
            radioButton.setToggleGroup(provinceToggleGroup);
            radioButton.setOnAction(event -> updateGraphScene(province));
            radioBox.getChildren().add(radioButton);
        }

        // Button to switch to the table view
        Button switchToTableButton = new Button("Switch to Table View");
        switchToTableButton.setOnAction(event -> switchToTableScene());

        // VBox to organize the layout
        VBox root = new VBox();
        root.getChildren().addAll(graphSceneController.initializeGraph(), radioBox, switchToTableButton);

        // Set the initial scene in the primary stage
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    // Method to set river data for a specific province
    private void setRiverDataForProvinces(String selectedProvince) throws SQLException {
        graphSceneController.setRiverData(databaseHandler.getRiversByProvince(selectedProvince));
        tableSceneController.setRiverData(databaseHandler.getRiversByProvince(selectedProvince));
    }

    // Method to get distinct provinces from the river data
    private String[] getDistinctProvinces() {
        Set<String> provinceSet = new HashSet<>();
        for (River river : riverData) {
            provinceSet.add(river.getProvince());
        }
        return provinceSet.toArray(new String[0]);
    }

    // Method to initialize the river data with sample values
    private void initializeRiverData() {
        riverData = FXCollections.observableArrayList();
        riverData.add(new River("Ontario", "St Lawrence River", 3058));
        riverData.add(new River("Ontario", "Ottawa River", 1271));
        riverData.add(new River("Ontario", "Albany River", 982));
        riverData.add(new River("Ontario", "Severn River", 982));
        riverData.add(new River("Ontario", "Winnipeg River", 813));

        riverData.add(new River("British Columbia", "Yukon River", 3185));
        riverData.add(new River("British Columbia", "Columbia River", 2000));
        riverData.add(new River("British Columbia", "Peace River", 1923));
        riverData.add(new River("British Columbia", "Fraser River", 1375));
        riverData.add(new River("British Columbia", "Liard River", 1115));

        riverData.add(new River("Alberta", "Slave River", 2338));
        riverData.add(new River("Alberta", "Saskatchewan River", 1939));
        riverData.add(new River("Alberta", "Churchill River", 1609));
        riverData.add(new River("Alberta", "Athabasca River", 1231));
        riverData.add(new River("Alberta", "Milk River", 1005));
    }

    // Method to set the Graph Scene for a specific province
    private static void setGraphScene(String selectedProvince) throws SQLException {
        graphSceneController.setRiverData(databaseHandler.getRiversByProvince(selectedProvince));
        Node graphRoot = graphSceneController.initializeGraph();

        if (graphRoot instanceof Group) {
            primaryStage.setScene(new Scene((Group) graphRoot, 800, 600));
        } else if (graphRoot instanceof Node) {
            Group rootGroup = new Group((Node) graphRoot);
            primaryStage.setScene(new Scene(rootGroup, 800, 600));
        }
    }

    // Method to update the Graph Scene based on the selected province
    void updateGraphScene(String selectedProvince) {
        if (graphSceneController != null && tableSceneController != null) {
            try {
                setGraphScene(selectedProvince);
                Node graphRoot = graphSceneController.initializeGraph();

                ToggleGroup provinceToggleGroup = new ToggleGroup();
                HBox radioBox = new HBox();
                for (String province : getDistinctProvinces()) {
                    RadioButton radioButton = new RadioButton(province);
                    radioButton.setToggleGroup(provinceToggleGroup);
                    radioButton.setOnAction(event -> updateGraphScene(province));
                    radioBox.getChildren().add(radioButton);
                }

                // Button to switch to the table view
                Button switchToTableButton = new Button("Switch to Table View");
                switchToTableButton.setOnAction(event -> switchToTableScene());

                // VBox to organize the layout
                VBox root = new VBox();
                root.getChildren().addAll(graphRoot, radioBox, switchToTableButton);

                // Set the updated scene in the primary stage
                primaryStage.setScene(new Scene(root, 800, 600));
                boolean isTableVisible = false;

                if (isTableVisible) {
                    setTableScene(selectedProvince);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to switch to the Table Scene
    void switchToTableScene() {
        try {
            setTableScene(selectedProvince);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to set the Table Scene for a specific province
    private static void setTableScene(String selectedProvince) throws SQLException {
        tableSceneController.setRiverData(databaseHandler.getRiversByProvince(selectedProvince));
        Scene tableScene = tableSceneController.createTableScene(selectedProvince);
        primaryStage.setScene(tableScene);
    }

    // Method to create a Table Scene for a specific province
    public Scene createTableScene(String selectedProvince) {
        Node tableView;
        tableView = null;
        StackPane root = new StackPane(tableView);
        updateTable(selectedProvince);
        return new Scene(root, 600, 400);
    }

    // Placeholder method for updating the table
    private void updateTable(String selectedProvince) {
        // Implement the table update logic here
    }

    // Method to switch to the Table Scene for a specific province
    void switchToTableScene(String selectedProvince) {
        if (tableSceneController != null) {
            try {
                setTableScene(selectedProvince);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to get the currently selected province from the radio buttons
    public String getSelectedProvince() {
        ToggleGroup provinceToggleGroup = null;
        RadioButton selectedRadioButton = (RadioButton) provinceToggleGroup.getSelectedToggle();
        return selectedRadioButton != null ? selectedRadioButton.getText() : null;
    }

    // Method to set the primary stage with a specified scene
    public void setTableScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to switch to the Graph Scene
    public void switchToGraphScene() {
        try {
            setGraphScene(selectedProvince);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
