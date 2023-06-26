package com.calculator.calculator_javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {
    @FXML
    public TextField display;
    private String operator = "+";

    @FXML
    protected void buttonDigitClick(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String digit = button.getText();
        if (display.getText().equals("0")){
            display.setText(digit);
        } else {
            display.setText(display.getText() + digit);
        }
    }

    @FXML
    protected void buttonOperatorClick(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String oldOperator = operator;
        operator = button.getText();
        if (display.getText().endsWith("+ ") || display.getText().endsWith("- ")
                || display.getText().endsWith("* ") || display.getText().endsWith("/ ")) {
            display.setText(display.getText().replace(oldOperator, operator));
        } else if (display.getText().contains("+") || display.getText().contains("-")
                || display.getText().contains("*") || display.getText().contains("/")) {
            calc();
            display.setText(display.getText() + " " + operator + " ");
        } else {
            display.setText(display.getText() + " " + operator + " ");
        }
    }

    @FXML
    protected void buttonEnterClick(ActionEvent actionEvent) {
        calc();
    }

    @FXML
    protected void buttonClearClick(ActionEvent actionEvent) {
        display.setText("0");
    }

    @FXML
    protected void buttonClearEnteredClick(ActionEvent actionEvent) {
        StringBuilder tmp = new StringBuilder(display.getText());
        display.setText(tmp.substring(0, tmp.indexOf(operator)+1));
    }

    @FXML
    protected void buttonPercentClick(ActionEvent actionEvent) { //todo доработать
        display.setText("Percent");
    }

    @FXML
    protected void buttonBackSpaceClick(ActionEvent actionEvent) { //todo доработать
        display.setText("BackSpace");
    }

    @FXML
    protected void buttonFractionOneClick(ActionEvent actionEvent) { //todo доработать
        display.setText("⅟");
    }

    @FXML
    protected void buttonCommaClick(ActionEvent actionEvent) { //todo доработать
        display.setText("Comma");
    }

    @FXML
    protected void buttonSqrtClick(ActionEvent actionEvent) {//todo доработать
        Button button = (Button) actionEvent.getSource();
        String sqrt = button.getText();
        String[] operands = display.getText().trim().split(" ");
        StringBuilder tmp = new StringBuilder();
        if (operands.length == 1) {
            display.setText(sqrt + " " + ((operands[0].equals("0")) ? "" : operands[0]));
        } else {
            for (int i = 0; i < operands.length; i++) {
                tmp.append(operands[i]).append(" ");
                if (operands[i].equals(operator))
                    tmp.append(sqrt).append(" ");
            }
            display.setText(tmp.toString());
        }

    }

    @FXML
    protected void buttonNegateClick(ActionEvent actionEvent) {//todo доработать
        String[] operands = display.getText().split("[-+/*]");
        if (operands.length == 1) {
            display.setText(String.valueOf(Double.parseDouble(operands[0]) * -1));
        } else {
            if (operator.equals("-")) {
                operator = "+";
                display.setText(operands[0] + operator + operands[1]);
            } else if (operator.equals("+")) {
                operator = "-";
                display.setText(operands[0] + operator + operands[1]);
            }

        }


    }

    private void calc() { //todo доработать
        String[] operands = display.getText().trim().split(" ");
        calc(operands);
        for(int i = 0; i < operands.length; i++) {
            switch (operands[i]) {
                case "+" -> display.setText(String.valueOf(Double.parseDouble(operands[i - 1]) + Double.parseDouble(operands[i + 1])));
                case "-" -> display.setText(String.valueOf(Double.parseDouble(operands[i - 1]) - Double.parseDouble(operands[i + 1])));
                case "*" -> display.setText(String.valueOf(Double.parseDouble(operands[i - 1]) * Double.parseDouble(operands[i + 1])));
                case "/" -> display.setText(String.valueOf(Double.parseDouble(operands[i - 1]) / Double.parseDouble(operands[i + 1])));
                case "" -> {
                    break;
                }

            }
        }
    }

    private void calc(String[] operands) { //todo выводить корень в выражении и без
        double d = 0d;
        for(int i = 0; i < operands.length; i++) {
            if(operands[i].equals("√")) {
                d = Math.sqrt(Double.parseDouble(operands[i + 1]));
                operands[i] = String.valueOf(d);
                operands[i+1] = "_";
            }
        }
    }
}