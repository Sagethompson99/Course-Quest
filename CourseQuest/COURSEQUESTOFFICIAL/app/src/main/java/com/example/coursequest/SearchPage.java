package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.SearchView;

public class SearchPage extends AppCompatActivity {
    private Button search;
    private SearchView searchVal;
    private Button homeButton;
    private Button settingsButton;
    private Button filter;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private LinearLayout popularSearches;
    private String[] popularSearchTerms = new String[] {"Science", "Astronomy", "Calculus", "Interior Design"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

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

        popularSearches = (LinearLayout) findViewById(R.id.popularSearches);
        populatePopularSearchesView(popularSearchTerms);

        filter = findViewById(R.id.filter);
        registerForContextMenu(filter);

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

    public void openResults(String search) {
        Intent intent = new Intent(this, SearchPageResults.class);
        intent.putExtra("message", search);
        startActivity(intent);
    }

    public void openSettingsPage() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void populatePopularSearchesView(String[] searchterms){
        for(String term: searchterms) {
            final Button b = new Button(this);
            b.setTransformationMethod(null);
            b.setText(term);
            courseButtonFormatter.format(this, b);
            Constraints.LayoutParams params = new Constraints.LayoutParams(
                    Constraints.LayoutParams.WRAP_CONTENT,
                    Constraints.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(20, 0, 0, 20);
            b.setLayoutParams(params);
            popularSearches.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openResults(b.getText().toString());
                }
            });
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filters,menu);
        menu.setHeaderTitle("Filters");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filterABC:
                return true;
            case R.id.filterZXY:
                return true;
            case R.id.filterProvider:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
