// Package declaration indicating the package in which the class belongs
package com.example.carivers;

// Importing necessary JavaFX classes for property binding
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Definition of the 'River' class
public class River {

    // Properties representing attributes of a river
    private final SimpleStringProperty province;
    private final SimpleStringProperty riverName;
    private final SimpleIntegerProperty lengthKm;

    // Constructor for initializing a River object with specified values
    public River(String province, String riverName, int lengthKm) {
        // Initializing properties using JavaFX SimpleProperty classes
        this.province = new SimpleStringProperty(province);
        this.riverName = new SimpleStringProperty(riverName);
        this.lengthKm = new SimpleIntegerProperty(lengthKm);
    }

    // Getter method for retrieving the value of the 'province' property
    public String getProvince() {
        return province.get();
    }

    // Getter method for obtaining the 'province' property itself
    public SimpleStringProperty provinceProperty() {
        return province;
    }

    // Setter method for updating the value of the 'province' property
    public void setProvince(String province) {
        this.province.set(province);
    }

    // Getter method for retrieving the value of the 'riverName' property
    public String getRiverName() {
        return riverName.get();
    }

    // Getter method for obtaining the 'riverName' property itself
    public SimpleStringProperty riverNameProperty() {
        return riverName;
    }

    // Setter method for updating the value of the 'riverName' property
    public void setRiverName(String riverName) {
        this.riverName.set(riverName);
    }

    // Getter method for retrieving the value of the 'lengthKm' property
    public int getLengthKm() {
        return lengthKm.get();
    }

    // Getter method for obtaining the 'lengthKm' property itself
    public SimpleIntegerProperty lengthKmProperty() {
        return lengthKm;
    }

    // Setter method for updating the value of the 'lengthKm' property
    public void setLengthKm(int lengthKm) {
        this.lengthKm.set(lengthKm);
    }
}
