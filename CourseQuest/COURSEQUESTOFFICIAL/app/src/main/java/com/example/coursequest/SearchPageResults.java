package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * resultsActivity shows the results from seaching, each course listing displayed by resultsActivity
 * functions as a button that, when clicked, opens up a corresponding url for the course
 * There will be a button on this page that takes the user back to the main search screen (MainActivity)
 */

public class SearchPageResults extends AppCompatActivity {

    public static ProgressDialog loadingView;
    private Button backButton;
    static ArrayList<Course> courses;
    private ArrayList<Button> courseButtons;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private String longPressedButtonText;
    private String longPressedButtonLink;
    private ArrayList<String> searchWhichWebsites;
    private String alphabeticalType;
    private int numScrapersFinished = 0;

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

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchPage();
            }
        });

        //populates searchVal textview with user's search query
        TextView searchVal = (TextView) findViewById(R.id.searchVal);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            searchVal.setText(bundle.getString("message"));
            searchWhichWebsites = getIntent().getExtras().getStringArrayList("searchWebsites");
            alphabeticalType = getIntent().getExtras().getString("alphabeticalType");
        }

        courses = new ArrayList<>();
        courseButtons = new ArrayList<>();

        //calls search method with user query
        search(getIntent().getExtras().getString("message"));

    }

    //adds course result buttons to the screen
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

    //creates buttons. Called via postExecute method in each website scraper class
    public ArrayList<Button> createButtons(ArrayList<Course> courses) {

        ArrayList<Button> buttons = new ArrayList<Button>();
        Iterator<Course> iter = courses.iterator();

        while(iter.hasNext())
            buttons.add(displayCourse(iter.next()));

        return buttons;
    }


    //displayResults uses this method in a loop - displaying each course
    public Button displayCourse(Course course)
    {
        Button courseView = new Button(this);
        ButtonFormatter.formatCourseButton(this, courseView);
        //adds menu (like,unlike) to button
        registerForContextMenu(courseView);

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
        Course.sortByNameABC(courses);
        courseButtons.addAll(createButtons(courses));
        numScrapersFinished++;
        if (numScrapersFinished==1)
        {
            displayResults(courses, courseButtons);
        }
    }

    //gets search results from website scrapers for a given search query
    public void search(String searchFor)
    {
        if (searchWhichWebsites.contains("FutureLearn"))
        {
            futureLearnWebScraper scraper = new futureLearnWebScraper();
            scraper.execute(searchFor, this);
            //numScrapersFinished++;

        }
        if (searchWhichWebsites.contains("CodeCademy"))
        {
            //codeCademyWebScraper scraper3 = new codeCademyWebScraper();
            //scraper3.execute(searchFor, this);
            //numScrapersFinished++;
        }
        if (searchWhichWebsites.contains("SkillShare"))
        {
            //SkillShareScraper scraper4 = new SkillShareScraper();
            //scraper4.execute(searchFor, this);
            //numScrapersFinished++;
        }

        //Course.sortByNameABC(courses);

        if (searchWhichWebsites.contains("Cousera"))
        {
            //   CourseraWebScraper scraper2 = new CourseraWebScraper();
            //   scraper2.execute(searchFor, this);
            //numScrapersFinished++;
        }

    }

    public String getButtonLink(Button getLink)
    {
        String link = "";
        if (getLink.getTag() != null)
        {
            link = (String) getLink.getTag();
        }
        return link;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        Button b = (Button)v;
        longPressedButtonText = b.getText().toString();
        longPressedButtonLink = getButtonLink(b);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.course_options,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        // Handle item click
        switch (item.getItemId()){
            case R.id.save :
                Toast.makeText(this,"course saved:)", Toast.LENGTH_SHORT).show();
                HomeActivity.addNewSavedCourse(longPressedButtonText, longPressedButtonLink);
                break;
            case R.id.unsave :
                Toast.makeText(this,"Course unsaved:(",Toast.LENGTH_SHORT).show();
                HomeActivity.deleteSavedCourse(longPressedButtonText);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
