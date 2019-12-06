package com.example.coursequest;

import java.util.ArrayList;

class courseCompareByKeyword
{
    static void sort(ArrayList<Course> courses, String keyword)
    {
        keyword = keyword.substring(1, keyword.length()-1);

        for(int i = 0; i < courses.size(); i++)
        {
            String courseName = Course.getCourseName(courses.get(i)).toUpperCase();

            if(courseName.contains(keyword))
            {
                Course course = courses.get(i);
                courses.remove(course);
                courses.add(0, course);
            }
        }
    }
}
