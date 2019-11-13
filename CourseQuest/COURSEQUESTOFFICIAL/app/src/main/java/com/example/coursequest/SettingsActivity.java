package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private Button searchButton;
    private Button homeButton;
    private Button settingsButton;
    private Switch darkModeSwitch;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //saved user preferences. Used to save dark mode/light mode preferences for each launch
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        //Sets page theme to dark mode if user selects dark mode theme
        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

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

        darkModeSwitch = (Switch) findViewById(R.id.DarkModeSwitch);
        darkModeSwitch.setChecked(useDarkTheme);
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });
    }

    //opens search page
    public void openSearchPage() {
        Intent intent = new Intent(this, SearchPage.class);
        startActivity(intent);
    }

    //opens home page
    public void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void refreshPage(Activity a){
        final Intent intent = this.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        a.finish();
        a.overridePendingTransition(0, 0);
        a.startActivity(intent);
        a.overridePendingTransition(0, 0);
        startActivity(intent);
    }

    //sets app theme to dark mode if darkModeSwitch is checked
    public void toggleTheme(boolean darkTheme){
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();
        refreshPage(this);
    }
}
