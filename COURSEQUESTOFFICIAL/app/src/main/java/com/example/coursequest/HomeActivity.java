package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<Course> results = new ArrayList<>();
    private Button searchButton;
    private Button homeButton;
    private Button moreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchButton = (Button) findViewById(R.id.searchButton2);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchPage();
            }
        });
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
