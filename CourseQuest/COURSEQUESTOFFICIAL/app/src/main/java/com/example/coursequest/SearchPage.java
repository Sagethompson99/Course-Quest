package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

public class SearchPage extends AppCompatActivity {
    private Button search;
    private SearchView searchVal;
    private Button homeButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        searchVal = (SearchView) findViewById(R.id.searchFor);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openHomePage();
            }
        });

        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSettingsPage();
            }
        });

        final Intent intent = new Intent(SearchPage.this, SearchPageResults.class);

        searchVal.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                intent.putExtra("message", text);
                return false;
            }
        });

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    public void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openResults() {
        Intent intent = new Intent(this, SearchPageResults.class);
        startActivity(intent);
    }

    public void openSettingsPage() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
