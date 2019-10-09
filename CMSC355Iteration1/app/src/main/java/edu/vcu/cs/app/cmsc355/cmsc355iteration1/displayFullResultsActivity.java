package edu.vcu.cs.app.cmsc355.cmsc355iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * displayFullResultsActivity shows the full details of a course, this activity opens when a user
 * clicks on one of the results from resultsActivity. There is a button on this activity to return
 * to the main results screen
 */
public class displayFullResultsActivity extends AppCompatActivity
{
    private Course fullCourseDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_results);
        Intent intent = getIntent();
        //TODO: Check if the information is properly stored into fullCourseDetails from the parcel
        fullCourseDetails = intent.getExtras().getParcelable("Course");
        TextView allCourseInfo = displayAllInformation();
        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
        rootView.addView(allCourseInfo);
        Button returnButton = new Button(this);
        String returnMessage = "Return to the main results screen";
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

    }

    public TextView displayAllInformation()
    {
        TextView allInfo = new TextView(this);
        String defaultSetText = "Course information: \n";
        allInfo.setText(defaultSetText);

        String courseSubject = Course.getCourseSubject(fullCourseDetails);
        String courseName = Course.getCourseName(fullCourseDetails);
        String courseWebsite = Course.getCourseWebsite(fullCourseDetails);
        String courseLink = Course.getCourseLink(fullCourseDetails);
        String courseDescription = Course.getCourseDescription(fullCourseDetails);
        String professors = Course.getProfessors(fullCourseDetails);
        String costType = Course.costTypeEnumToString(Course.getCostType(fullCourseDetails)).toUpperCase();
        double courseCost = Course.getCourseCost(fullCourseDetails);
        String rating = Course.getRating(fullCourseDetails);
        boolean offersDegree = Course.getOffersDegree(fullCourseDetails);
        String degreeUniversity = Course.getDegreeUniversity(fullCourseDetails);
        String degreeType = Course.getDegreeType(fullCourseDetails);
        boolean offersCertificate = Course.getOffersCertificate(fullCourseDetails);
        String certificationType = Course.getCertificateType(fullCourseDetails);
        String difficulty = Course.getDifficultyString(fullCourseDetails);
        double courseLength = Course.getCourseLength(fullCourseDetails);

        //Displaying course subject
        if (courseSubject.equals(""))
        {
            allInfo.append("No Subject Found or Listed\n");
        }
        else
        {
            allInfo.append("Subject: "+courseSubject+"\n");
        }

        //Displaying course name
        if (courseName.equals(""))
        {
            allInfo.append("No Course Name Found or Listed\n");
        }
        else
        {
            allInfo.append("Course Name: "+courseName+"\n");
        }

        //Displaying course website
        if (courseWebsite.equals(""))
        {
            allInfo.append("No Course Website Found\n");
        }
        else
        {
            allInfo.append("Course Website: "+courseWebsite+"\n");
        }

        //Displaying course link
        if (courseLink.equals(""))
        {
            allInfo.append("No Course Link Found\n");
        }
        else
        {
            allInfo.append("Course Link: "+courseLink+"\n");
        }

        //Displaying course description
        if (courseDescription.equals(""))
        {
            allInfo.append("No Course Description Found or Listed\n");
        }
        else
        {
            allInfo.append("Course Description: "+courseDescription+"\n");
        }

        //Display course professors
        if (professors.equals(""))
        {
            allInfo.append("No Course Professors Found or Listed\n");
        }
        else
        {
            allInfo.append("Professors and Universities for Course: "+professors+"\n");
        }
        //CHECK THAT COSTTYPE IS CONVERTED TO STRING CORRECTLY IF IT IS NULL
        //ADD CONDITIONAL FOR IF THE COURSE IS FREE - display cost differently

        //Displaying cost type and cost
        if (costType.equals("")&& courseCost == -1)
        {
            allInfo.append("No course payment model found and no cost found");
        }
        if (costType.equals("")&& courseCost != -1)
        {
            allInfo.append("No Course Payment Model Found,\n");
            allInfo.append("but a course cost of "+courseCost+" was found\n");
        }
        else if (costType.equals("FREE"))
        {
            allInfo.append("This course is free\n");
        }
        else if (costType.equals("MEMBERSHIP") && courseCost == -1)
        {
            allInfo.append("This course has a membership cost, but the cost it not listed\n");
        }
        else if (costType.equals("MEMBERSHIP") && courseCost != -1)
        {
            allInfo.append("This course has a membership cost of "+courseCost+"\n");
        }
        else if (costType.equals("ONETIMECOST") && courseCost == -1)
        {
            allInfo.append("This course has a one time cost, but the cost is not listed\n");
        }
        else if (costType.equals("ONETIMECOST") && courseCost != -1)
        {
            allInfo.append("This course has a one time cost of "+courseCost+"" +"\n");
        }

        //Displaying rating
        if (rating.equals(""))
        {
            allInfo.append("A rating for a course was not found\n");
        }
        else
        {
            allInfo.append("The rating for this course is "+rating+"\n");
        }

        //Displaying information about if the class offers degree
        if (offersDegree && !degreeType.equals("") && !degreeUniversity.equals(""))
        {
            allInfo.append("This course offers a degree of type: "+degreeType+" from "+degreeUniversity+"\n");
        }
        else if (offersDegree && !degreeType.equals(""))
        {
            allInfo.append("This course offers a degree of type: "+degreeType+" from an unspecified university\n");
        }
        else if (offersDegree && !degreeUniversity.equals(""))
        {
            allInfo.append("This course offers an unspecified degree type from "+degreeUniversity+"\n");
        }
        else
        {
            allInfo.append("No information about if this course offers a degree found\n");
        }

        //Displaying information about certificates
        if (offersCertificate && !certificationType.equals(""))
        {
            allInfo.append("This course offers a certification of type "+certificationType+"\n");
        }
        else
        {
            allInfo.append("No information about this course offering certification was found\n");
        }

        //Displaying information about courseDifficulty
        if (difficulty.equals(""))
        {
            allInfo.append("No information about course difficulty found\n");
        }
        else
        {
            allInfo.append("Course difficulty listed as: "+difficulty+"\n");
        }

        //Displaying information about course length
        if (courseLength == -1)
        {
            allInfo.append("No course length found\n");
        }
        else
        {
            allInfo.append("Course length listed as: "+courseLength+"\n");
        }

        return allInfo;
    }


}
