package com.example.treedrawing;

import android.content.Context;
import android.text.Editable;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Objects;

public class LabelledInput extends LinearLayout {
    public LabelledInput(Context context, String labelText) {
        super(context);
        setOrientation(VERTICAL);
        label = new AppCompatTextView(context);
        label.setText(labelText);
        label.setTextSize(18);
        textBox = new AppCompatEditText(context);


        LinearLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        setLayoutParams(params);

        addView(label);
        addView(textBox);
    }

    public String getText() {
       return Objects.requireNonNull(textBox.getText()).toString();
    }

    private AppCompatTextView label;
    private AppCompatEditText textBox;
}
