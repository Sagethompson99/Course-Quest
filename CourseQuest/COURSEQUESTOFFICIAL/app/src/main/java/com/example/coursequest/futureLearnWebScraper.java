package com.example.coursequest;

import android.os.AsyncTask;

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
class futureLearnWebScraper extends AsyncTask<Object, String, ArrayList<Course>> {

    private AsyncResponse informer = null;

    /**
     * doInBackground is a pre-defined method that runs an asynchronous task on a separate thread from the UI.
     *
     * This method connects to the FutureLearn URL, collects course information for a given user search query,
     * and creates course objects with that information
     */
    @Override
    protected ArrayList<Course> doInBackground(Object... params) {
        final String url = "https://www.futurelearn.com";
        Document doc;
        String query = (String) params[0];
        informer = (AsyncResponse) params[1];
        List<String>courses = new ArrayList<>();
        List<String>courseDescriptions = new ArrayList<>();
        List<String>courseLinks = new ArrayList<>();

        ArrayList<Course> courseList = new ArrayList<>();

        try {
            //connects to future learn URL
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
            a.setCourseWebsite("Future Learn");
            courseList.add(a);
        }

        return courseList;
    }

    /**
     * onPostExecute is a pre-defined method that is automatically executed after a successful
     * doInBackground() method execution
     */
    @Override
    protected void onPostExecute(ArrayList<Course> list) {
        try {
            informer.scraperFinished(list);
        }
        catch(Exception e){
            System.out.println("Results creation unsuccessful");
        }
    }

}
