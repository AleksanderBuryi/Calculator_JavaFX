module com.calculator.calculator_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.calculator.calculator_javafx to javafx.fxml;
    exports com.calculator.calculator_javafx;
}