package com.example.treedrawing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        LabelledInput branchLengthInput = new LabelledInput(this, "Maximum Branch Length");
        LabelledInput maxAngleInput = new LabelledInput(this, "Maximum Angle From Trunk");

        AppCompatButton button = new AppCompatButton(this);
        button.setText("Generate Tree");
        button.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(this, TreeActivity.class);
                int branchLength = Integer.parseInt(branchLengthInput.getText());
                int maxAngle = Integer.parseInt(maxAngleInput.getText());
                intent.putExtra("maxLength", branchLength);
                intent.putExtra("maxAngle", maxAngle);
                startActivity(intent);
            }
            catch (Exception ignored) {

            }
        });

        mainLayout.addView(branchLengthInput);
        mainLayout.addView(maxAngleInput);
        mainLayout.addView(button);
        setContentView(mainLayout);
    }
}