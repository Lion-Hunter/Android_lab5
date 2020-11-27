package com.example.lab5

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test


class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun firstTest() {
        onView(withId(R.id.edit_text)).perform(typeText("Immortal string"))
        onView(withId(R.id.button)).perform(click())

        onView(withId(R.id.edit_text)).check(matches(withText("Immortal string")))
        onView(withId(R.id.button)).check(matches(withText("Button pressed")))

        activityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.button)).check(matches(withText("Button")))
        onView(withId(R.id.edit_text)).check(matches(withText("Immortal string")))
    }
}