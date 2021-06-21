package com.example.trabajosexpres

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.trabajosexpres", appContext.packageName)
    }

    @Test
    fun LogInCorrect() {
        ActivityScenario.launch(Login:: class.java)
        onView(withId(R.id.TextFieldUserName)).perform(typeText("MiroStar"))
                .perform(closeSoftKeyboard())
        Thread.sleep(250)
        onView(withId(R.id.TextFieldPassword)).perform(ViewActions.typeText("Mmmol180515"))
                .perform(closeSoftKeyboard())
        Thread.sleep(250)
        onView(withId(R.id.ButtonLogIn)).perform(ViewActions.click())
        Thread.sleep(300)
        //onView(withId(R.id.textView2)).check(matches(withText("Success")))
    }
}