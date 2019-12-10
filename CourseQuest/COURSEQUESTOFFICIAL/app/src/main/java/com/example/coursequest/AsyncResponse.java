package com.example.coursequest;

import java.util.ArrayList;

/**found from https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
 *
 * This interface is used to inform SearchPageResults when a scraper AsyncTask is finished so that the results may be added to the UI
 */
public interface AsyncResponse {
    void scraperFinished(ArrayList<Course> courses);
}
