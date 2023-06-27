module com.calculator.calculator_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;


    opens com.calculator.calculator_javafx to javafx.fxml;
    exports com.calculator.calculator_javafx;
}