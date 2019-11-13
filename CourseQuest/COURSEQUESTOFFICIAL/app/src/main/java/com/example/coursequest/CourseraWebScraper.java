//Code by Jeetika Sainani
package com.example.coursequest;

import android.widget.Button;
import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CourseraWebScraper extends AsyncTask<Object, String, ArrayList<Course>>{

		
	private Document document;
	private Document document1;
	SearchPageResults page;


	protected ArrayList<Course> doInBackground(Object... params) {
		String searchTerm = (String) params[0];
		page = (SearchPageResults) params[1];
		List<String> titlesList = new ArrayList<String>();
		List<String> allLinks = new ArrayList<String>();
		List<String> courseLinks = new ArrayList<String>();
		List<String> descriptions = new ArrayList<>();
		ArrayList<Course> allCourses = new ArrayList<>();
		
		try {
			//first page of results
			document1 = Jsoup.connect("https://www.coursera.org/search?query=" + searchTerm + "&indices%5Bprod_all_products%5D%5Bpage%5D=1&indices%5Bprod_all_products%5D%5Bconfigure%5D%5BclickAnalytics%5D=true&indices%5Bprod_all_products%5D%5Bconfigure%5D%5BhitsPerPage%5D=10&configure%5BclickAnalytics%5D=true").get();

			Elements numPagesTotal = document1.getElementsByClass("filter-menu-and-number-of-results horizontal-box");
			Element totalResults = numPagesTotal.first();
			String[] numResults = totalResults.text().split(" ");
			int numOfResults = Integer.parseInt(numResults[1]);

			int numPages = (numOfResults/10);

			if(numPages % 10 != 0) {
				numPages += 1;
			}

			if(numPages > 5){
				numPages = 5;
			}


			for(int pageNumber = 1; pageNumber < numPages; pageNumber++) {

				document = Jsoup.connect("https://www.coursera.org/search?query=" + searchTerm + "&indices%5Bprod_all_products_term_optimization%5D%5Bpage%5D=" + pageNumber + "&indices%5Bprod_all_products_term_optimization%5D%5Bconfigure%5D%5BclickAnalytics%5D=true&indices%5Bprod_all_products_term_optimization%5D%5Bconfigure%5D%5BhitsPerPage%5D=10&configure%5BclickAnalytics%5D=true").get();

				Elements titles = document.getElementsByClass("color-primary-text card-title headline-1-text");
				titlesList.addAll(titles.eachText());
				
				Elements links = document.getElementsByTag("a");
				allLinks.addAll(links.eachAttr("href"));

			}
			
		}

		catch(Exception e){
			System.out.println("No search results");
		}

		String desc = "No description. Click for more information about this course.";
		for(int j = 0; j < titlesList.size(); j++){
			descriptions.add(desc);
		}

		getCourseLinks(allLinks, courseLinks);
		for(int i = 0; i < titlesList.size(); i++) {
			Course courseObj = new Course();
			courseObj.setCourseLink(allLinks.get(i));
			courseObj.setCourseName(titlesList.get(i));
			courseObj.setCourseSubject(searchTerm);
			courseObj.setCourseWebsite("Coursera");
			courseObj.setCourseDescription(descriptions.get(i));
			allCourses.add(courseObj);
		}
		return allCourses;

	}
	
	
	public static void getCourseLinks(List<String> allLinks, List<String> courseLinks) {
		for(int r = 0; r < allLinks.size(); r ++) {
			String potential_Link = allLinks.get(r);
			if(potential_Link.contains("learn/") || potential_Link.contains("specializations/") || potential_Link.contains("professional-certificates/")) {
				courseLinks.add("https://www.coursera.org" + potential_Link);
			}
		}
	}


	protected void onPostExecute(ArrayList<Course> list) {
		try {
			//adds courses to a course ArrayList in SearchPageResults
			SearchPageResults.courses.addAll(list);
			page.scraperFinished();
		}
		catch(Exception e){
			System.out.println("Results creation unsuccessful");
		}
	}
		
}
