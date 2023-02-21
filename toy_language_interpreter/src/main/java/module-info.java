module com.example.interprsmr {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.interprsmr to javafx.fxml;
    exports com.example.interprsmr;
}