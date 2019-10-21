package com.example.coursequest;/*
 * Class name: SkillShareScraper
 * Author: Noah Weingand
 * Description: Acquires class information listed by Skill Share via HTML
 * Project: 355 Final Project - Android App
 * Project Team: Runtime Terror
 * Course: CMSC 355 - Software Engineering
 * Professor: Dr. Damevski
 */
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SkillShareScraper extends Course {
	
	public SkillShareScraper() {
		//empty constructor
	}
	
	public SkillShareScraper(String query) {
		getCourses(query);
	}
	
	public ArrayList<Course> getCourses(String userSearch) {
		//userSearch = "algebra"; //remove when done when testing!
		ArrayList<String> skillTitles = new ArrayList<String>();
		ArrayList<String> skillLinksTemp = new ArrayList<String>();
		ArrayList<String> skillLinks = new ArrayList<String>();
		ArrayList<Course> allCourses = new ArrayList<Course>();
		
		try {
			Document doc = Jsoup.connect("https://www.skillshare.com/search?query=" + userSearch).get();
			//spaces don't matter because browser catches 
			
			Elements titles = doc.getElementsByClass("ss-card__title"); 
			Elements links = doc.getElementsByTag("a");

			for (Element title : titles) {
				//System.out.println("Title : " + title.text());
				skillTitles.add(title.text());
					//What else is needed? Images?
			}
			
			for (Element link : links) {
				//all links from page
				skillLinksTemp.add(link.attr("href"));
			}
			
			//links only containing classes (but has duplicates)
			for (int c = 0; c < skillLinksTemp.size(); c++) {
				if(skillLinksTemp.get(c).contains("https://www.skillshare.com/classes")) {
					skillLinks.add(skillLinksTemp.get(c));
				}
			}
			
			ArrayList<String> finalLinks = removeDuplicates(skillLinks);
			
//			for (int c = 0; c < finalLinks.size(); c++) {
//				System.out.println("Links fr: " + finalLinks.get(c));
//			}
			
			//System.out.println("array list counts: " + skillTitles.size() + " and " + finalLinks.size());
			allCourses = makeCourseObjects(skillTitles,finalLinks);
		}
		catch (IOException e) {
			e.printStackTrace(); //for debugging
		}
		return allCourses;
	}

	public static ArrayList<String> removeDuplicates(ArrayList<String> tempLinks) {
		ArrayList<String> links = new ArrayList<String>();
		for (String link : tempLinks) {
			if(!links.contains(link)) {
				links.add(link);
			}
		}
		return links;
	}
	
	public ArrayList<Course> makeCourseObjects(ArrayList<String> titles, ArrayList<String> links) {
		ArrayList<Course> allCourses = new ArrayList<Course>();
		if (titles.size() == links.size()) { //to make sure the titles and links aren't offset
			for(int i = 0; i < titles.size(); i++) {
				Course course = new Course();
				course.setCourseName(course, titles.get(i));
				course.setCourseLink(course, links.get(i));
				course.setCourseWebsite(course, getWebsite());
				course.setCost(course, getCost());
				//need to add course cost type
				allCourses.add(course);
			}
			return allCourses;
		}
		return null; //maybe return string notifying error?
	}
	
	public String getWebsite() {
		return "Skill Share";
	}
	
	public String getCost() {
		return "$15/month or $99/year";
	}
	
	public String getCourseCostType() {
		return "Subscription";
	}
	
//	public static void main(String[] args) {
//		SkillShareScraper scraper = new SkillShareScraper();
//		scraper.getCourses("test");
//	}
}