package edu.vcu.cs.app.cmsc355.cmsc355iteration1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;

/***************************************************************************************************
 * The Course object is used to encapsulate information found using the web scraper into a form that
 * the app's results screen can work with easily
 *
 * *************************************************************************************************
 * COURSE OBJECTS ARE EXPECTED TO HAVE SEVERAL (OR POSSIBLY MOSTLY) EMPTY INSTANCE VARIABLES
 * Course objects have so many instance variables to account for nearly any kind of online course
 * It would be very unlikely that a course has values for all of the instance variables and it some
 * websites make it extremely difficult to fill every instance variable just with the general
 * information included about the course and some instance variables such as certificateType may be
 * very rarely used if at all
 * *************************************************************************************************
 *
 * Most of the fields for a course object are straightforward due to their names, but some of them
 * use somewhat unexpected data types due to how different websites store information about their
 * classes, explanations of any unusual data type choices are below
 *
 * The majority of methods for Course objects are getter and setter methods, since the main purpose
 * of Course objects is to encapsulate information rather than to manipulate it
 *
 * Besides the getter and setter methods are several sorting methods. These methods use
 * Collections.sort with implementations of the Comparator class to efficiently sort and filter the
 * results of the search
 *
 * courseName and courseSubject are different fields to differentiate between a specific course
 * topic and the general subject it is a part of. For example, CMSC 355 would have a courseName
 * of "Software Engineering" and a courseSubject of "Computer Science"
 *
 * professors is a string containing all of the professors or universities that created the course
 *
 * Enums of types costTypeEnum and difficultyEnum are used to describe the payment models for
 * different classes (free, per month membership, or one time cost) and the difficulties of
 * different classes (easy, medium, hard, etc.).
 * Multiple terms are used for each difficulty due to the differing wording from different websites,
 * but difficultyEnumToInt allows for these to be compared and sorted.
 * The DEFAULT enum is used if no information is found to set the enum
 *
 * The rating of a course is a string since different websites use different scales for rating, this
 * could be changed in the future to a numeric value to allow for sorting by rating
 *
 * offersDegree, degreeUniversity, degreeType, offersCertificate, and certificateType are used if
 * the course offers progress towards a university degree or some sort of professional certification
 * The boolean offersDegree and offersCertificate values are used for easier checking for this
 * information since most courses most likely will not offer credit towards a certification or
 * degree
 *
 * courseLength refers to the number of hours that the course is estimated to take to complete
 *
 **************************************************************************************************/
public class Course implements Parcelable
{
    @Override
    public int describeContents()
    {
        return 0;
    }

    public Course(Parcel in)
    {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags)
    {
        parcel.writeString(courseSubject);
        parcel.writeString(courseName);
        parcel.writeString(courseWebsite);
        parcel.writeString(courseLink);
        parcel.writeString(courseDescription);
        parcel.writeString(professors);
        parcel.writeString(costType.toString());
        parcel.writeDouble(courseCost);
        parcel.writeString(rating);
        parcel.writeBoolean(offersDegree);
        parcel.writeString(degreeUniversity);
        parcel.writeString(degreeType);
        parcel.writeBoolean(offersCertificate);
        parcel.writeString(certificateType);
        parcel.writeString(difficulty.toString());
        parcel.writeDouble(courseLength);
    }

