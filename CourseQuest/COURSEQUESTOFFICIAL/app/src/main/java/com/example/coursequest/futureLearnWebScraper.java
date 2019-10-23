package com.example.coursequest;

import android.os.AsyncTask;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
This class was made by Sage
*/
public class futureLearnWebScraper extends AsyncTask<Object, String, ArrayList<Course>> {

    private final String url = "https://www.futurelearn.com";
    private Document doc;
    SearchPageResults page;

    @Override
    protected ArrayList<Course> doInBackground(Object... params) {
        String query = (String) params[0];
        page = (SearchPageResults) params[1];
        List<String>courses = new ArrayList<String>();
        List<String>courseDescriptions = new ArrayList<String>();
        List<String>courseLinks = new ArrayList<String>();

        ArrayList<Course> courseList = new ArrayList<Course>();

        try {
            doc = Jsoup.connect(url + "/search?q=" + query).get();

            Elements courseTitles = doc.select(".m-link-list__item h3");
            courses.addAll(courseTitles.eachText());

            Elements courseDesc = doc.select(".m-link-list__item p");
            courseDescriptions.addAll(courseDesc.eachText());

            Elements courseLink = doc.select("h3 > a");
            courseLinks.addAll(courseLink.eachAttr("href"));
        }
        catch(Exception e) {
            System.out.println("Invalid Search.");
        }

        //creates course objects from data
        for(int i = 0; i < courses.size(); i++) {
            Course a = new Course();
            a.setCourseLink(url + courseLinks.get(i));
            a.setCourseDescription(courseDescriptions.get(i));
            a.setCourseName(courses.get(i));
            a.setCourseWebsite(getCourseProvider());
            courseList.add(a);
        }

        return courseList;
    }

    @Override
    protected void onPostExecute(ArrayList<Course> list) {
        try {
            ArrayList<Button> buttons = page.createButtons(list);
            page.displayResults(list, buttons);
        }
        catch(Exception e){
            System.out.println(Course.getCourseName(list.get(0)));
        }
    }

    public String getCoursePrice() {
        return "Free";
    }

    public String getCourseProfessors() {
        return "";
    }

    public String getCourseProvider() {
        return "Future Learn";
    }

}
