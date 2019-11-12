package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SearchPage extends AppCompatActivity {
    private Button search;
    private Button deleteSearchHistory;
    private SearchView searchVal;
    private Button homeButton;
    private Button settingsButton;
    private Button filter;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private String alphabeticalType = "";
    private ArrayList<String> searchWhichWebsites;
    private LinearLayout popularSearches;
    private LinearLayout recentSearches;
    private String[] popularSearchTerms = new String[] {"Science", "Math", "Art History", "Engineering", "Politics", "Business"};
    private ArrayList<String> recentSearchTerms;
    private SharedPreferences myRecentSearches;
    String currentSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchWhichWebsites = new ArrayList<>();

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
        setContentView(R.layout.activity_search_page);

        myRecentSearches = getSharedPreferences("shared preferences", MODE_PRIVATE);
        recentSearches =  (LinearLayout) findViewById(R.id.recentSearches);

        loadData();

        searchVal = (SearchView) findViewById(R.id.searchFor);

        deleteSearchHistory = (Button) findViewById(R.id.deleteRecentSearches);
        deleteSearchHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearRecentSearches();
            }
        });

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
        populatePopularSearchesView();
        populateRecentSearchesView();

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

        intent.putExtra("alphabeticalType", alphabeticalType);
        intent.putExtra("searchWebsites", searchWhichWebsites);

        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                hideKeyboard(v);
                startActivity(intent);
                currentSearch = searchVal.getQuery().toString();
                if(!(recentSearchTerms.contains(currentSearch)) && currentSearch.length()>0) {
                    recentSearchTerms.add(currentSearch);
                }
                saveData();
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

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    public void populatePopularSearchesView(){
        for(String term: popularSearchTerms) {
            final Button b = new Button(this);
            b.setText(term);
            ButtonFormatter.formatSearchPageButton(this, b);
            popularSearches.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openResults(b.getText().toString());
                }
            });
        }
    }

    public void populateRecentSearchesView(){
        for(int i = recentSearchTerms.size()-1; i >= 0; i--) {
            final Button b = new Button(this);
            b.setText(recentSearchTerms.get(i));
            ButtonFormatter.formatSearchPageButton(this, b);
            recentSearches.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openResults(b.getText().toString());
                  }
            });
        }

    }

    public void clearRecentSearches(){
        if(recentSearchTerms.size() > 0) {
            recentSearchTerms.clear();
            saveData();
            finish();
            startActivity(getIntent());
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = myRecentSearches.edit();
        Gson gson = new Gson();
        String Json = gson.toJson(recentSearchTerms);
        editor.putString("search", Json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("search", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        recentSearchTerms = gson.fromJson(json, type);
        if (recentSearchTerms == null)
        {
            recentSearchTerms = new ArrayList<>();
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
        switch (item.getItemId())
        {
            case R.id.filterABC:
                alphabeticalType = "filterABC";
            case R.id.filterZXY:
                alphabeticalType = "filterZYX";
            case R.id.filterDefault:
                alphabeticalType = "filterDefault";
            case R.id.filterCodeCademy:
                searchWhichWebsites.add("CodeCademy");
            case R.id.filterCoursera:
                searchWhichWebsites.add("Coursera");
            case R.id.filterFutureLearn:
                searchWhichWebsites.add("FutureLearn");
            case R.id.filterSkillShare:
                searchWhichWebsites.add("SkillShare");
            case R.id.searchAllWebsites:
                addAllWebsites();
            case R.id.clearFilters:
                addAllWebsites();
                alphabeticalType = "filterDefault";
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addAllWebsites()
    {
            searchWhichWebsites.add("CodeCademy");
            searchWhichWebsites.add("Coursera");
            searchWhichWebsites.add("FutureLearn");
            searchWhichWebsites.add("SkillShare");
    }
}