    private void readFromParcel(Parcel in)
    {
        courseSubject = in.readString();
        courseName = in.readString();
        courseWebsite = in.readString();
        courseLink = in.readString();
        courseDescription = in.readString();
        professors = in.readString();
        costType = costTypeStringToEnum(in.readString());
        courseCost = in.readDouble();
        rating = in.readString();
        offersDegree = in.readBoolean();
        degreeUniversity = in.readString();
        degreeType = in.readString();
        offersCertificate = in.readBoolean();
        certificateType = in.readString();
        difficulty = difficultyStringToEnum(in.readString());
        courseLength = in.readDouble();

    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator()
            {
                public Course createFromParcel(Parcel in)
                {
                    return new Course(in);
                }

                public Course[] newArray(int size)
                {
                    return new Course[size];
                }
            };

    public enum costTypeEnum
    {
        FREE, MEMBERSHIP, ONETIMECOST, DEFAULT;
    }

    public enum difficultyEnum
    {
        EASY, MEDIUM, HARD, BEGINNER, INTERMEDIATE, ADVANCED, DEFAULT;
    }

    public static int costTypeEnumToInt(costTypeEnum c)
    {
        int costType = -1;
        if (c == costTypeEnum.FREE)
        {
            costType = 0;
        }
        else if (c == costTypeEnum.MEMBERSHIP)
        {
            costType = 1;
        }
        else if (c == costTypeEnum.ONETIMECOST)
        {
            costType = 2;
        }
        return costType;
    }

    public static String costTypeEnumToString(costTypeEnum c)
    {
        String costType = "";
        if (c == costTypeEnum.FREE)
        {
            costType = "FREE";
        }
        else if (c == costTypeEnum.MEMBERSHIP)
        {
            costType = "MEMBERSHIP";
        }
        else if (c == costTypeEnum.ONETIMECOST)
        {
            costType = "ONETIMECOST";
        }
        return costType;
    }

    public static costTypeEnum costTypeStringToEnum(String c)
    {
        costTypeEnum type = costTypeEnum.DEFAULT;
        if (c.toUpperCase().equals("FREE"))
        {
            type = costTypeEnum.FREE;
        }
        else if (c.toUpperCase().equals("MEMBERSHIP"))
        {
            type = costTypeEnum.MEMBERSHIP;
        }
        else if (c.toUpperCase().equals("ONETIMECOST"))
        {
            type = costTypeEnum.ONETIMECOST;
        }
        return type;
    }

    //Difficulty is listed numerically from easiest (0) to highest (2)
    public static int difficultyEnumToInt(difficultyEnum d)
    {
        int difficulty = -1;
        if (d == difficultyEnum.EASY || d == difficultyEnum.BEGINNER)
        {
            difficulty = 0;
        }
        else if (d == difficultyEnum.MEDIUM || d == difficultyEnum.INTERMEDIATE)
        {
            difficulty = 1;
        }
        else if (d == difficultyEnum.HARD || d == difficultyEnum.ADVANCED)
        {
            difficulty = 2;
        }
        return difficulty;
    }

    public static difficultyEnum difficultyStringToEnum(String d)
    {
        if (d.toUpperCase().equals("EASY"))
        {
            return difficultyEnum.EASY;
        }
        else if (d.toUpperCase().equals("MEDIUM"))
        {
            return difficultyEnum.MEDIUM;
        }
        else if (d.toUpperCase().equals("HARD"))
        {
            return difficultyEnum.HARD;
        }
        else if (d.toUpperCase().equals("BEGINNER"))
        {
            return difficultyEnum.BEGINNER;
        }
        else if (d.toUpperCase().equals("INTERMEDIATE"))
        {
            return difficultyEnum.INTERMEDIATE;
        }
        else if (d.toUpperCase().equals("ADVANCED"))
        {
            return difficultyEnum.ADVANCED;
        }
        return difficultyEnum.DEFAULT;
    }

    public static String difficultyEnumToString(difficultyEnum d)
    {
        String difficulty = "";
        if (d == difficultyEnum.EASY)
        {
            difficulty = "EASY";
        }
        else if (d == difficultyEnum.BEGINNER)
        {
            difficulty = "BEGINNER";
        }
        else if (d == difficultyEnum.MEDIUM)
        {
            difficulty = "MEDIUM";
        }
        else if (d == difficultyEnum.INTERMEDIATE)
        {
            difficulty = "INTERMEDIATE";
        }
        else if (d == difficultyEnum.HARD)
        {
            difficulty = "HARD";
        }
        else if (d == difficultyEnum.ADVANCED)
        {
            difficulty = "ADVANCED";
        }
        return difficulty;
    }

    private String courseSubject = "";
    private String courseName = "";
    private String courseWebsite = "";
    private String courseLink = "";
    private String courseDescription = "";
    private String professors = "";
    private costTypeEnum costType = costTypeEnum.DEFAULT;
    private double courseCost = -1;
    private String rating = "";
    private boolean offersDegree = false;
    private String degreeUniversity = "";
    private String degreeType = "";
    private boolean offersCertificate = false;
    private String certificateType = "";
    private difficultyEnum difficulty = difficultyEnum.DEFAULT;
    private double courseLength = -1;

    public void setCourseSubject(Course course, String courseSubject)
    {
        course.courseSubject = courseSubject;
    }

    public void setCourseName(Course course, String courseName)
    {
        course.courseName = courseName;
    }

    public void setCourseWebsite(Course course, String courseWebsite)
    {
        course.courseWebsite = courseWebsite;
    }

    public void setCourseLink(Course course, String courseLink)
    {
        course.courseLink = courseLink;
    }

    public void setCourseDescription(Course course, String courseDescription)
    {
        course.courseDescription = courseDescription;
    }

    public void setProfessors(Course course, String professors)
    {
        course.professors = professors;
    }

    public void setCostType(Course course, String costType)
    {
        if (costType.toUpperCase().equals("FREE"))
        {
            course.costType = costTypeEnum.FREE;
        }
        else if (costType.toUpperCase().equals("MEMBERSHIP"))
        {
            course.costType = costTypeEnum.MEMBERSHIP;
        }
        else if (costType.toUpperCase().equals("ONETIMECOST"))
        {
            course.costType = costTypeEnum.ONETIMECOST;
        }
    }

    public void setCost(Course course, int courseCost)
    {
        course.courseCost = courseCost;
    }

    public void setRating(Course course, String rating)
    {
        course.rating = rating;
    }

    public void setOffersDegree(Course course, boolean offersDegree)
    {
        course.offersDegree = offersDegree;
    }

    public void setDegreeUniversity(Course course, String degreeUniversity)
    {
        course.degreeUniversity = degreeUniversity;
    }

    public void setDegreeType(Course course, String degreeType)
    {
        course.degreeType = degreeType;
    }

    public void setOffersCertificate(Course course, boolean offersCertificate)
    {
        course.offersCertificate = offersCertificate;
    }

    public void setCertificate(Course course, String certificateType)
    {
        course.certificateType = certificateType;
    }

    public void setDifficulty(Course course, String difficulty)
    {
        if (difficulty.toUpperCase().equals("EASY"))
        {
            course.difficulty = difficultyEnum.EASY;
        }
        else if (difficulty.toUpperCase().equals("BEGINNER"))
        {
            course.difficulty = difficultyEnum.BEGINNER;
        }
        else if (difficulty.toUpperCase().equals("MEDIUM"))
        {
            course.difficulty = difficultyEnum.MEDIUM;
        }
        else if (difficulty.toUpperCase().equals("INTERMEDIATE"))
        {
            course.difficulty = difficultyEnum.INTERMEDIATE;
        }
        else if (difficulty.toUpperCase().equals("HARD"))
        {
            course.difficulty = difficultyEnum.HARD;
        }
        else if (difficulty.toUpperCase().equals("ADVANCED"))
        {
            course.difficulty = difficultyEnum.ADVANCED;
        }
    }

    public void setCourseLength(Course course, int courseLength)
    {
        course.courseLength = courseLength;
    }

    public static String getCourseSubject(Course course)
    {
        return course.courseSubject;
    }

    public static String getCourseName(Course course)
    {
        return course.courseName;
    }

    public static String getCourseWebsite(Course course)
    {
        return course.courseWebsite;
    }

    public static String getCourseLink(Course course)
    {
        return course.courseLink;
    }

    public static String getCourseDescription(Course course)
    {
        return course.courseDescription;
    }

    public static String getProfessors(Course course)
    {
        return course.professors;
    }

    public static costTypeEnum getCostType(Course course)
    {
        return course.costType;
    }

    public static String getCostTypeString(Course course)
    {
        return costTypeEnumToString(course.costType);
    }

    public static double getCourseCost(Course course)
    {
        return course.courseCost;
    }

    public static String getRating(Course course)
    {
        return course.rating;
    }

    public static boolean getOffersDegree(Course course)
    {
        return course.offersDegree;
    }

    public static String getDegreeUniversity(Course course)
    {
        return course.degreeUniversity;
    }

    public static String getDegreeType(Course course)
    {
        return course.degreeType;
    }

    public static boolean getOffersCertificate(Course course)
    {
        return course.offersCertificate;
    }

    public static String getCertificateType(Course course)
    {
        return course.certificateType;
    }

    public static difficultyEnum getDifficulty(Course course)
    {
        return course.difficulty;
    }

    public static String getDifficultyString(Course course)
    {
        return difficultyEnumToString(course.difficulty);
    }

    public static double getCourseLength(Course course)
    {
        return course.courseLength;
    }

    public static void sortBySubject(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareBySubject());
    }

    public static void sortByCost(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareByPrice());
    }

    public static void sortByDifficulty(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareByDifficulty());
    }

    public static void sortByLength(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareByLength());
    }

    public static void sortByWebsite(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareByWebsite());
    }

    public static void sortByName(ArrayList<Course> courses)
    {
        Collections.sort(courses, new courseCompareByName());
    }

}
