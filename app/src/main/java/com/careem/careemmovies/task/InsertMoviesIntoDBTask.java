package com.careem.careemmovies.task;

import android.os.AsyncTask;

import com.careem.careemmovies.model.dao.MovieDao;
import com.careem.careemmovies.model.database.CareemMoviesDatabase;
import com.careem.careemmovies.model.entity.Movie;
import com.careem.careemmovies.model.pojo.Result;

import java.util.List;


/**
 * Created by alfred afutu on 30/10/17.
 */

public class InsertMoviesIntoDBTask extends AsyncTask<List<Result>, Void, Void> {

    private CareemMoviesDatabase careemMoviesDatabase;
    private MovieDao movieDao;

    public InsertMoviesIntoDBTask (CareemMoviesDatabase moviesDatabase) {
        super();
        careemMoviesDatabase = moviesDatabase;
        movieDao = careemMoviesDatabase.movieDao();
    }

    @Override
    protected Void doInBackground(List<Result>[] results) {

        for (Result result : results[0]) {
            Movie movie = createDBMovie(result);
            if (movieDao.findMovieById(movie.getId()) != null)
                movieDao.update(movie);
            else
                movieDao.insert(movie);
        }
        return null;
    }

    private Movie createDBMovie (Result result) {
        Movie movie = new Movie();
        movie.setId(result.getId());
        movie.setAdult(result.getAdult());
        movie.setBackdropPath(result.getBackdropPath());
        movie.setOriginalLanguage(result.getOriginalLanguage());
        movie.setOriginalTitle(result.getOriginalTitle());
        movie.setOverview(result.getOverview());
        movie.setPopularity(result.getPopularity());
        movie.setPosterPath(result.getPosterPath());
        movie.setReleaseDate(result.getReleaseDate());
        movie.setVideo(result.getVideo());
        movie.setVoteAverage(result.getVoteAverage());
        movie.setVoteCount(result.getVoteCount());
        movie.setTitle(result.getTitle());

        return movie;
    }
}
