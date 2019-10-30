package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * resultsActivity shows the results from seaching, each course listing displayed by resultsActivity
 * functions as a button that, when clicked, opens up a corresponding url for the course
 * There will be a button on this page that takes the user back to the main search screen (MainActivity)
 */

public class SearchPageResults extends AppCompatActivity {

    private Button backButton;
    private LinearLayout resultView;
    private static ArrayList<Course> courses;
    private static String[] buttonColors;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    //Receive the courses from MainActivity and use displayResults to display them
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        setContentView(R.layout.activity_search_page_results);
        Intent intent = getIntent();

        ArrayList<Course> courses = new ArrayList<Course>();
        resultView = (LinearLayout)findViewById(R.id.resultView);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchPage();
            }
        });

        TextView textView = (TextView) findViewById(R.id.searchVal);
        String btn_text;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            btn_text = bundle.getString("message");
            textView.setText(btn_text);
        }

        search(getIntent().getExtras().getString("message"));
        buttonColors = new String[]{"#FFC300", "#57B5F3", "#96E864", "#E864AE", "#FF5959"};
    }


    public void displayResults(ArrayList<Course> courses, ArrayList<Button> buttons)
    {
        //Code idea used for adding views programmatically
        //https://github.com/udacity/ud839_Miwok/blob/b7c723c3c38c2c2ca9eb7067e34fb526052cfd34/app/src/main/java/com/example/android/miwok/NumbersActivity.java
        LinearLayout rootView = (LinearLayout) findViewById(R.id.resultView);

        if (courses == null)
        {
            return;
        }
        for (int i = 0; i<courses.size(); i++)
        {
            final Course currentCourse = courses.get(i);
            Button courseView = displayCourse(currentCourse);
            TextView t = new TextView(this);
            t.setHeight(25);
            rootView.addView(t);
            if (buttons.get(i) != null)
                rootView.addView(buttons.get(i));
        }
    }

    public ArrayList<Button> createButtons(ArrayList<Course> courses) {

        ArrayList<Button> buttons = new ArrayList<Button>();

        Iterator<Course> iter = courses.iterator();

        int i = 0;
        while(iter.hasNext()) {
            buttons.add(displayCourse(iter.next()));
            i++;
        }

        return buttons;
    }

    //displayResults uses this method in a loop - displaying each course
    public Button displayCourse(Course course)
    {
        int i = new Random().nextInt(5);
        Button courseView = new Button(this);
        courseView.setTextSize(18);
        courseView.setTextColor(Color.WHITE);
        courseView.setPadding(20, 10, 20, 10);
        courseView.setBackgroundColor(Color.parseColor(buttonColors[i]));

        courseView.setText("");

        String courseName = Course.getCourseName(course)+"\n\n";
        String courseDesc = Course.getCourseDescription(course) + "\n\n";
        String courseWebsite = Course.getCourseWebsite(course)+'\n';

        final String courseLink = Course.getCourseLink(course);
        if(!courseLink.equals("")) {
            courseView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(courseLink));
                    startActivity(intent);
                }
            });
        }
        if (!courseName.equals(""))
        {
            courseView.append(courseName);
        }
        if (!courseDesc.equals(""))
        {
            courseView.append(courseDesc);
        }
        if (!courseWebsite.equals(""))
        {
            courseView.append(courseWebsite);
        }
        if (courseView.getText().equals(""))
        {
            return null;
        }
        else
        {
            return courseView;
        }
    }

    public void openSearchPage() {
        Intent intent = new Intent(this, SearchPage.class);
        startActivity(intent);
    }

    public void search(String searchFor) {
        futureLearnWebScraper scraper = new futureLearnWebScraper();
        scraper.execute(searchFor, this);
    }
}