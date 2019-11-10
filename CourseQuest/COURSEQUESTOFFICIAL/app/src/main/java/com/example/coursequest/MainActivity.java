package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * MainActivity is the starting screen with the search bar, after searching, the results will be
 * displayed on the resultsActivity
 * This class was made by Araf
 */
public class MainActivity extends AppCompatActivity {

    private static int TIMEOUT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, TIMEOUT);
    }
}
