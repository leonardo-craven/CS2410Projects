package com.example.calculator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatButton;

public class CalculatorButton extends AppCompatButton {
    public CalculatorButton(Context context, CalculatorButtonData data, OnClickListener listener) {
        super(context);

        GradientDrawable background = new GradientDrawable();
        if (data.getType() == CalculatorButtonData.buttonType.NUMBER) {
            background.setColor(getResources().getColor(R.color.numberButton, null));
            setTextColor(getResources().getColor(R.color.white, null));
        }
        else {
            background.setColor(getResources().getColor(R.color.colorAccent, null));
            setTextColor(getResources().getColor(R.color.black, null));
        }
        background.setStroke(2, getResources().getColor(R.color.black, null));
        setBackground(background);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(data.getRow(), 1, 1);
        params.columnSpec = GridLayout.spec(data.getCol(), data.getSize(), 1);
        setLayoutParams(params);

        setText(data.getText());
        setOnClickListener(listener);
        setTextSize(32);
    }
}
