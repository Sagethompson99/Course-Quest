package com.example.coursequest;

import java.util.Comparator;

/**
This class will be used in a future iteration for filtering results
This class was made by Christopher Flippen
*/
public class courseCompareBySubject implements Comparator<Course>
{
    public int compare(Course course1, Course course2)
    {
        String name1 = Course.getCourseSubject(course1);
        String name2 = Course.getCourseSubject(course2);
        return name1.compareTo(name2);
    }
}
