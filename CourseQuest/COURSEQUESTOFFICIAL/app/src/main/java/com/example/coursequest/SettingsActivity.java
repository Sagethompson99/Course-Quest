package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    private Button searchButton;
    private Button homeButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        searchButton = (Button) findViewById(R.id.searchButton2);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchPage();
            }
        });

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openHomePage();
            }
        });
    }

    public void openSearchPage() {
        Intent intent = new Intent(this, SearchPage.class);
        startActivity(intent);
    }

    public void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
