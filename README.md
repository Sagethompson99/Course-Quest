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

## Iteration 1 Report

The group decided on the idea when groups first met in class and Christopher proposed it. We met soon after to discuss how we would implement the idea which resulted in using a third-party Java package to scrape online sites for courses and information. We split app tasks into UI and web scraping. Christopher and Araf expressed interest in working on the UI functionality and UI design respectively, while Noah, Sage, and Jeetika chose web scraping. Sage also updated button pictures, optimized navigation buttons, created the app's logo, and illustrated a splash screen that Araf implemented. We chose user stories to test searching by course name and course provider. After working for a week, we obtained a working version with just Sage's scraper since Sage concluded that we needed a background thread for accessing the internet. Noah's and Jeetika's will be implemented in the 2nd iteration. We then rerouted our user stories to test our navigation buttons and search buttons since those were the targets of our Espresso tests. The previous user stories need to be tested with JUnit or Mockito. Looking back, we needed to use GitHub in a more organized manner so that files don't have to get removed and commit history doesn't get lost. Looking forward, for our next iteration, we need to begin testing earlier and upload to GitHub more efficiently/organize it better.
