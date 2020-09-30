package com.newsapp.ui.main.headlines

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.common.truth.Truth.assertThat
import com.newsapp.R
import com.newsapp.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class HeadlinesFragmentTest {

    @get: Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    private val ITEM_TO_CLICK = 5

    @Test
    fun isHeadlinesFragmentShown_onAppStart() {
        onView(withId(R.id.recyclerViewHeadlines)).check(matches(isDisplayed()))
    }

    @Test
    fun onItemClick_openDetailsFragment_isVisible() {

        onView(withId(R.id.recyclerViewHeadlines)).perform(actionOnItemAtPosition<HeadlinesAdapter.MyViewHolder>(ITEM_TO_CLICK, click()))

        onView(withId(R.id.tvDetailsTitle)).check(matches(isDisplayed()))
    }

    // Set animation to 0 in Developers Mode for better results
    @Test
    fun backNavigation_onHeadlinesFragment_ExitApp() {
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))

        // Catch NoActivityResumedException to skip all close error
        try {
            onView(withId(R.id.recyclerViewHeadlines)).check(matches(isDisplayed())).perform(pressBack())
        } catch (e: NoActivityResumedException) {
            assertThat(activityScenarioRule.scenario.state).isEqualTo(Lifecycle.State.DESTROYED)
        }
    }

    @Test
    fun backNavigation_onDetailsFragment_BackToHeadlines() {
        // Check is View Pager Displayed
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))

        // Click on ITEM_TO_CLICK news item
        onView(withId(R.id.recyclerViewHeadlines)).perform(actionOnItemAtPosition<HeadlinesAdapter.MyViewHolder>(ITEM_TO_CLICK, click()))

        onView(withId(R.id.tvDetailsTitle)).check(matches(isDisplayed())).perform(pressBack())

        onView(withId(R.id.recyclerViewHeadlines)).check(matches(isDisplayed()))
    }
}