package com.example.lab5

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Rule
import org.junit.Test


class FirstTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(First::class.java)

    @Test
    fun isFirst() {
        onView(withId(R.id.to_2_button)).check(matches(isDisplayed()))
    }

    @Test
    fun isSecond() {
        onView(withId(R.id.to_1_button)).check(matches(isDisplayed()))
        onView(withId(R.id.to_3_button)).check(matches(isDisplayed()))
    }

    @Test
    fun isThird() {
        onView(withId(R.id.to_1_from_3)).check(matches(isDisplayed()))
        onView(withId(R.id.to_2_from_3)).check(matches(isDisplayed()))
    }

    @Test
    fun isAbout() {
        onView(withId(R.id.about)).check(matches(isDisplayed()))
    }

    @Test
    fun firstToSecond() {
        isFirst()
        onView(withId(R.id.to_2_button)).perform(click())
        isSecond()
    }

    @Test
    fun secondToThird() {
        firstToSecond()
        onView(withId(R.id.to_3_button)).perform(click())
        isThird()
    }

    @Test
    fun thirdToSecond() {
        secondToThird()
        onView(withId(R.id.to_2_from_3)).perform(click())
        isSecond()
    }

    @Test
    fun thirdToFirst() {
        secondToThird()
        onView(withId(R.id.to_1_from_3)).perform(click())
        isFirst()
    }

    @Test
    fun toAbout() {
        when ((1..3).shuffled().first()) {
            1 -> isFirst()
            2 -> firstToSecond()
            3 -> secondToThird()
        }

        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.to_about)).perform(click())
        isAbout()
    }

    @Test
    fun backFromSecond() {
        firstToSecond()
        pressBack()
        isFirst()
    }

    @Test
    fun backFromThird() {
        secondToThird()
        pressBack()
        isSecond()
        pressBack()
        isFirst()
    }
}