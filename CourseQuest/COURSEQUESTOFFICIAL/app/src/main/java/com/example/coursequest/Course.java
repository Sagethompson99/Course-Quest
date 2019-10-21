package com.example.coursequest;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;

public class Course implements Parcelable
{
    @Override
    public int describeContents()
    {
        return 0;
    }

    public Course(Parcel in)
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

    public void setCourseSubject(String courseSubject)
    {
        this.courseSubject = courseSubject;
    }

    public static void setCourseSubject(Course course, String courseSubject)
    {
        course.courseSubject = courseSubject;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public static void setCourseName(Course course, String courseName)
    {
        course.courseName = courseName;
    }


    public void setCourseWebsite(String courseWebsite)
    {
        this.courseWebsite = courseWebsite;
    }

    public static void setCourseWebsite(Course course, String courseWebsite)
    {
        course.courseWebsite = courseWebsite;
    }

    public void setCourseLink(String courseLink)
    {
        this.courseLink = courseLink;
    }

    public static void setCourseLink(Course course, String courseLink)
    {
        course.courseLink = courseLink;
    }

    public void setCourseDescription(String courseDescription)
    {
        this.courseDescription = courseDescription;
    }

    public static void setCourseDescription(Course course, String courseDescription)
    {
        course.courseDescription = courseDescription;
    }

    public void setCost(String cost)
    {
        this.cost = cost;
    }

    public static void setCost(Course course, String cost)
    {
        course.cost = cost;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public static void setRating(Course course, String rating)
    {
        course.rating = rating;
    }

    public static String getCourseSubject(Course course)
    {
        return course.courseSubject;
    }

    public static String getCourseName(Course course)
    {
        return course.courseName;
    }

    public static String getCourseWebsite(Course course)
    {
        return course.courseWebsite;
    }

    public static String getCourseLink(Course course)
    {
        return course.courseLink;
    }

    public static String getCourseDescription(Course course)
    {
        return course.courseDescription;
    }

    public static String getCost(Course course)
    {
        return course.cost;
    }

    public static String getRating(Course course)
    {
        return course.rating;
    }

    public static void sortBySubject(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareBySubject());
    }

    public static void sortByWebsite(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareByWebsite());
    }

    public static void sortByName(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareByName());
    }

}

