package com.adrian_971029.ichef;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.adrian_971029.ichef.activity.VideoActivity;
import com.adrian_971029.ichef.utils.TestUtilsVideo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class VideoActivityTest {

    private static final String STEPS = "steps";

    @Rule
    public ActivityTestRule<VideoActivity> mActivityTestRule =
            new ActivityTestRule<VideoActivity>(VideoActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = getInstrumentation()
                            .getTargetContext();
                    Intent i = new Intent(targetContext, VideoActivity.class);
                    try {
                        i.putExtra(STEPS, TestUtilsVideo.resultadoRecetas().get(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return i;
                }
            };

    @Test
    public void testVideoLayout_ToolBar() {
        SystemClock.sleep(2000);
        onView(allOf(withId(R.id.tool_bar), withText(R.string.lbl_tool_bar_title)));
    }

    @Test
    public void testVideoLayout_Views() {
        SystemClock.sleep(2000);
        onView(withId(R.id.exo_video)).check(matches(isDisplayed()));
        onView(withId(R.id.lbl_descripcion)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_descriptionsVideo)).check(matches(isDisplayed()));
    }


}
