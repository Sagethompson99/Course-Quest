package com.example.coursequest;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

class codeCademyWebScraper extends AsyncTask<Object, String, ArrayList<Course>> {


	private AsyncResponse informer = null;

	/**
	 * doInBackground is a pre-defined method that runs an asynchronous task on a separate thread from the UI.
	 *
	 * This method connects to the CodeCademy URL, collects course information for a given user search query,
	 * and creates course objects with that information
	 */
	@Override
	protected ArrayList<Course> doInBackground(Object... params) {
		Document doc;
		final String url = "https://www.codecademy.com/search?page=";
		String query = (String) params[0];
		informer = (AsyncResponse) params[1];
		List<String>courses = new ArrayList<>();
		List<String>courseDescriptions = new ArrayList<>();
		List<String>courseLinks = new ArrayList<>();

		ArrayList<Course> courseList = new ArrayList<>();

		int pageNumber = 1; //current page number
		int numPages; //number of pages to parse (determined by numResults)
		
	    try {        	
	    	// finds the number of search results
	    	doc = Jsoup.connect(url + pageNumber + "&query=" + query).get();
	    	Elements numResultsText = doc.getElementsByClass("resultsInfo__1kdRooMNwAuwJCXclRXaPY");
	    	String unformattedNumResults = numResultsText.first().text();
	    	int numResults = Integer.parseInt(unformattedNumResults.substring(0, 2));
	    	
	    	//determines number of pages to parse based on number of search results
	    	numPages = (numResults/10);
	    	    if(numResults % 10 != 0)
	    	    	numPages += 1;
	    	   
	    	//loops through numPages of search results
	    	for(int i = 1; i <= numPages; i++) {
	    	   doc = Jsoup.connect(url + pageNumber + "&query="+ query).get();	
	    	   
	    	   Elements courseNames = doc.getElementsByClass("title__3sKtpbrKLRKxJviSPPEYHM");
	    	   courses.addAll(courseNames.eachText()); 	  
	    	   
	    	   Elements courseDesc = doc.getElementsByClass("shell__1WWxiF0OcGIAbVS1rMqur3 result__1H8LBMtqhGsaDZ6nXppDyY");
	    	   courseDescriptions.addAll(courseDesc.eachText());
	    	   
	    	   Elements courseLink = doc.getElementsByClass("resultLink__t5uUGIWxRGQlJeR0Uk8bG");
	    	   courseLinks.addAll(courseLink.eachAttr("href"));
	    	  
	    	   pageNumber++;
	    	}
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    	System.out.println("[CodeCademy] Invalid Search.");
	    }
		
	    /*
	     * Formats course descriptions by removing unnecessary info from element shell.
	     * Formats courseLinks to be full links rather than partial links.
	     */
	    for(int i = 0; i < courses.size(); i++) {
	    	String unformattedDesc = courseDescriptions.get(i);
	    	int courseNameLength = courses.get(i).length() + 8;
	    	courseDescriptions.set(i, unformattedDesc.substring(courseNameLength));
		    
	    	String baseURL = "https://www.codecademy.com";
	    	String fullLink = baseURL + courseLinks.get(i);
	    	courseLinks.set(i, fullLink);
	    }
	    
	    //creates course objects from data
	    for(int i = 0; i < courses.size(); i++) {
	    	Course course = new Course();
	    	course.setCourseLink(courseLinks.get(i));
	    	course.setCourseDescription(courseDescriptions.get(i));
	    	course.setCourseName(courses.get(i));
	        course.setCourseWebsite("CodeCademy");
	    	courseList.add(course);
	    } 
	    
	    return courseList;
	}

	/**
	 * onPostExecute is a pre-defined method that is automatically executed after a successful
	 * doInBackground() method execution
	 *
	 * This method passes an ArrayList of courses created in the doInBackground() method to SearchResultsPage
	 */
	@Override
	protected void onPostExecute(ArrayList<Course> list) {
		try {
			informer.scraperFinished(list);
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("[CodeCademy] Results creation unsuccessful");
		}
	}

}

