package com.example.webbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create web view
        final WebView webView = new WebView(this);
        webView.loadUrl("https://www.google.com");
        final History webHistory = new History("https://www.google.com");


        // create address bar, set params
        LinearLayout addressBar = new LinearLayout(this);
        LinearLayout.LayoutParams addressBarParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams urlFieldParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        addressBar.setLayoutParams(addressBarParams);

        // create buttons and address field
        final AppCompatEditText urlField = new AppCompatEditText(this);
        urlField.setLayoutParams(urlFieldParams);

        AppCompatButton prevButton = new AppCompatButton(this);
        prevButton.setText("Previous");
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prevPage = webHistory.previous();
                if (prevPage != null) {
                    urlField.setText(prevPage);
                    webView.loadUrl(prevPage);
                }
            }
        });

        AppCompatButton nextButton = new AppCompatButton(this);
        nextButton.setText("Next");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nextPage = webHistory.next();
                if (nextPage != null) {
                    urlField.setText(nextPage);
                    webView.loadUrl(nextPage);
                }
            }
        });


        AppCompatButton goButton = new AppCompatButton(this);
        goButton.setText("Go");
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gotoUrl = Objects.requireNonNull(urlField.getText()).toString();
                webView.loadUrl(webHistory.goHere(gotoUrl));
            }
        });

        // add views to address bar
        addressBar.addView(prevButton);
        addressBar.addView(nextButton);
        addressBar.addView(goButton);
        addressBar.addView(urlField);
        addressBar.setBackgroundColor(Color.GRAY);


        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.addView(addressBar);
        mainLayout.addView(webView);
        setContentView(mainLayout);
    }
}