package com.example.coursequest;

import java.util.Comparator;

public class courseCompareByCourseName implements Comparator<Course>
{
    public int compare(Course course1, Course course2)
    {
        String name1 = Course.getCourseName(course1);
        String name2 = Course.getCourseName(course2);
        return name1.compareTo(name2);
    }
}
