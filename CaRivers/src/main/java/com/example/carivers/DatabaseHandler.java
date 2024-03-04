// Package declaration indicating the package in which the class belongs
package com.example.carivers;

// Imported statements for required JavaFX and SQL classes
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Class definition for handling database operations related to rivers
public class DatabaseHandler {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3307/canada_rivers";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    // Method to establish a database connection
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Method to retrieve a list of rivers based on the specified province
    public static ObservableList<River> getRiversByProvince(String province) {
        List<River> rivers = new ArrayList<>();  // List to store River objects retrieved from the database

        try (Connection connection = connect()) {
            String query = "SELECT * FROM rivers WHERE province = ?";  // SQL query to select rivers by province
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, province);  // Set the parameter for the province in the query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Iterate through the result set and create River objects for each record
                    while (resultSet.next()) {
                        String riverName = resultSet.getString("river_name");
                        int lengthKm = resultSet.getInt("length_km");
                        rivers.add(new River(province, riverName, lengthKm));  // Add a new River object to the list
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQL exception that occurs
        }

        // Converted the List to ObservableList for use in JavaFX
        return FXCollections.observableList(rivers);
    }

}
