package com.example.coursequest;

import java.util.ArrayList;

public class courseCompareByKeyword
{
    public static void sort(ArrayList<Course> courses, String keyword)
    {
        keyword = keyword.substring(1, keyword.length()-1);

        for(int i = 0; i < courses.size(); i++){
            String courseName = courses.get(i).getCourseName(courses.get(i)).toUpperCase();

            if(courseName.contains(keyword)){
                Course course = courses.get(i);
                courses.remove(i);
                courses.add(0, course);
            }
        }
    }
}
