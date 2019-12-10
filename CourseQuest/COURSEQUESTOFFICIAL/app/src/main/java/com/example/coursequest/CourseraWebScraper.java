//Code by Jeetika Sainani
package com.example.coursequest;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

class CourseraWebScraper extends AsyncTask<Object, String, ArrayList<Course>>{

	private AsyncResponse informer = null;

	/**
	 * doInBackground is a pre-defined method that runs an asynchronous task on a separate thread from the UI.
	 *
	 * This method connects to the FutureLearn URL, collects course information for a given user search query,
	 * and creates course objects with that information
	 */
	protected ArrayList<Course> doInBackground(Object... params) {
		Document document;
		final String url = "https://www.coursera.org/search?query=";
		String searchTerm = (String) params[0];
		informer = (AsyncResponse) params[1];
		List<String> titlesList = new ArrayList<>();
		List<String> courseLinks = new ArrayList<>();
		ArrayList<Course> allCourses = new ArrayList<>();
		
		try {
			//first page of results
			document = Jsoup.connect(url + searchTerm).get();

			Elements numPagesTotal = document.getElementsByClass("filter-menu-and-number-of-results horizontal-box");
			Element totalResults = numPagesTotal.first();
			String[] numResults = totalResults.text().split(" ");
			int numOfResults = Integer.parseInt(numResults[1]);

			//determines number of pages to parse based on number of search results
			int numPages = (numOfResults/10);

			if(numOfResults % 10 != 0) {
				numPages += 1;
			}

			if(numPages > 5){
				numPages = 5;
			}


			for(int pageNumber = 1; pageNumber < numPages; pageNumber++) {

				document = Jsoup.connect(url + searchTerm + "&indices%5Bprod_all_products_term_optimization%5D%5Bpage%5D=" + pageNumber + "&indices%5Bprod_all_products_term_optimization%5D%5Bconfigure%5D%5BclickAnalytics%5D=true&indices%5Bprod_all_products_term_optimization%5D%5Bconfigure%5D%5BhitsPerPage%5D=10&configure%5BclickAnalytics%5D=true").get();

				Elements titles = document.getElementsByClass("color-primary-text card-title headline-1-text");
				titlesList.addAll(titles.eachText());

				Elements links = document.select("a.rc-DesktopSearchCard.anchor-wrapper");
				courseLinks.addAll(links.eachAttr("href"));

			}
			
		}

		catch(Exception e){
			System.out.println("No search results");
		}

		String courseDescription = "No description. Click for more information about this course.";

		for(int i = 0; i < titlesList.size(); i++) {
			courseLinks.set(i, "https://www.coursera.org" + courseLinks.get(i));

			Course courseObj = new Course();
			courseObj.setCourseLink(courseLinks.get(i));
			courseObj.setCourseName(titlesList.get(i));
			courseObj.setCourseWebsite("Coursera");
			courseObj.setCourseDescription(courseDescription);
			allCourses.add(courseObj);
		}
		
		return allCourses;
	}

	protected void onPostExecute(ArrayList<Course> list) {
		try {
			informer.scraperFinished(list);
		}
		catch(Exception e){
			System.out.println("[Coursera] Results creation unsuccessful");
		}
	}
		
}
