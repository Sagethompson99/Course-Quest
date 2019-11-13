package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private Button searchButton;
    private Button homeButton;
    private Button settingsButton;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    static ArrayList<String> savedCourses;
    static ArrayList<String> savedCourseLinks;
    private LinearLayout savedCourseView;
    private ImageView noCoursesImage;
    private TextView noCoursesText;
    public static SharedPreferences mySavedCourses;
    String longPressedButtonText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        mySavedCourses = getSharedPreferences("shared preferences", MODE_PRIVATE);

        loadData();

        setContentView(R.layout.activity_home);
        savedCourseView = findViewById(R.id.savedCourseView);
        noCoursesImage = findViewById(R.id.noCoursesImage);
        noCoursesText = findViewById((R.id.noCoursesText));

        searchButton = findViewById(R.id.searchButton2);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchPage();
            }
        });

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSettingsPage();
            }
        });

        populateSavedCoursesView();
    }

    public void openSearchPage() {
        Intent intent = new Intent(this, SearchPage.class);
        startActivity(intent);
    }

    public void openSettingsPage() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public static void addNewSavedCourse(String buttonText, String link)
    {
        savedCourses.add(buttonText);
        savedCourseLinks.add(link);
        saveData();
    }

    public static void deleteSavedCourse(String buttonText)
    {
        int index = savedCourses.indexOf(buttonText);
        savedCourses.remove(index);
        savedCourseLinks.remove(index);
        saveData();
    }

    private static void saveData() {
        SharedPreferences.Editor editor = mySavedCourses.edit();
        Gson gson = new Gson();
        String courseJson = gson.toJson(savedCourses);
        String linkJson = gson.toJson(savedCourseLinks);
        editor.putString("saved courses", courseJson);
        editor.putString("saved links", linkJson);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String courseJson = sharedPreferences.getString("saved courses", null);
        String linkJson = sharedPreferences.getString("saved links", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        savedCourses = gson.fromJson(courseJson, type);
        savedCourseLinks = gson.fromJson(linkJson, type);
        if (savedCourses == null)
        {
            savedCourses = new ArrayList<>();
        }
        if (savedCourseLinks == null)
        {
            savedCourseLinks = new ArrayList<>();
        }
    }

    public void setButtonLink(final String courseLink, Button courseView)
    {
        if(!courseLink.equals(""))
        {
            courseView.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v) {
                 Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_VIEW);
                 intent.addCategory(Intent.CATEGORY_BROWSABLE);
                 intent.setData(Uri.parse(courseLink));
                 startActivity(intent);
              }
            }
            );
            courseView.setTag(courseLink);
        }

    }

    public void populateSavedCoursesView(){
        if(savedCourses.size() < 1){
            noCoursesImage.setVisibility(View.VISIBLE);
            noCoursesText.setVisibility(View.VISIBLE);
        }
        else{
            for(int i = savedCourses.size()-1; i >= 0; i--) {
                Button course = new Button(this);
                registerForContextMenu(course);
                course.setText(savedCourses.get(i));
                ButtonFormatter.formatCourseButton(this, course);
                final String courseLink = savedCourseLinks.get(i);
                setButtonLink(courseLink, course);
                savedCourseView.addView(course);
            }
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Button b = (Button)v;
        longPressedButtonText = b.getText().toString();
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.course_options,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Handle item click
        switch (item.getItemId()){
            case R.id.save :
                Toast.makeText(this,"course already saved", Toast.LENGTH_SHORT).show();
                break;
            case R.id.unsave :
                Toast.makeText(this,"Course unsaved:(",Toast.LENGTH_SHORT).show();
                deleteSavedCourse(longPressedButtonText);
                finish();
                startActivity(getIntent());
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
