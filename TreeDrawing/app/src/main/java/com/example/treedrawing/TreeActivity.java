package com.example.treedrawing;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int maxLength = intent.getIntExtra("maxLength", 200);
        int maxAngle = intent.getIntExtra("maxAngle", 20);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        DrawTreeView drawTreeView = new DrawTreeView(this, maxLength, maxAngle);

        mainLayout.addView(drawTreeView);
        setContentView(mainLayout);
    }
}
