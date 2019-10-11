package edu.vcu.cs.app.cmsc355.cmsc355iteration1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import java.util.ArrayList;

/**
 * resultsActivity shows the results from seaching, each course listing displayed by resultsActivity
 * functions as a button that, when clicked, opens up a more detailed description of the course in
 * the displayFullResultsActivity
 * There will be a button on this page that takes the user back to the main search screen (MainActivity)
 */
public class resultsActivity extends AppCompatActivity
{
    //Receive the courses from MainActivity and use displayResults to display them
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        Intent intent = getIntent();
        ArrayList<Course> courses = intent.getExtras().getParcelableArrayList("Results");
        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
        Button returnButton = new Button(this);
        String returnMessage = "Return to the main search screen";
        returnButton.setText(returnMessage);
        int returnId = 1;
        returnButton.setId(returnId);
        returnButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        rootView.addView(returnButton);
        displayResults(courses);
    }

    public void displayResults(ArrayList<Course> courses)
    {
        //Code idea used for adding views programmatically
        //https://github.com/udacity/ud839_Miwok/blob/b7c723c3c38c2c2ca9eb7067e34fb526052cfd34/app/src/main/java/com/example/android/miwok/NumbersActivity.java
        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);

        if (courses == null)
        {
            return;
        }

        for (int i = 0; i<courses.size(); i++)
        {
            final Course currentCourse = courses.get(i);
            Button courseView = displayCourse(currentCourse);
            if (courseView == null)
            {
                continue;
            }
            else
            {
                courseView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //USES PARCELABLE
                        // https://developer.android.com/reference/android/os/Parcelable.html
                        Intent intent = new Intent(view.getContext(), displayFullResultsActivity.class);
                        intent.putExtra("Course", currentCourse);
                        view.getContext().startActivity(intent);
                    }
                });
                rootView.addView(courseView);
            }
        }
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
        String courseCostType = Course.getCostTypeString(course)+'\n';
        double courseCost = Course.getCourseCost(course);
        String courseCostString = courseCost+"\n";

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
        if (!courseCostType.equals(""))
        {
            courseView.append(courseCostType);
        }
        if (courseCost != -1)
        {
            courseView.append(courseCostString);
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
}
