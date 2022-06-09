package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize vertical layout with views matching size of screen
        final LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Create welcome banner
        AppCompatTextView banner = new AppCompatTextView(this);
        banner.setText("Welcome to Mad Libs! To get started, fill out the fields below:");

        // Noun field
        AppCompatTextView nounText = new AppCompatTextView(this);
        nounText.setText("A noun:");
        final AppCompatEditText nounField = new AppCompatEditText(this);
        nounField.setLayoutParams(params);

        // Gerund field
        AppCompatTextView gerundText = new AppCompatTextView(this);
        gerundText.setText("A gerund (verb with the suffix -ing; i.e. running):");
        final AppCompatEditText gerundField = new AppCompatEditText(this);
        gerundField.setLayoutParams(params);

        // Class field
        AppCompatTextView collClassText = new AppCompatTextView(this);
        collClassText.setText("A college class:");
        final AppCompatEditText collClassField = new AppCompatEditText(this);
        collClassField.setLayoutParams(params);

        // Place field
        AppCompatTextView placeText = new AppCompatTextView(this);
        placeText.setText("A place:");
        final AppCompatEditText placeField = new AppCompatEditText(this);
        placeField.setLayoutParams(params);

        // Object field
        AppCompatTextView objectText = new AppCompatTextView(this);
        objectText.setText("An object:");
        final AppCompatEditText objectField = new AppCompatEditText(this);
        objectField.setLayoutParams(params);

        // Final story view
        final AppCompatTextView storyView = new AppCompatTextView(this);

        // Button
        AppCompatButton button = new AppCompatButton(this);
        button.setText("Libby");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get words from text fields
                String noun = nounField.getText().toString();
                String gerund = gerundField.getText().toString();
                String collClass = collClassField.getText().toString();
                String place = placeField.getText().toString();
                String object = objectField.getText().toString();

                // Compose story, adding in the words from fields
                String completeStory = "Once upon a time there was a *noun* who enjoyed hobbies such as *gerund*. One day, they realized they had forgotten to do their homework for *class* because they were too busy *gerund* and only had 72 hours to finish it. Panicking, the *noun* ran to the *place* and hopped onto the *object* to begin their assignment. They worked hard, and eventually they finished their work and turned it in. Satisfied, they went back home to continue *gerund*.";
                completeStory = completeStory.replace("*noun*", noun);
                completeStory = completeStory.replace("*gerund*", gerund);
                completeStory = completeStory.replace("*class*", collClass);
                completeStory = completeStory.replace("*place*", place);
                completeStory = completeStory.replace("*object*", object);
                storyView.setText(completeStory);
            }
        });


        // Adding all views to layout
        layout.addView(banner);
        layout.addView(nounText);
        layout.addView(nounField);
        layout.addView(gerundText);
        layout.addView(gerundField);
        layout.addView(collClassText);
        layout.addView(collClassField);
        layout.addView(placeText);
        layout.addView(placeField);
        layout.addView(objectText);
        layout.addView(objectField);
        layout.addView(button);
        layout.addView(storyView);

        // Setting layout and displaying
        layout.setOrientation(LinearLayout.VERTICAL);

        setContentView(layout);
    }
}