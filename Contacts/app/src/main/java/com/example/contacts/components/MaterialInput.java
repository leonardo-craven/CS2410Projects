package com.example.contacts.components;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.contacts.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MaterialInput extends TextInputLayout {
    TextInputEditText input;

    public MaterialInput(@NonNull Context context, String hint) {
        super(context, null, R.attr.textInputStyle);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(48, 24, 48, 24);
        setLayoutParams(params);
        input = new TextInputEditText(getContext());
        input.setHint(hint);
        input.setGravity(Gravity.START);
        addView(input);
    }

    public Editable getText() {
        return input.getText();
    }

    public void setText(String text) {
        input.setText(text);
    }

    public void setInputType(int type) {
        input.setInputType(type);
    }
}