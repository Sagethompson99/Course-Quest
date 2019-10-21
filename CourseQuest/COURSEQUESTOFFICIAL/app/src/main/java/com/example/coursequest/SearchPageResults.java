package com.example.coursequest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * resultsActivity shows the results from seaching, each course listing displayed by resultsActivity
 * functions as a button that, when clicked, opens up a more detailed description of the course in
 * the displayFullResultsActivity
 * There will be a button on this page that takes the user back to the main search screen (MainActivity)
 */

public class SearchPageResults extends AppCompatActivity {

    private Button backButton;
    private LinearLayout resultView;

    //Receive the courses from MainActivity and use displayResults to display them
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page_results);
        Intent intent = getIntent();
//        ArrayList<Course> courses = intent.getExtras().getParcelableArrayList("Results");

        ArrayList<Course> courses = new ArrayList<>();
        resultView = (LinearLayout)findViewById(R.id.resultView);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openSearchPage();
            }
        });

        TextView textView = (TextView) findViewById(R.id.searchVal);
        String btn_text;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            btn_text = bundle.getString("message");
            textView.setText(btn_text);
            textView.setTextColor(Color.parseColor("#e74c3c"));
        }
          add(courses);

        //     addResults(courses, resultView);
        ArrayList<Button> buttons = new ArrayList<Button>();
        buttons = createButtons(courses);

        displayResults(courses, buttons);





    }



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
            if (buttons.get(i) == null)
            {
                continue;
            }
            else
            {
                buttons.get(i).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //USES PARCELABLE
                        // https://developer.android.com/reference/android/os/Parcelable.html
                        //Intent intent = new Intent(view.getContext(), DisplayFullResults.class);
                        //intent.putExtra("Course", currentCourse);
                        //view.getContext().startActivity(intent);
                    }
                });
                rootView.addView(buttons.get(i));
            }
        }
    }

    public ArrayList<Button> createButtons(ArrayList<Course> courses) {

        ArrayList<Button> buttons = new ArrayList<Button>();


        Iterator<Course> iter = courses.iterator();

        int i = 0;
        while(iter.hasNext()) {

            buttons.add(displayCourse(iter.next()));
            i++;

        }

        return buttons;
    }

    //displayResults uses this method in a loop - displaying each course
    public Button displayCourse(Course course)
    {
        Button courseView = new Button(this);
        courseView.setText("");

        String courseSubject = Course.getCourseSubject(course)+'\n';
        String courseName = Course.getCourseName(course)+'\n';
        String courseWebsite = Course.getCourseWebsite(course)+'\n';
        String courseLink = Course.getCourseLink(course)+'\n';
        String courseCost = Course.getCost(course)+'\n';

        if (!courseSubject.equals(""))
        {
            courseView.append(courseSubject);
        }
        if (!courseName.equals(""))
        {
            courseView.append(courseName);
        }
        if (!courseWebsite.equals(""))
        {
            courseView.append(courseWebsite);
        }
        if (!courseLink.equals(""))
        {
            courseView.append(courseLink);
        }
        if (!courseCost.equals(""))
        {
            courseView.append(courseCost);
        }
        if (courseView.getText().equals(""))
        {
            return null;
        }
        else
        {
            return courseView;
        }
    }

    public void openSearchPage() {
        Intent intent = new Intent(this, SearchPage.class);
        startActivity(intent);
    }

    public void add(ArrayList<Course> courses)
    {
        ArrayList<Course> results = new ArrayList<>();
        //fill results with the found courses from searching
        //Then call viewResults to open resultsActivity
        //TEST COURSES:
        Course testCourse = new Course();
        testCourse.setCourseSubject(testCourse,"Math");
        testCourse.setCourseName(testCourse,"Differential Equations");
        testCourse.setCourseDescription(testCourse,"Test description");
        testCourse.setCourseWebsite(testCourse,"Google");
        testCourse.setCourseLink(testCourse,"https://www.udemy.com/course/differential-equations-u/");
        //testCourse.setCost(testCourse,0);
        testCourse.setRating(testCourse,"8/10");
        //courses.add(testCourse);

        //courses.add(new Course("Differential Equations", "Math", "Google", "Test description", "https://www.udemy.com/course/differential-equations-u/", 0, "8/10"));
        Course testCourse2 = new Course();
        testCourse2.setCourseSubject(testCourse,"Computer Science");
        testCourse2.setCourseName(testCourse,"Software Engineering");
        testCourse2.setCourseDescription(testCourse,"Test description that I don't feel like writing");
        testCourse2.setCourseWebsite(testCourse,"Some website");
        testCourse2.setCourseLink(testCourse,"https://www.google.com/");
        //testCourse2.setCost(testCourse,25);
        testCourse.setRating(testCourse,"9/10");
        //courses.add(testCourse2);

        //courses.add(new Course("Software Engineering", "CS", "VCU", "Test description", "https://www.udemy.com/course/differential-equations-u/", 70, "8/10"));
        //courses.add(new Course("Software Engineering", "CS", "CodeAcademy", "Test description", "https://www.udemy.com/course/differential-equations-u/", 70, "8/10"));
        //courses.add(new Course("Software Engineering", "CS", "JMU", "Test description", "https://www.udemy.com/course/differential-equations-u/", 70, "8/10"));
        //courses.add(new Course("English Fundamentals", "English", "RazKids", "Test description", "https://www.udemy.com/course/differential-equations-u/", 70, "8/10"));
        //courses.add(new Course("Math 101", "Math", "KhanAcademy", "Test description", "https://www.udemy.com/course/differential-equations-u/", 70, "8/10"));
        //courses.add(new Course("Software Engineering", "CS", "UVA", "Test description", "https://www.udemy.com/course/differential-equations-u/", 70, "8/10"));
    }
}
