package com.careem.careemmovies;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.careem.careemmovies.model.dao.MovieDao;
import com.careem.careemmovies.model.database.CareemMoviesDatabase;
import com.careem.careemmovies.model.entity.Movie;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by alfred afutu on 31/10/17.
 */
@RunWith(AndroidJUnit4.class)
public class MovieEntityTest {

    CareemMoviesDatabase careemMoviesDatabase;
    MovieDao movieDao;

    @Before
    public void createDB () {
        Context context = InstrumentationRegistry.getTargetContext();
        careemMoviesDatabase = Room.inMemoryDatabaseBuilder(context, CareemMoviesDatabase.class).build();
        movieDao = careemMoviesDatabase.movieDao();
    }

    @Test
    public void testMovieDao() {
        movieDao.insert(brazil());
        movieDao.insert(bladeRunner());
        movieDao.insert(theTinDrum());

        Assert.assertEquals(3, movieDao.getAllMoviesTest().size());

        Assert.assertEquals(1, movieDao.getMoviesByDateTest("1979").size());
    }

    private Movie brazil () {
        Movie movie = new Movie();
        movie.setId(20891);
        movie.setTitle("Brazil");
        movie.setReleaseDate("2016-11-10");
        movie.setAdult(false);
        return movie;
    }

    private Movie bladeRunner () {
        Movie movie = new Movie();
        movie.setId(20198);
        movie.setTitle("Blade Runner");
        movie.setReleaseDate("2016-09-10");
        movie.setAdult(false);
        return movie;
    }

    private Movie theTinDrum () {
        Movie movie = new Movie();
        movie.setId(1097);
        movie.setTitle("The Tin Drum");
        movie.setReleaseDate("1979-05-02");
        movie.setAdult(true);
        return movie;
    }
}
