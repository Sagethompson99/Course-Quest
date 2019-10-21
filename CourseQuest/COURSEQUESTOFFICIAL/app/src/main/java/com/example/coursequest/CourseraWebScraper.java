package com.example.coursequest;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseraWebScraper {

		
	private Document document;

	public void search(String searchTerm){
			
		List<String> titlesList = new ArrayList<String>();
		List<String> univs = new ArrayList<String>();
		List<String> types = new ArrayList<String>();
		List<String> difficulties = new ArrayList<String>();
		List<String> allLinks = new ArrayList<String>();
		List<String> courseLinks = new ArrayList<String>();
		List<String> ratings = new ArrayList<String>();
		
		try {
			
			for(int i = 1; i < 4; i++) {

				document = Jsoup.connect("https://www.coursera.org/search?query=" + searchTerm + "\"&indices%5Bprod_all_products%5D%5Bpage%5D=" + Integer.toString(i) + "&indices%5Bprod_all_products%5D%5Bconfigure%5D%5BclickAnalytics%5D=true&indices%5Bprod_all_products%5D%5Bconfigure%5D%5BhitsPerPage%5D=10&configure%5BclickAnalytics%5D=true\"").get();
				
				
				Elements titles = document.getElementsByClass("color-primary-text card-title headline-1-text");
				titlesList.addAll(titles.eachText());
				
				Elements university = document.getElementsByClass("partner-name");
				univs.addAll(university.eachText());
			
				Elements courseType = document.getElementsByClass("productTypePill_vy0uva-o_O-gradientBg_98hw3a");
				types.addAll(courseType.eachText());
				
				Elements difficulty = document.getElementsByClass("difficulty");
				difficulties.addAll(difficulty.eachText());
				
				Elements links = document.getElementsByTag("a");
				allLinks.addAll(links.eachAttr("href"));
				
				Elements rating = document.getElementsByClass("ratings-text");
				ratings.addAll(rating.eachText());
				
				
			}
			
			getCourseLinks(allLinks, courseLinks);
			makeCourseObjects(titlesList, ratings, courseLinks, "Coursera", searchTerm);
					
		}

		catch(Exception e){
			System.out.println("No search results");
		}
	}
	
	
	public static void getCourseLinks(List<String> allLinks, List<String> courseLinks) {
		for(int r = 0; r < allLinks.size(); r ++) {
			String potential_Link = allLinks.get(r).toString();
			if(potential_Link.contains("learn/") || potential_Link.contains("specialization") || potential_Link.contains("professional-certificates")) {
				courseLinks.add("coursera.org" + potential_Link);
			}
		}
	}

	
	public ArrayList<Course> makeCourseObjects(List<String> titles, List<String> ratings, List<String> links, String website, String subject) {
		ArrayList<Course> allCourses = new ArrayList<Course>();
		for(int i = 0; i < titles.size(); i++) {
			Course courseObj = new Course();
			courseObj.setCourseLink(links.get(i));
			courseObj.setCourseName(titles.get(i));
			courseObj.setCourseSubject(subject);
			courseObj.setCourseWebsite(website);
			courseObj.setRating(ratings.get(i));
			allCourses.add(courseObj);
		}

		return allCourses;
		
	}
	
	
	public static void main(String[] args) {
		CourseraWebScraper webScraper = new CourseraWebScraper();
	    String searchTerm = "Biology";
	    webScraper.search(searchTerm);
	}
		
}
