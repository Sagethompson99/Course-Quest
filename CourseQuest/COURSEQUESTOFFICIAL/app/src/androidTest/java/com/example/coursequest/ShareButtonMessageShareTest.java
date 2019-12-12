/*
Scenario:
- Given a user who wants to share a course, that user will be able to share the course
  via SMS message so that they can show a friend.
 */
package com.example.coursequest;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class ShareButtonMessageShareTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shareButtonMessageShareTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.searchButton2),
                        childAtPosition(
                                allOf(withId(R.id.Navigation),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction button = onView(
                allOf(withText("Photography"),
                        childAtPosition(
                                allOf(withId(R.id.popularSearches),
                                        childAtPosition(
                                                withId(R.id.popularSearchesView),
                                                0)),
                                1)));
        button.perform(scrollTo(), click());

        ViewInteraction button2 = onView(
                allOf(withText("Photography Capstone Project\n\nNo description. Click for more information about this course."),
                        childAtPosition(
                                allOf(withId(R.id.resultView),
                                        childAtPosition(
                                                withId(R.id.results),
                                                0)),
                                1)));
        button2.perform(scrollTo(), longClick());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.shareButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.resultView),
                                        2),
                                1)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.messageShare),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.HorizontalScrollView")),
                                        0),
                                0)));
        appCompatButton3.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
