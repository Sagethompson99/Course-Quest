package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.graphics.Color;
import android.util.TypedValue;

/**
 * MainActivity is the starting screen with the search bar, after searching, the results will be
 * displayed on the resultsActivity
 */
public class MainActivity extends AppCompatActivity
{
    private ArrayList<Course> results = new ArrayList<>();
    private Button searchButton;
    private Button homeButton;
    private Button moreButton;
    private static int TIMEOUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        searchButton = (Button) findViewById(R.id.searchButton);
//        searchButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                openSearchPage();
//            }
//        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, TIMEOUT);
    }

    //PLACEHOLDER SEARCH METHOD, this will be replaced later by
    //the web scraping tools
    //This version of the search method is to demonstrate how the activities are connected
    //The real search function will probably have more parameters to allow for filters, etc.
    public void search(String searchTerm)
    {
        ArrayList<Course> results = new ArrayList<>();
        //fill results with the found courses from searching
        //Then call viewResults to open resultsActivity
        viewResults(results);
    }

    //Send the found results to resultsActivity
    public void viewResults(ArrayList<Course> results)
    {
        Intent intent = new Intent(this, SearchPageResults.class);
        intent.putExtra("Results", results);
        startActivity(intent);
    }

    public void openSearchPage() {
        Intent intent = new Intent(this, SearchPage.class);
        startActivity(intent);
    }

}
