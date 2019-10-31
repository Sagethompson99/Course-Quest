package com.example.coursequest;

import android.os.AsyncTask;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 This class was made by Sage Thompson.
 Creates a web scraper that pulls information from futurelearn.com for a given user search query
 and passes that info to the Search Results Page class
*/
public class futureLearnWebScraper extends AsyncTask<Object, String, ArrayList<Course>> {

    private final String url = "https://www.futurelearn.com";
    private Document doc;
    SearchPageResults page;

    /**
     * doInBackground is a pre-defined method that runs an asynchronous task on a separate thread from the UI.
     *
     * This method connects to the FutureLearn URL, collects course information for a given user search query,
     * and creates course objects with that information
     */
    @Override
    protected ArrayList<Course> doInBackground(Object... params) {
        String query = (String) params[0];
        page = (SearchPageResults) params[1];
        List<String>courses = new ArrayList<String>();
        List<String>courseDescriptions = new ArrayList<String>();
        List<String>courseLinks = new ArrayList<String>();

        ArrayList<Course> courseList = new ArrayList<Course>();

        try {
            //connects to futurelearn URL
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

    /**
     * onPostExecute is a pre-defined method that is automatically executed after a successful
     * doInBackground() method execution
     *
     * This method calls createButtons() and displayResults() from the SearchPageResults class, passing
     * them an ArrayList of courses created in the doInBackground() method
     */
    @Override
    protected void onPostExecute(ArrayList<Course> list) {
        try {
            //creates course buttons with course info and displays them on SearchPageResults
            ArrayList<Button> buttons = page.createButtons(list);
            page.displayResults(list, buttons);
        }
        catch(Exception e){
            System.out.println("Results creation unsuccessful");
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
