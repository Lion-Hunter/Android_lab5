package com.example.lab5

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import junit.framework.Assert.assertTrue
import java.io.IOException

import java.io.InputStreamReader


import org.junit.Assert.*

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class FirstTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(First::class.java)

    private fun isFirst() {
        onView(withId(R.id.to_2_button)).check(matches(isDisplayed()))
    }

    private fun isSecond() {
        onView(withId(R.id.to_1_button)).check(matches(isDisplayed()))
        onView(withId(R.id.to_3_button)).check(matches(isDisplayed()))
    }

    private fun isThird() {
        onView(withId(R.id.to_1_from_3)).check(matches(isDisplayed()))
        onView(withId(R.id.to_2_from_3)).check(matches(isDisplayed()))
    }

    private fun isAbout() {
        onView(withId(R.id.about)).check(matches(isDisplayed()))
    }

    @Test
    fun firstToSecond() {
        isFirst()
        onView(withId(R.id.to_2_button)).perform(click())
        isSecond()
    }

    @Test
    fun secondToFirst() {
        firstToSecond()
        onView(withId(R.id.to_1_button)).perform(click())
        isFirst()
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

    private fun toAbout() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.to_about)).perform(click())
        isAbout()
    }

    @Test
    fun toAboutTransition() {
        for (i in 1..3) {
            when (i) {
                1 -> isFirst()
                2 -> firstToSecond()
                3 -> secondToThird()
            }

            toAbout()

            when (i) {
                1 -> {
                    pressBack()
                    isFirst()
                }
                2 -> {
                    pressBack()
                    isSecond()
                    pressBack()
                }
                3 -> {
                    pressBack()
                    isThird()
                }
            }
        }
    }

    @Test
    fun backFromSecond() {
        firstToSecond()
        pressBack()
        isFirst()

        thirdToSecond()
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

    @Test
    fun backStackValueTest() {
        var exception = false

        thirdToFirst()  //  1 -> 2 -> 3 -> 2 -> 1
        secondToFirst() //  1 -> 2 -> 1
        secondToThird() //  1 -> 2 -> 3
        toAbout()
        pressBack()
        pressBack()
        pressBack()

        try {
            pressBack()
        } catch (e: NoActivityResumedException) {
            exception = true
        }

        assertTrue(exception)
    }
}