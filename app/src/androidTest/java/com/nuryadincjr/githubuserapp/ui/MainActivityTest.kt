package com.nuryadincjr.githubuserapp.ui

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.nuryadincjr.githubuserapp.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    private val query = "nuryadin"
    private val userDummy = "nuryadincjr"
    private val position = 0

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }


    private fun typeSearchViewText(text: String?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return CoreMatchers.allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun getDescription(): String {
                return text.toString()
            }

            override fun perform(uiController: UiController?, view: View) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }

    @Test(expected = PerformException::class)
    fun itemWithText_doesNotExist() {
        onView(withId(R.id.sv_user)).check(matches(isDisplayed()))
        onView(withId(R.id.sv_user)).perform(
            typeSearchViewText(query),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )

        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))

        onView(withId(R.id.rv_users)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                hasDescendant(withText(userDummy))
            )
        )

        onView(withId(R.id.rv_users)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position, click()
            )
        )
    }
}