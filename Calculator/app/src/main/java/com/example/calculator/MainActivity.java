package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CalculatorButtonData> calculatorButtonData =
            new ArrayList<CalculatorButtonData>() {
        {
            add(new CalculatorButtonData("C", 0, 3, 1,
                    CalculatorButtonData.buttonType.CLEAR));
            add(new CalculatorButtonData("9", 1, 0, 1));
            add(new CalculatorButtonData("8", 1, 1, 1));
            add(new CalculatorButtonData("7", 1, 2, 1));
            add(new CalculatorButtonData("/", 1, 3, 1,
                    CalculatorButtonData.buttonType.SYMBOL));
            add(new CalculatorButtonData("6", 2, 0, 1));
            add(new CalculatorButtonData("5", 2, 1, 1));
            add(new CalculatorButtonData("4", 2, 2, 1));
            add(new CalculatorButtonData("*", 2, 3, 1,
                    CalculatorButtonData.buttonType.SYMBOL));
            add(new CalculatorButtonData("3", 3, 0, 1));
            add(new CalculatorButtonData("2", 3, 1, 1));
            add(new CalculatorButtonData("1", 3, 2, 1));
            add(new CalculatorButtonData("-", 3, 3, 1,
                    CalculatorButtonData.buttonType.SYMBOL));
            add(new CalculatorButtonData("0", 4, 0, 2));
            add(new CalculatorButtonData(".", 4, 2, 1,
                    CalculatorButtonData.buttonType.SYMBOL));
            add(new CalculatorButtonData("+", 4, 3, 1,
                    CalculatorButtonData.buttonType.SYMBOL));
            add(new CalculatorButtonData("=", 5, 0, 4,
                    CalculatorButtonData.buttonType.EQUALS));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createLayout();
        createDisplay();
        addButtons();
    }


    protected void createLayout() {
        GridLayout mainLayout = new GridLayout(this);
        mainLayout.setColumnCount(4);
        mainLayout.setId(R.id.mainLayout);
        setContentView(mainLayout);
    }

    protected void createDisplay() {
        CalculatorDisplay calculatorDisplay = new CalculatorDisplay(this);
        calculatorDisplay.setId(R.id.calculatorDisplay);
        GridLayout mainLayout = findViewById(R.id.mainLayout);
        mainLayout.addView(calculatorDisplay);
    }

    protected void addButtons() {
        GridLayout mainLayout = findViewById(R.id.mainLayout);
        final CalculatorDisplay calculatorDisplay = findViewById(R.id.calculatorDisplay);
        calculatorButtonData.forEach(data -> {
            CalculatorButton button = new CalculatorButton(this, data, view -> {
                if (data.getType() == CalculatorButtonData.buttonType.CLEAR) {
                    calculatorDisplay.setText("");
                }

                if (data.getType() == CalculatorButtonData.buttonType.NUMBER) {
                    String expression = calculatorDisplay.getText().toString();
                    if (!expression.endsWith("error")) {
                        String newExpression = calculatorDisplay.getText().toString() + data.getText();
                        calculatorDisplay.setText(newExpression);
                    }
                }

                if (data.getType() == CalculatorButtonData.buttonType.SYMBOL) {
                    String expression = calculatorDisplay.getText().toString();
                    boolean endsWithSymbol = expression.endsWith(" ") || expression.endsWith(".") ||
                            expression.endsWith("error");
                    if (!endsWithSymbol) {
                        if (data.getText().equals(".")) {
                            String newExpression = expression + data.getText();
                            calculatorDisplay.setText(newExpression);
                        }
                        else {
                            String newExpression = expression + " " + data.getText() + " ";
                            calculatorDisplay.setText(newExpression);
                        }
                    }
                }

                if (data.getType() == CalculatorButtonData.buttonType.EQUALS) {
                    String expression = calculatorDisplay.getText().toString();
                    boolean endsWithSymbol = expression.endsWith(" ") ||  expression.endsWith(".")
                            || expression.endsWith("error");
                    if (!endsWithSymbol) calculatorDisplay.evaluate();
                }
            });
            mainLayout.addView(button);
        });
    }

}