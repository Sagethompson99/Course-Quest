package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * resultsActivity shows the results from searching, each course listing displayed by resultsActivity
 * functions as a button that, when clicked, opens up a corresponding url for the course.
 * There is a button on this page that takes the user back to the main search screen
 */
public class SearchPageResults extends AppCompatActivity {

    private static Dialog loadingView;
    private TextView numResults;
    static ArrayList<Course> courses;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private ArrayList<String> searchWhichWebsites;
    private String alphabeticalType;
    private int numScrapersFinished = 0;
    private LinearLayout resultsView;
    private static String searchQuery;
    private optionsBarHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Button backButton;
        Button homeButton;
        Button settingsButton;
        Button searchButton;

        super.onCreate(savedInstanceState);

        //saved user preferences. Used to save dark mode/light mode preferences for each launch
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        //Sets page theme to dark mode if user selects dark mode theme
        if(useDarkTheme)
        {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        setContentView(R.layout.activity_search_page_results);

        homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openHomePage();
            }
        });

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSettingsPage();
            }
        });

        searchButton = findViewById(R.id.searchButton2);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchPage();
            }
        });

        loadingView = new Dialog(this);
        loadingView.setContentView(R.layout.loading_view);
        loadingView.setCancelable(false);
        numResults = loadingView.findViewById(R.id.numResults);

        resultsView = findViewById(R.id.resultView);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchPage();
            }
        });

        //populates searchVal text view with user's search query
        TextView searchVal = findViewById(R.id.searchVal);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            searchVal.setText(bundle.getString("message"));
            searchWhichWebsites = getIntent().getExtras().getStringArrayList("searchWebsites");
            alphabeticalType = getIntent().getExtras().getString("alphabeticalType");
            searchQuery = bundle.getString("message");
        }

        courses = new ArrayList<>();

        //calls search method with user query
        search(searchQuery);

    }

    //adds course result buttons to the screen
    private void displaySearchResults()
    {
        //Code idea used for adding views programmatically
        //https://github.com/udacity/ud839_Miwok/blob/b7c723c3c38c2c2ca9eb7067e34fb526052cfd34/app/src/main/java/com/example/android/miwok/NumbersActivity.java
        LinearLayout rootView = findViewById(R.id.resultView);

        sortCourses();

        if(courses.size() == 0)
        {
            TextView noResultsText = findViewById(R.id.noResultsText);
            noResultsText.setVisibility(View.VISIBLE);
        }
        for(int i = 0; i < courses.size(); i++)
        {
            final Course currentCourse = courses.get(i);
            Button courseView = createCourseButton(currentCourse);
            if (courses.get(i) != null)
                rootView.addView(courseView);
        }
        loadingView.dismiss();
    }


    private void sortCourses(){
        if(alphabeticalType.equals("filterABC")) {
            Course.sortByNameABC(courses);
        }
        else if(alphabeticalType.equals("filterZYX"))
        {
            Course.sortByNameZYX(courses);
        }
        else {
            //Default case. Used to present most relevant results first
            courseCompareByKeyword.sort(courses, searchQuery.toUpperCase());
        }
    }

    //displayResults uses this method in a loop - displaying each course
    private Button createCourseButton(Course course)
    {
        Button courseView = new Button(this);

        courseView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Button course = (Button)view;
                if(handler != null){
                    handler.closeCourseOptionsBar();
                }
                handler = new optionsBarHandler(resultsView, SearchPageResults.this, course, "Results");
                handler.openCourseOptionsBar();
                return true;
            }
        });

        courseView.setText("");

        //gets course link and adds onClick function to open the link in an external browser
        setLink(course, courseView);
        ButtonFormatter.formatCourseButton(this, courseView);
        courseView.setWidth(resultsView.getWidth());

        //appends course information to each courseView button
       // courseView.append(Course.getInfoString(course));
        courseView.append(Course.getCourseName(course) + "\n\n");
        courseView.append(Course.getCourseDescription(course));

        if (courseView.getText().equals(""))
            return null;
        else
            return courseView;
    }


    private void setLink(Course course, Button courseView)
    {
        final String courseLink = Course.getCourseLink(course);
        if(!courseLink.equals(""))
        {
            courseView.setOnClickListener(new View.OnClickListener()
            {
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

    public void scraperFinished()
    {
        numScrapersFinished++;
        String currentNumResults = courses.size()+"";
        numResults.setText(currentNumResults);

        if (numScrapersFinished == searchWhichWebsites.size())
        {
            displaySearchResults();
        }
    }

    //gets search results from website scrapers for a given search query
    private void search(String searchFor)
    {
        if(searchWhichWebsites.contains("FutureLearn"))
        {
            futureLearnWebScraper scraper = new futureLearnWebScraper();
            scraper.execute(searchFor, this);
        }
        if(searchWhichWebsites.contains("CodeCademy"))
        {
            codeCademyWebScraper scraper3 = new codeCademyWebScraper();
            scraper3.execute(searchFor, this);
        }
        if(searchWhichWebsites.contains("SkillShare"))
        {
            SkillShareScraper scraper4 = new SkillShareScraper();
            scraper4.execute(searchFor, this);
        }

        if(searchWhichWebsites.contains("Coursera"))
        {
            CourseraWebScraper scraper2 = new CourseraWebScraper();
            scraper2.execute(searchFor, this);
        }

        loadingView.show();
    }

    private void openHomePage()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void openSettingsPage()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    //exits search results page and opens search page
    private void openSearchPage()
    {
        Intent intent = new Intent(this, SearchPage.class);
        startActivity(intent);
    }
}
