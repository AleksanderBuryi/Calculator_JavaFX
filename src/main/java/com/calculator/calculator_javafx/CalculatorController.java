package com.calculator.calculator_javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.math.NumberUtils;

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
    protected void buttonPercentClick(ActionEvent actionEvent) {
        String[] displayString = display.getText().trim().split(" ");
        double num1 = 0;
        double num2 = 0;
        if (displayString.length == 1) {
            display.setText(displayString[0]);
        } else {
            if (NumberUtils.isCreatable(displayString[0])) num1 = Double.parseDouble(displayString[0]);
            if (NumberUtils.isCreatable(displayString[displayString.length - 1]))
                num2 = Double.parseDouble(displayString[displayString.length - 1]);
            display.setText(num1 + " " + operator + " " + (num1 * num2 / 100));
        }
    }

    @FXML
    protected void buttonBackSpaceClick(ActionEvent actionEvent) {
        StringBuilder tmp = new StringBuilder(display.getText());
        if (tmp.isEmpty()) {
            display.setText("");
        }else if (tmp.length() > 1 && tmp.charAt(tmp.length() - 2) == ' ') {
            display.setText(String.valueOf(tmp.delete(tmp.length() - 2, tmp.length())));
        } else if (tmp.charAt(tmp.length()-1) == ' '){
            display.setText(String.valueOf(tmp.delete(tmp.length() - 2, tmp.length())));
        } else {
            display.setText(String.valueOf(tmp.delete(tmp.length() - 1, tmp.length())));
        }
    }

    @FXML
    protected void buttonFractionOneClick(ActionEvent actionEvent) {
        String[] displayString = display.getText().trim().split(" ");
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < displayString.length; i++) {
            if(i == displayString.length-1 && NumberUtils.isCreatable(displayString[i])) {
                tmp.append(1 / Double.parseDouble(displayString[i]));
            } else {
                tmp.append(displayString[i]).append(" ");
            }
        }
        display.setText(tmp.toString());
    }

    @FXML
    protected void buttonCommaClick(ActionEvent actionEvent) {
        display.setText(display.getText() + ".");
    }

    @FXML
    protected void buttonSqrtClick(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String sqrt = button.getText();
        String[] operands = display.getText().trim().split(" ");
        StringBuilder tmp = new StringBuilder();
        if (operands.length == 1) {
            display.setText(sqrt + " " + ((operands[0].equals("0")) ? "" : operands[0]));
        } else {
            for (String operand : operands) {
                tmp.append(operand).append(" ");
                if (operand.equals(operator))
                    tmp.append(sqrt).append(" ");
            }
            display.setText(tmp.toString());
        }

    }

    @FXML
    protected void buttonNegateClick(ActionEvent actionEvent) {
        String[] operands = display.getText().trim().split(" ");
        StringBuilder tmp = new StringBuilder();
        if (operands.length == 1) {
            display.setText(String.valueOf(Double.parseDouble(operands[0]) * -1));
        } else {
            for (int i = 0; i < operands.length; i++) {
                if (i == operands.length-1)
                    tmp.append(Double.parseDouble(operands[i])*-1);
                else tmp.append(operands[i]).append(" ");
            }
            display.setText(tmp.toString());
        }


    }

    private void calc() {
        String[] displayString = display.getText().trim().split(" ");
        String[] operands = calc(displayString);

        for(int i = 0; i < operands.length; i++) {
            if(operands.length == 1) display.setText(operands[i]);
            try {
                switch (operands[i]) {
                    case "+" -> display.setText(String.valueOf(Double.parseDouble(operands[i - 1]) + Double.parseDouble(operands[i + 1])));
                    case "-" -> display.setText(String.valueOf(Double.parseDouble(operands[i - 1]) - Double.parseDouble(operands[i + 1])));
                    case "*" -> display.setText(String.valueOf(Double.parseDouble(operands[i - 1]) * Double.parseDouble(operands[i + 1])));
                    case "/" -> display.setText(String.valueOf(Double.parseDouble(operands[i - 1]) / Double.parseDouble(operands[i + 1])));
                }
            } catch (Exception e) {
                display.setText("Incorrect data");
            }
        }
    }

    private String[] calc(String[] operands) {
        double d;
        int index = 0;
        for (String op : operands) {
            if (op.equals("√")) {
                String[] result = new String[operands.length - 1];
                for (int i = 0; i < operands.length - 1; i++) {
                    if (operands[i] != null && operands[i].equals("√")) {
                        d = Math.sqrt(Double.parseDouble(operands[i + 1]));
                        operands[i] = String.valueOf(d);
                        operands[i + 1] = null;
                        index = i + 1;
                    }
                }
                System.arraycopy(operands, 0, result, 0, index);
                System.arraycopy(operands, index + 1, result, index, operands.length - index - 1);
                return result;
            }
        }
        return operands;
    }
}