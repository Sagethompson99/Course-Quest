package edu.vcu.cs.app.cmsc355.cmsc355iteration1;

import java.util.Comparator;

public class courseCompareByDifficulty implements Comparator<Course>
{
    @Override
    public int compare(Course course1, Course course2)
    {
        int difficulty1 = Course.difficultyEnumToInt(Course.getDifficulty(course1));
        int difficulty2 = Course.difficultyEnumToInt(Course.getDifficulty(course2));
        if (difficulty1 < difficulty2)
        {
            return -1;
        }
        else if (difficulty1 == difficulty2)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}
