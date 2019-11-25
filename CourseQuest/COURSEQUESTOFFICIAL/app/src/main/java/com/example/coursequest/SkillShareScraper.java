/*
 * Class name: SkillShareScraper
 * Author: Noah Weingand
 * Description: Acquires class information listed by Skill Share via HTML
 * Project: 355 Final Project - Android App
 * Project Team: Runtime Terror
 * Course: CMSC 355 - Software Engineering
 * Professor: Dr. Damevski
 */
package com.example.coursequest;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.widget.Button;

public class SkillShareScraper extends AsyncTask<Object, String, ArrayList<Course>> {

	private final String url = "https://www.skillshare.com/search?query=";
	SearchPageResults page;
	ArrayList<Course> finalCourses;

	protected ArrayList<Course> doInBackground(Object... params) {
		String userSearch = (String) params[0];
		page = (SearchPageResults) params[1];
		ArrayList<String> skillTitles = new ArrayList<String>();
		ArrayList<String> skillLinksTemp = new ArrayList<String>();
		ArrayList<String> skillLinks = new ArrayList<String>();
		ArrayList<Course> allCourses = new ArrayList<Course>();
		try {
			Document doc = Jsoup.connect(url + userSearch).get();

			Elements titles = doc.getElementsByClass("ss-card__title");
			Elements links = doc.getElementsByTag("a");

			for (Element title : titles) {
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

			allCourses = makeCourseObjects(skillTitles,finalLinks);
		}
		catch (IOException e) {
			e.printStackTrace(); //for debugging
		}
		return allCourses;
	}

	public ArrayList<String> removeDuplicates(ArrayList<String> tempLinks) {
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
				course.setCourseDescription(course,getNoDescription());
				//need to add course cost type
				allCourses.add(course);
			}

			finalCourses = allCourses;
			return allCourses;
		}
		return null; //maybe return string notifying error?
	}

	public ArrayList<Course> getCourses() {
		return finalCourses;
	}

	
	public String getWebsite() {
		return "Skill Share";
	}
	
	public String getCost() {
		return "$15/month or $99/year";
	}

	public String getNoDescription() {
		return "No description. Click for more information about this course.";
	}
	
	public String getCourseCostType() {
		return "Subscription";
	}


	protected void onPostExecute(ArrayList<Course> list) {
		try {
			SearchPageResults.courses.addAll(list);
			page.scraperFinished();
		}
		catch(Exception e) {
			System.out.println("ERROR [Skill Share]: couldn't get classes :(");
		}
	}

}