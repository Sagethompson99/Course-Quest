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

## Espresso Testing Issues

If there are issues with Espresso failing tests, allow your emulator to be in developer mode. Once in developer mode, turn off animator duration scale, transition animation scale, and window animation scale. 

If the ShareButtonMessagesShareTest fails, run it separately from the other tests. Some of the phones we tested it on failed the test due to the Messages app opening and for Espresso waiting on a response, but there never is one as it is the end of the test. 

In the iteration 2, one issue we had was the search results used by the Espresso tests changed between the time of us posting the tests and the tests been run for our grade due to the websites updating. We have tried to prevent this issue, but if the tests fail again due to the websites updating, a link to a video of the tests passing is provided here. The video is low quality due to google drive compressing the file, but the test passing progress and the 26/26 tests passed is visible at the end
Video of Espresso tests passing:
https://drive.google.com/open?id=1r7ezNfT_Pabsltu36khNH1ILqpwk5JBQ

## Iteration 1 Report

The group decided on the idea when groups first met in class and Christopher proposed it. We met soon after to discuss how we would implement the idea which resulted in using a third-party Java package to scrape online sites for courses and information. We split app tasks into UI and web scraping. Christopher and Araf expressed interest in working on the UI functionality and UI design respectively, while Noah, Sage, and Jeetika chose web scraping. Sage also updated button pictures, optimized navigation buttons, created the app's logo, and illustrated a splash screen that Araf implemented. We chose user stories to test searching by course name and course provider. After working for a week, we obtained a working version with just Sage's scraper since Sage concluded that we needed a background thread for accessing the internet. Noah's and Jeetika's will be implemented in the 2nd iteration. We then rerouted our user stories to test our navigation buttons and search buttons since those were the targets of our Espresso tests. The previous user stories need to be tested with JUnit or Mockito. Looking back, we needed to use GitHub in a more organized manner so that files don't have to get removed and commit history doesn't get lost. Looking forward, for our next iteration, we need to begin testing earlier and upload to GitHub more efficiently/organize it better.

## Iteration 2 Report

This iteration, we implemented a course save feature and the previously created web scrapers to add more courses for users. Sage wrote the save feature and made them display on home pages, even if a user returns to the app. Noah and Jeetika made their scrapers display courses correctly. Christopher found which design pattern our app fit the best and identified the structure and similiarities. Christopher originally looked at implementing the template design pattern to allow our Course objects to be abstract and therefore allowing different web scrapers to have different implementations of the object. This ended up not being very useful since all of the web scrapers use the same course object. Instead, we used the strategy pattern because it aligned well with how our app has multiple options and filters for searching. Chris also made sure the saved courses store links correctly so you could still access them from the home page. Araf created a tutorial page (that needs to be implemented) on the Settings page to help users work the app. Sage also added a dark mode feature. We were able to successfully finish the targeted user stories for this iteration. Chris, Sage, Noah, and Jeetika peer-programmed the filter implementation. Noah continued UI testing in Espresso for the filter and save features. For our last iteration, we will work on results populating on pages instead of just a constant stream. Also, we will potentially add more scrapers and finish the tutorial pages in Settings.

## Iteration 3 Report

As the project comes to a close, we made our last changes to the app. We fixed up the UI by adding loading view while the app searches. Sage combined the like/dislike button to make it more fluid and added a dark mode feature. We implemented 3 more user stories: a share button, a popular searches feature, and a recent searches feature. Christopher cleaned up the project using Lint. Noah, Christoper, Jeetika, and Sage continued the Espresso testing as a group. We encountered some difficulties with developer animation settings with the loading view but were able to test on Christopher's machine successfully.

## Ignored Lint Warnings

Within activity_home.xml
- "This tag and its children can be replaced by a compound drawable" warning
  This view being seperated into an ImageView and TextView allows us to more easily separately control the visibility of the image and the text.

Within shareBarHandler.java
- "Custom view ImageView has setOnTouchListener called on it but does not override 'performClick'"
  This onTouchListener was made so that when a user taps outside of the share bar, it will automatically close. If a user was using accessibility features, they would interact with the cancel button instead of tapping somewhere else on the screen.

Vector Image Path Length Warnings
- Even with searches that return a very large number of results (such as "Science"), the size of these vector images has very little impact on performance.

Typo warnings are ignored because they are not accurate - the text they are referring to is not mispelled.
