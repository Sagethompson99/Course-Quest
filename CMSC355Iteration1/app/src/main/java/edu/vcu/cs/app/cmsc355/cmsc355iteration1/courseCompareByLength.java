package edu.vcu.cs.app.cmsc355.cmsc355iteration1;

import java.util.Comparator;

public class courseCompareByLength implements Comparator<Course>
{
    @Override
    public int compare(Course course1, Course course2)
    {
        /**double length1 = Course.getCourseLength(course1);
        double length2 = Course.getCourseLength(course2);
        if (length1 < length2)
        {
            return -1;
        }
        else if (length1 == length2)
        {
            return 0;
        }
        else
        {
            return 1;
        }*/
        return 1;
    }
}
