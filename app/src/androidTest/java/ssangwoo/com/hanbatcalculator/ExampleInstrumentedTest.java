package ssangwoo.com.hanbatcalculator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    public static String TYPE_EXPRESSION;
    public static String EXPECTED_POSTFIX_EXPRESSTION;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setExpression() throws Exception {
        TYPE_EXPRESSION = "(4+8)*(6-5)/((3-2)*(2+2))";
        EXPECTED_POSTFIX_EXPRESSTION = "48+6-5+*3-2+22+*/";
//        TYPE_EXPRESSION = "3+4*5/6";
//        EXPECTED_POSTFIX_EXPRESSTION = "345*6/+";
//
//        TYPE_EXPRESSION = "(300+23)*(43-21)/(84+7)";
//        EXPECTED_POSTFIX_EXPRESSTION = "30023+4321-*847+/"
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("ssangwoo.com.hanbatcalculator", appContext.getPackageName());
    }

    @Test
    public void typeExpression() throws Exception {

        onView(withId(R.id.button_bracket)).perform(click());
        onView(withId(R.id.button_four)).perform(click());
        onView(withId(R.id.button_plus)).perform(click());
        onView(withId(R.id.button_eight)).perform(click());
        onView(withId(R.id.button_bracket)).perform(click());
        onView(withId(R.id.button_mul)).perform(click());
        onView(withId(R.id.button_bracket)).perform(click());
        onView(withId(R.id.button_six)).perform(click());
        onView(withId(R.id.button_minus)).perform(click());
        onView(withId(R.id.button_five)).perform(click());
        onView(withId(R.id.button_bracket)).perform(click());
        onView(withId(R.id.button_div)).perform(click());
        onView(withId(R.id.button_bracket)).perform(click());
        onView(withId(R.id.button_bracket)).perform(click());
        onView(withId(R.id.button_three)).perform(click());
        onView(withId(R.id.button_minus)).perform(click());
        onView(withId(R.id.button_two)).perform(click());
        onView(withId(R.id.button_bracket)).perform(click());
        onView(withId(R.id.button_mul)).perform(click());
        onView(withId(R.id.button_bracket)).perform(click());
        onView(withId(R.id.button_two)).perform(click());
        onView(withId(R.id.button_plus)).perform(click());
        onView(withId(R.id.button_two)).perform(click());
        onView(withId(R.id.button_result)).perform(click());

//        onView(withId(R.id.text_result))
//                .check(matches(
//                        withText(TYPE_EXPRESSION + "=" + EXPECTED_POSTFIX_EXPRESSTION)));

        onView(withId(R.id.text_result))
                .check(matches(
                        withText(TYPE_EXPRESSION + "=" + 3)));
    }
}
