package com.example.coursequest;

import java.util.Comparator;

public class courseCompareByNameZYX implements Comparator<Course>
{
    @Override
    public int compare(Course course1, Course course2) {
        String name1 = Course.getCourseName(course1);
        String name2 = Course.getCourseName(course2);
        return name2.compareTo(name1);
    }
}
