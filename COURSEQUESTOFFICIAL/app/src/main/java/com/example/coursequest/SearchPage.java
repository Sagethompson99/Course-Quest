package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchPage extends AppCompatActivity {
    private Button search;
    private SearchView searchVal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        final Intent intent = new Intent(SearchPage.this, SearchPageResults.class);

        searchVal = (SearchView) findViewById(R.id.searchFor);
        searchVal.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                intent.putExtra("SEARCHED", text);
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

    public void openResults() {
        Intent intent = new Intent(this, SearchPageResults.class);
        startActivity(intent);
    }
}
