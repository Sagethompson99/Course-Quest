import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class futureLearnWebScraper {

	private final String url = "https://www.futurelearn.com";
	private Document doc;
	
	public ArrayList<Course> getCourses(String query) {
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

