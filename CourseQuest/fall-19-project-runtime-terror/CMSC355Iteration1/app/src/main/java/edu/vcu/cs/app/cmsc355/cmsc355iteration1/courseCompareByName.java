package edu.vcu.cs.app.cmsc355.cmsc355iteration1;

import java.util.Comparator;

public class courseCompareByName implements Comparator<Course>
{

    @Override
    public int compare(Course course1, Course course2)
    {
        String name1 = Course.getCourseName(course1);
        String name2 = Course.getCourseName(course2);
        return name1.compareTo(name2);
    }
}
