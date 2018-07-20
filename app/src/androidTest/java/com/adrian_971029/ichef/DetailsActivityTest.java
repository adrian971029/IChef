package com.adrian_971029.ichef;

import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.adrian_971029.ichef.activity.DetailsActivity;
import com.adrian_971029.ichef.utils.TestUtilsDetails;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {

    private static final String RECETAS = "recetas";

    @Rule
    public ActivityTestRule<DetailsActivity> mActivityTestRule =
            new ActivityTestRule<DetailsActivity>(DetailsActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = getInstrumentation()
                            .getTargetContext();
                    Intent i = new Intent(targetContext, DetailsActivity.class);
                    try {
                        i.putExtra(RECETAS, TestUtilsDetails.resultadoRecetas().get(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return i;
                }
            };

    @Test
    public void testDetaislLayout_ToolBar() {
        onView(allOf(withId(R.id.tool_bar), withText(R.string.lbl_tool_bar_title)));
    }

    @Test
    public void testDetaislLayout_ListIngredient() {
        onView(withId(R.id.tv_ingredients)).check(matches(isDisplayed()));
    }

    @Test
    public void testDetaislLayout_ListSteps() {
        onView(withId(android.R.id.content)).perform(ViewActions.swipeUp());
        onView(withId(R.id.listSteps))
                .check(matches(hasDescendant(withId(R.id.tv_numberSteps)))).check(matches(isDisplayed()));
        onView(withId(R.id.listSteps))
                .check(matches(hasDescendant(withId(R.id.tv_descriptionsSteps)))).check(matches(isDisplayed()));
    }

    @Test
    public void testDetaislLayout_ClickFavoritos() {
        onView(allOf(withId(R.id.btn_favorito))).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void testDetaislLayout_ClickWidget() {
        onView(allOf(withId(R.id.btn_widget))).check(matches(isDisplayed())).perform(click());
    }


}
