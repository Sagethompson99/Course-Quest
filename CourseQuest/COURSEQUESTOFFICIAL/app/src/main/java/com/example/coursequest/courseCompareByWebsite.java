package com.example.coursequest;

import java.util.Comparator;

/**
This class will be used in the future to filter results
This class was made by Christopher Flippen
*/
public class courseCompareByWebsite implements Comparator<Course>
{

    @Override
    public int compare(Course course1, Course course2)
    {
        String websiteName1 = Course.getCourseWebsite(course1);
        String websiteName2 = Course.getCourseWebsite(course2);
        return websiteName1.compareTo(websiteName2);
    }
}
