package com.serhii.launches

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sehii.launches.R
import com.serhii.launches.ui.RootActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<RootActivity> =
        ActivityScenarioRule(RootActivity::class.java)

    @Test
    fun listExistsWhenLaunchApp() {
        onView(withId(R.id.launchesList)).check(matches(isDisplayed()))
    }

    @Test
    fun navigateToDetailsScreenWhenClickOnItem() {
        onView(withId(R.id.launchesList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.rocketName)).check(matches(isDisplayed()))
        onView(withId(R.id.rocketDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.rocketImage)).check(matches(isDisplayed()))
    }
}
