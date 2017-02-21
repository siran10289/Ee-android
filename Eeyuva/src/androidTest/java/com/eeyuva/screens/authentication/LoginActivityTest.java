package com.eeyuva.screens.authentication;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.eeyuva.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by hari on 6/7/16.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class, true /* Initial touch mode  */,
                    false /* Lazily launch activity */);

    @Test
    public void activeTaskDetails_DisplayedInUi() throws Exception {

        onView(ViewMatchers.withId(R.id.mEdtUsername)).check(matches(withText("")));
        onView(ViewMatchers.withId(R.id.mEdtPassword)).check(matches(withText("")));

    }
}
