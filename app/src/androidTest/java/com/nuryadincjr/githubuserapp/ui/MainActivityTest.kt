package com.nuryadincjr.githubuserapp.ui

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
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
    private val query = "nuryadincj"
    private val userDummy = "nuryadincjr"
    private val position = 0

    private fun typeSearchViewText(text: String?): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return CoreMatchers.allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun getDescription(): String {
                return "Change view text"
            }

            override fun perform(uiController: UiController?, view: View) {
                (view as SearchView).setQuery(text, true)
            }
        }
    }

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun tesShowDetailOfUser() {
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        Thread.sleep(3000)

        onView(withId(R.id.rv_users)).perform(scrollToPosition<ViewHolder>(position))

        onView(withId(R.id.rv_users)).perform(actionOnItemAtPosition<ViewHolder>(position, click()))
    }

    @Test
    fun tesSetFavoriteUser() {
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        Thread.sleep(3000)

        onView(withId(R.id.rv_users)).perform(scrollToPosition<ViewHolder>(position))

        onView(withId(R.id.rv_users)).perform(actionOnItemAtPosition<ViewHolder>(position, click()))

        onView(withId(R.id.floatingActionButton)).check(matches(isDisplayed()))

        onView(withId(R.id.floatingActionButton)).perform(click())
    }

    @Test
    fun tesShareUser() {
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        Thread.sleep(3000)

        onView(withId(R.id.rv_users)).perform(scrollToPosition<ViewHolder>(position))

        onView(withId(R.id.rv_users)).perform(actionOnItemAtPosition<ViewHolder>(position, click()))

        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))

        onView(withId(R.id.btn_share)).perform(click())
    }

    @Test
    fun tesFindUsers() {
        onView(withId(R.id.sv_user)).check(matches(isDisplayed()))

        onView(withId(R.id.sv_user)).perform(click())

        onView(withId(R.id.sv_user)).perform(typeSearchViewText(query))

        onView(withId(R.id.sv_user)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(8000)

        onView(withId(R.id.rv_users)).perform(scrollToPosition<ViewHolder>(position))

        onView(withId(R.id.rv_users)).perform(
            actionOnItemAtPosition<ViewHolder>(
                position,
                click()
            )
        )

        onView(withId(R.id.tv_username)).check(matches(withText(userDummy)))
    }
}