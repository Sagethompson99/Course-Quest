package edu.vcu.cs.app.cmsc355.cmsc355iteration1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;

/**
 * MainActivity is the starting screen with the search bar, after searching, the results will be
 * displayed on the resultsActivity
 */
public class MainActivity extends AppCompatActivity
{
    private ArrayList<Course> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent intent = new Intent(this, resultsActivity.class);
        intent.putExtra("Results", results);
        startActivity(intent);
    }

}
