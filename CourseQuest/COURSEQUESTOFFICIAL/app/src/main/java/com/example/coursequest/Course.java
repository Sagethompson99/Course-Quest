package com.example.coursequest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;

/**
This class encapsulates the information found by the web scraper
This class was written by Christopher Flippen
*/

public class Course implements Parcelable
{
    @Override
    public int describeContents()
    {
        return 0;
    }

    private Course(Parcel in)
    {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeString(this.courseSubject);
        parcel.writeString(this.courseName);
        parcel.writeString(this.courseWebsite);
        parcel.writeString(this.courseLink);
        parcel.writeString(this.courseDescription);
        parcel.writeString(this.cost);
        parcel.writeString(this.rating);
    }

    private void readFromParcel(Parcel in)
    {
        this.courseSubject = in.readString();
        this.courseName = in.readString();
        this.courseWebsite = in.readString();
        this.courseLink = in.readString();
        this.courseDescription = in.readString();
        this.cost = in.readString();
        this.rating = in.readString();
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator()
            {
                public Course createFromParcel(Parcel in)
                {
                    return new Course(in);
                }

                public Course[] newArray(int size)
                {
                    return new Course[size];
                }
            };

    private String courseSubject = "";
    private String courseName = "";
    private String courseWebsite = "";
    private String courseLink = "";
    private String courseDescription = "";
    private String cost = "";
    private String rating = "";

    public Course()
    {

    }

    void setCourseSubject(String courseSubject)
    {
        this.courseSubject = courseSubject;
    }

    void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    static void setCourseName(Course course, String courseName)
    {
        course.courseName = courseName;
    }

    void setCourseWebsite(String courseWebsite)
    {
        this.courseWebsite = courseWebsite;
    }

    void setCourseLink(String courseLink)
    {
        this.courseLink = courseLink;
    }

    static void setCourseLink(Course course, String courseLink)
    {
        course.courseLink = courseLink;
    }

    void setCourseDescription(String courseDescription)
    {
        this.courseDescription = courseDescription;
    }

    static String getCourseName(Course course)
    {
        return course.courseName;
    }

    private static String getCourseWebsite(Course course)
    {
        return course.courseWebsite;
    }

    static String getCourseLink(Course course)
    {
        return course.courseLink;
    }

    public static String getCourseDescription(Course course)
    {
        return course.courseDescription;
    }

    static void sortByNameABC(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareByNameABC());
    }

    static void sortByNameZYX(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareByNameZYX());
    }

}
