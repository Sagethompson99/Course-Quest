package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * resultsActivity shows the results from searching, each course listing displayed by resultsActivity
 * functions as a button that, when clicked, opens up a corresponding url for the course
 * There will be a button on this page that takes the user back to the main search screen (MainActivity)
 */
public class SearchPageResults extends AppCompatActivity {

    public static ProgressDialog loadingView;
    private Button backButton;
    static ArrayList<Course> courses;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private ArrayList<String> searchWhichWebsites;
    private String alphabeticalType;
    private int numScrapersFinished = 0;
    public LinearLayout resultsView;
    private static String searchQuery;
    private optionsBarHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        loadingView = new ProgressDialog(this);

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
        }
        searchQuery = bundle.getString("message");

        courses = new ArrayList<>();

        //calls search method with user query
        search(searchQuery);

    }

    //adds course result buttons to the screen
    public void displaySearchResults()
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
    }


    public void sortCourses(){
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
    public Button createCourseButton(Course course)
    {
        Button courseView = new Button(this);
        ButtonFormatter.formatCourseButton(this, courseView);

        courseView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Button b = (Button)view;
                handler = new optionsBarHandler(resultsView, SearchPageResults.this, b, "Results");
                handler.openCourseOptionsBar();
                return true;
            }
        });

        courseView.setText("");

        //gets course link and adds onClick function to open the link in an external browser
        setLink(course, courseView);

        //appends course information to each courseView button
        courseView.append(Course.getInfoString(course));
        if (courseView.getText().equals(""))
            return null;
        else
            return courseView;
    }


    public void setLink(Course course, Button courseView)
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

    //exits search results page and opens search page
    public void openSearchPage()
    {
        Intent intent = new Intent(this, SearchPage.class);
        startActivity(intent);
    }

    public void scraperFinished()
    {
        numScrapersFinished++;
        if (numScrapersFinished == searchWhichWebsites.size())
        {
            displaySearchResults();
            loadingView.dismiss();
        }
    }

    //gets search results from website scrapers for a given search query
    public void search(String searchFor)
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

        loadingView.setMessage("Finding Courses...");
        loadingView.show();
        loadingView.setCanceledOnTouchOutside(false);
    }
}
