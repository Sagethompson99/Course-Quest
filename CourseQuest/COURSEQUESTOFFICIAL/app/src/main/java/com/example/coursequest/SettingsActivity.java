package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private LinearLayout settingsView;
    private PopupWindow colorPalettePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Button searchButton;
        Button homeButton;
        Switch darkModeSwitch;
        Button colorPalette;

        //saved user preferences. Used to save dark mode/light mode preferences for each launch
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        //Sets page theme to dark mode if user selects dark mode theme
        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsView = findViewById(R.id.settingsItems);

        searchButton = findViewById(R.id.searchButton2);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchPage();
            }
        });

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openHomePage();
            }
        });

        darkModeSwitch = findViewById(R.id.DarkModeSwitch);
        darkModeSwitch.setChecked(useDarkTheme);
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });

        colorPalette = findViewById(R.id.courseColorsButton);
        colorPalette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPalette();
            }
        });
    }

    //opens search page
    private void openSearchPage() {
        Intent intent = new Intent(this, SearchPage.class);
        startActivity(intent);
    }

    //opens home page
    private void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void openColorPalette(){
        LayoutInflater inflater = this.getLayoutInflater();
        View popup;
        //if(inflater != null) {
            popup = inflater.inflate(R.layout.color_palette_popup, settingsView, false);
            Button closePopupBtn = popup.findViewById(R.id.closePalette);

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;

            final colorManager ColorManager = new colorManager(this);
            ArrayList<String> colorNames = ColorManager.getColorNames();
            ArrayList<String> colorValues = ColorManager.getColorValues();
            ArrayList<String> checkState = ColorManager.getColorStates();

            LinearLayout colorList = popup.findViewById(R.id.paletteColorList);

            for(int i = 0; i < colorNames.size(); i++){
                CheckBox c = new CheckBox(this);
                    if(checkState.get(i).equals("1"))
                        c.setChecked(true);
                Drawable colorPreview = Objects.requireNonNull(getDrawable(R.drawable.ic_circle)).mutate();
                c.setText(colorNames.get(i));
                colorPreview.setTint(Color.parseColor(colorValues.get(i)));
                c.setCompoundDrawablesWithIntrinsicBounds(null, null, colorPreview, null);
                c.setPadding(0, 0, 20, 0);
                c.setTag(colorNames.get(i));
                colorList.addView(c);
                c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean b) {
                        if(!(button.isChecked())) {
                            boolean removeColor = ColorManager.removeColor(button.getTag().toString());
                            if(!removeColor){
                                Toast.makeText(SettingsActivity.this, "At least one color must be selected", Toast.LENGTH_SHORT).show();
                                button.setChecked(true);
                            }
                        }
                        else {
                            ColorManager.addColor(button.getTag().toString());
                        }
                    }
                });
            }

            //create popup
            colorPalettePopup = new PopupWindow(popup, Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT);
            colorPalettePopup.setWidth(width-80);

            //display the popup
            colorPalettePopup.showAtLocation(findViewById(R.id.courseColorsButton), Gravity.CENTER, 0, 0);

            closePopupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    colorPalettePopup.dismiss();
                }
            });
        //}
    }

    private void refreshPage(Activity a){
        final Intent intent = this.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        a.finish();
        a.overridePendingTransition(0, 0);
        a.startActivity(intent);
        a.overridePendingTransition(0, 0);
        startActivity(intent);
    }

    //sets app theme to dark mode if darkModeSwitch is checked
    private void toggleTheme(boolean darkTheme){
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();
        refreshPage(this);
    }
}
