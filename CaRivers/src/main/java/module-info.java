module com.example.carivers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.carivers to javafx.fxml;
    exports com.example.carivers;
}