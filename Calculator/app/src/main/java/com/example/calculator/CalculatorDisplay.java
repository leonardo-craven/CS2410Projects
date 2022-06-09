package com.example.calculator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatTextView;

public class CalculatorDisplay extends AppCompatTextView {
    public CalculatorDisplay(Context context) {
        super(context);

        GradientDrawable background = new GradientDrawable();
        background.setColor(getResources().getColor(R.color.black, null));
        setBackground(background);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(0, 1, 1);
        params.columnSpec = GridLayout.spec(0, 3, 1);
        setLayoutParams(params);
        setTextSize(18);
        setTextColor(Color.WHITE);
    }

    public void evaluate() {
        String[] tokens = getText().toString().split(" ");

        if (tokens.length == 0) return;

        if (tokens.length == 1) {
            try {
                float currentValue = Float.parseFloat(tokens[0]);
                setText(Float.toString(currentValue));
                return;
            }
            catch (Exception e) {
                String error = "error";
                setText(error);
                return;
            }
        }


        float currentValue;
        float secondValue;
        try {
             currentValue = Float.parseFloat(tokens[0]);
        }
        catch (Exception e) {
            String error = "error";
            setText(error);
            return;
        }
        String operator = tokens[1];
        String mode = "value";

        for (int i = 2; i < tokens.length; i++) {
            if (mode.equals("value")) {
                try {
                    secondValue = Float.parseFloat(tokens[i]);
                }
                catch (Exception e) {
                    String error = "error";
                    setText(error);
                    return;
                }
                switch (operator) {
                    case "+":
                        currentValue += secondValue;
                        break;
                    case "-":
                        currentValue -= secondValue;
                        break;
                    case "*":
                        currentValue *= secondValue;
                        break;
                    case "/":
                        currentValue /= secondValue;
                        break;
                }
                mode = "operator";
            }
            else {
                operator = tokens[i];
                mode = "value";
            }
        }
        setText(Float.toString(currentValue));
    }
}
