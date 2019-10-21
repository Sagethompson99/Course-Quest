package com.example.coursequest;

import java.util.Comparator;

public class courseCompareByPrice implements Comparator<Course>
{
    @Override
    public int compare(Course course1, Course course2)
    {
        double courseCost1 = Course.getCourseCost(course1);
        double courseCost2 = Course.getCourseCost(course2);
        if (courseCost1 < courseCost2)
        {
            return -1;
        }
        if (courseCost1 == courseCost2)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}
