package com.example.coursequest;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class codeCademyWebScraper {
	
	private final String url = "https://www.codecademy.com/search?page=";
	private Document doc;
	
	public ArrayList<Course> getCourses(String query) {
		List<String> courses = new ArrayList<String>();
		List<String>courseDescriptions = new ArrayList<String>();
		List<String>courseLinks = new ArrayList<String>();
		
		ArrayList<Course> courseList = new ArrayList<Course>();
		
		int pageNumber = 1; //current page number
		int numPages = 0; //number of pages to parse (0 by default)
		
	    try {        	
	    	// finds the number of search results
	    	doc = Jsoup.connect(url + pageNumber + "&query=" + query).get();
	    	Elements numResults = doc.getElementsByClass("resultsInfo__1kdRooMNwAuwJCXclRXaPY");
	    	String str = numResults.first().text();
	    	String numOfResults = str.substring(0, 2);
	    	
	    	//determines number of pages to parse based on number of search results
	    	numPages = (Integer.parseInt(numOfResults)/10);
	    	    if(numPages % 10 != 0)
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
	    	System.out.println("Invalid Search.");
	    }
		
	    /*
	     * Formats course descriptions by removing unnecessary info from element shell.
	     * Formats courseLinks to be full links rather than partial links.
	     */
	    for(int i = 0; i < courses.size(); i++) {
	    	String unformattedDesc = courseDescriptions.get(i);
	    	int courseNameLength = courses.get(i).length() + 8;
	    	courseDescriptions.set(i, unformattedDesc.substring(courseNameLength, unformattedDesc.length()));
		    
	    	String baseURL = "https://www.codecademy.com";
	    	String fullLink = baseURL + courseLinks.get(i);
	    	courseLinks.set(i, fullLink);
	    }
	    
	    //creates course objects from data
	    for(int i = 0; i < courses.size(); i++) {
	    	Course a = new Course();
	    	a.setCourseLink(courseLinks.get(i));
	    	a.setCourseDescription(courseDescriptions.get(i));
	    	a.setCourseName(courses.get(i));
	        a.setCourseWebsite(getCourseProvider());
	    	courseList.add(a);	    	
	    } 
	    
	    return courseList;
	}
	
	public String getCoursePrice() {
		return "$19.99/Month";
	}
	
	public String getCourseProfessors() {
		return "";
	}
	
	public String getCourseRating() {
		return "";
	}
	
	public String getCourseProvider() {
		return "CodeCademy";
	}

}

