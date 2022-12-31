module com.example.acciobrickbreaker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.acciobrickbreaker to javafx.fxml;
    exports com.example.acciobrickbreaker;
}