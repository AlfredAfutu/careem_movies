package com.careem.careemmovies;

import android.support.v4.BuildConfig;

import com.careem.careemmovies.application.CareemMovies;
import com.careem.careemmovies.viewmodel.MoviesViewModel;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Created by alfred afutu on 31/10/17.
 */
@Config(constants = BuildConfig.class, application = CareemMovies.class)
@RunWith(RobolectricTestRunner.class)
public class MoviesViewModelTest {

    @Mock
    private MoviesViewModel moviesViewModel;

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);
        moviesViewModel = new MoviesViewModel(RuntimeEnvironment.application);
    }

    @Test
    public void testGetMoviesList () {
        Assert.assertNotNull(moviesViewModel.getMovieList());
    }
}
