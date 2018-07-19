package com.adrian_971029.ichef;

import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;

import android.support.test.rule.ActivityTestRule;
import com.adrian_971029.ichef.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testPrincipalLayot_ToolBar() {
        SystemClock.sleep(2000);
        onView(allOf(withId(R.id.tool_bar), withText(R.string.lbl_tool_bar_title)));
    }

    @Test
    public void testPrincipalLayot_DadosRecetas() {
        SystemClock.sleep(2000);
        onView(withId(R.id.grid_view))
                .check(matches(hasDescendant(withId(R.id.image_receta)))).check(matches(isDisplayed()));
        onView(withId(R.id.grid_view))
                .check(matches(hasDescendant(withId(R.id.tituloReceta)))).check(matches(isDisplayed()));

    }

    @Test
    public void testPrincipalLayot_ClickGridView() {
        SystemClock.sleep(2000);
        onData(anything()).inAdapterView(withId(R.id.grid_view)).atPosition(1).perform(click());
    }

}
