# CourseQuest

CourseQuest is an Android application that aggregates online courses based on a user's search.

## Installation

The application is still underdevelopment. To obtain a version, pull the code and emulate via Android Studio. If it cannot recognize the jsoup package, add the jar as an external library.

Target API: 29

Working phone emulator/SDK: 
- Pixel 3 API 28
- Pixel 3 API 29
- Pixel 2 API 28
- Pixel 2 API 29

## Contributors

Members: Noah Weingand, Sage Thompson, Christopher Flippen, Jeetika Sainani, and Araf Rahman

## Design Pattern Description
This project employs the strategy design pattern by allowing the user to choose filters for their searches before searching. The strategy design pattern involves multiple interchangeable algorithms that are selected at runtime. In our app, the strategy design pattern is incorporated in the form of search filters which the user selects. After a user selects filters and types their search query, the filters and the search query are sent to the searchPageResults class which chooses the appropriate search method. These filters are chosen by the user by tapping a button next to the search bar which brings up a menu of the different options. Our use of filters is extendable because new methods for searching and returning results can be created for new filter types. The current filters for the app include: sorting the courses alphabetically (either A-Z or Z-A) and specifying which websites are searched (the user can specify any number of websites from the list of those sorted). Multiple search filters can be on at once, that is, the results can search a specific website, then display its results in A-Z order. By default (i.e. without any filters on), the search results are displayed in the order that they are found from the websites and all websites are searched by default.

## Iteration 1 Report

The group decided on the idea when groups first met in class and Christopher proposed it. We met soon after to discuss how we would implement the idea which resulted in using a third-party Java package to scrape online sites for courses and information. We split app tasks into UI and web scraping. Christopher and Araf expressed interest in working on the UI functionality and UI design respectively, while Noah, Sage, and Jeetika chose web scraping. Sage also updated button pictures, optimized navigation buttons, created the app's logo, and illustrated a splash screen that Araf implemented. We chose user stories to test searching by course name and course provider. After working for a week, we obtained a working version with just Sage's scraper since Sage concluded that we needed a background thread for accessing the internet. Noah's and Jeetika's will be implemented in the 2nd iteration. We then rerouted our user stories to test our navigation buttons and search buttons since those were the targets of our Espresso tests. The previous user stories need to be tested with JUnit or Mockito. Looking back, we needed to use GitHub in a more organized manner so that files don't have to get removed and commit history doesn't get lost. Looking forward, for our next iteration, we need to begin testing earlier and upload to GitHub more efficiently/organize it better.

## Iteration 2 Report

This iteration, we implemented a course save feature and the previously created web scrapers to add more courses for users. Sage wrote the save feature and made them display on home pages, even if a user returns to the app. Noah and Jeetika made their scrapers display courses correctly. Christopher found which design pattern our app fit the best and identified the structure and similiarities. Christopher originally looked at implementing the template design pattern to allow our Course objects to be abstract and therefore allowing different web scrapers to have different implementations of the object. This ended up not being very useful since all of the web scrapers use the same course object. Instead, we used the strategy pattern because it aligned well with how our app has multiple options and filters for searching. Chris also made sure the saved courses store links correctly so you could still access them from the home page. Araf created a tutorial page on the Settings page to help users work the app. Sage also added a dark mode feature. We were able to successfully finish the targeted user stories for this iteration. We continued UI testing in Espresso for the filter and save features. For our last iteration, we will work on results populating on pages instead of just a constant stream. Also, we will potentially add more scrapers and finish the tutorial pages in Settings.
