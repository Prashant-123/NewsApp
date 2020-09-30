package com.newsapp.ui.main.headlines

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.newsapp.R
import com.newsapp.ui.main.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class BookmarksFragmentTest {

    @get: Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun backNavigation_onBookmarksFragment_returnToHeadlinesFragment() {
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))

        // Swipe to switch fragment
        onView(withId(R.id.viewPager)).check(matches(isDisplayed())).perform(swipeLeft())

        // Checking from toolbar as Recycler View may be empty
        onView(withId(R.id.toolbarBookmarks)).check(matches(isDisplayed())).perform(pressBack())

        onView(withId(R.id.toolbarHeadlines)).check(matches(isDisplayed()))
    }

    @Test
    fun testBookmarks_show404View_whenZeroBookmarks() {
        onView(withId(R.id.viewPager)).check(matches(isDisplayed())).perform(swipeLeft())

        onView(withId(R.id.toolbarBookmarks)).check(matches(isDisplayed()))

        try {
            // Case: When atleast 1 bookmark exist

            onView(withId(R.id.recyclerViewBookmarks)).perform(actionOnItemAtPosition<HeadlinesAdapter.MyViewHolder>(0, click()))
            onView(withId(R.id.tvDetailsTitle)).check(matches(isDisplayed()))
        } catch (e: Exception) {
            // Case: When Recycler view is empty

            onView(withId(R.id.recyclerViewBookmarks)).check(matches(not(isDisplayed())))
            onView(withId(R.id.noResultLottie)).check(matches(isDisplayed()))
        }
    }
}