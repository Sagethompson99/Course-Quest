package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

    private Button searchButton;
    private Button deleteSearchHistoryButton;
    private Button homeButton;
    private Button settingsButton;
    private Button filterButton;
    private SearchView searchVal;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    private String alphabeticalType = "";
    private ArrayList<String> searchWhichWebsites;
    private int checkedFilterOrderButtonId = 0;
    private ArrayList<Integer> checkedButtons = new ArrayList<>();
    private LinearLayout popularSearchesView;
    private LinearLayout recentSearchesView;
    private ArrayList<String> recentSearchTerms;
    private String[] popularSearchTerms = new String[] {"Science", "Photography", "Coding", "Math", "Art History", "Engineering", "Politics", "Business"};
    private SharedPreferences myRecentSearches;
    String currentSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }

        searchWhichWebsites = new ArrayList<>();

        setContentView(R.layout.activity_search_page);

        myRecentSearches = getSharedPreferences("shared preferences", MODE_PRIVATE);
        recentSearchesView = findViewById(R.id.recentSearches);

        loadData();

        searchVal = findViewById(R.id.searchFor);

        deleteSearchHistoryButton = findViewById(R.id.deleteRecentSearches);
        deleteSearchHistoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearRecentSearches();
            }
        });

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

        popularSearchesView = findViewById(R.id.popularSearches);
        populatePopularSearchesView();
        populateRecentSearchesView();

        filterButton = findViewById(R.id.filter);
        registerForContextMenu(filterButton);

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

        searchButton = findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                hideKeyboard(v);
                currentSearch = searchVal.getQuery().toString();
                if(!(recentSearchTerms.contains(currentSearch)) && currentSearch.length()>0) {
                    recentSearchTerms.add(currentSearch);
                }
                saveData();
                if(searchWhichWebsites.size() < 1)
                    addAllWebsites();

                intent.putExtra("alphabeticalType", alphabeticalType);
                intent.putExtra("searchWebsites", searchWhichWebsites);
                startActivity(intent);
            }
        });
    }

    public void openHomePage() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void openResultsPage(String search) {
        Intent intent = new Intent(this, SearchPageResults.class);
        intent.putExtra("message", search);
        intent.putExtra("alphabeticalType", alphabeticalType);
        if(searchWhichWebsites.size() < 1)
            addAllWebsites();
        intent.putExtra("searchWebsites", searchWhichWebsites);

        startActivity(intent);
    }

    public void openSettingsPage() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void refreshPage(Activity a){
        final Intent intent = this.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        a.finish();
        a.overridePendingTransition(0, 0);
        a.startActivity(intent);
        a.overridePendingTransition(0, 0);
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
            popularSearchesView.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openResultsPage(b.getText().toString());
                }
            });
        }
    }

    public void populateRecentSearchesView(){
        for(int i = recentSearchTerms.size()-1; i >= 0; i--) {
            final Button b = new Button(this);
            b.setText(recentSearchTerms.get(i));
            ButtonFormatter.formatSearchPageButton(this, b);
            recentSearchesView.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openResultsPage(b.getText().toString());
                  }
            });
        }

    }

    public void clearRecentSearches(){
        if(recentSearchTerms.size() > 0) {
            recentSearchTerms.clear();
            saveData();
            refreshPage(this);
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

        //repopulates button checked states
        if(checkedButtons != null){
            for(int i = 0; i < checkedButtons.size(); i++){
                menu.findItem(checkedButtons.get(i)).setChecked(true);
            }
        }
        if(checkedFilterOrderButtonId != 0){
            menu.findItem(checkedFilterOrderButtonId).setChecked(true);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.filterABC:
                alphabeticalType = "filterABC";
                checkedFilterOrderButtonId = item.getItemId();
                break;
            case R.id.filterZYX:
                alphabeticalType = "filterZYX";
                checkedFilterOrderButtonId = item.getItemId();
                break;
            case R.id.filterDefault:
                alphabeticalType= "filterDefault";
                checkedFilterOrderButtonId = item.getItemId();
                break;
            case R.id.filterCodeCademy:
                checkOrUncheckWebsite("CodeCademy", item);
                break;
            case R.id.filterCoursera:
                checkOrUncheckWebsite("Coursera", item);
                break;
            case R.id.filterFutureLearn:
                checkOrUncheckWebsite("FutureLearn", item);
                break;
            case R.id.filterSkillShare:
                checkOrUncheckWebsite("SkillShare", item);
                break;
            case R.id.clearFilters:
                addAllWebsites();
                alphabeticalType = "filterDefault";
                break;
            default:
                break;
        }
       return super.onContextItemSelected(item);
    }

    public void checkOrUncheckWebsite(String website, MenuItem item) {
        //If item already checked then unchecked it
        if(searchWhichWebsites.contains(item.toString())) {
            checkedButtons.remove(Integer.valueOf(item.getItemId()));
            searchWhichWebsites.remove(website);
        }
        //If item is unchecked then checked it
        else {
            checkedButtons.add(item.getItemId());
            searchWhichWebsites.add(website);
        }
    }

    public void addAllWebsites()
    {
        searchWhichWebsites.add("CodeCademy");
        //searchWhichWebsites.add("Coursera");
        searchWhichWebsites.add("FutureLearn");
        searchWhichWebsites.add("SkillShare");
    }
}
