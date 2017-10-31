package com.careem.careemmovies;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.careem.careemmovies.view.activity.MovieListActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Created by alfred afutu on 31/10/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MovieListActivityTest {

    @Rule
    public IntentsTestRule<MovieListActivity> intentsTestRule = new IntentsTestRule<>(MovieListActivity.class);


    private int recyClerViewItemPosition = 8;


    @Test
    public void testRecyclerView () {
        Matcher<View> matcher = ViewMatchers.withId(R.id.movie_list);
        ViewInteraction viewInteraction = onView(matcher)
                .perform(RecyclerViewActions.actionOnItemAtPosition(recyClerViewItemPosition, click()));

        intended(hasComponent("com.careem.careemmovies.view.activity.MovieDetailActivity"));

        pressBack();
    }
}
