package com.careem.careemmovies.model.dao;

import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.careem.careemmovies.model.entity.Movie;

import java.util.List;

/**
 * Created by alfred afutu on 29/10/17.
 */
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LivePagedListProvider<Integer, Movie> getAllMovies ();

    @Query("SELECT * FROM movie")
    List<Movie> getAllMoviesTest ();

    @Query("SELECT * FROM movie WHERE release_date LIKE '%' || :date || '%'")
    LivePagedListProvider<Integer, Movie> getMoviesByDate (String date);

    @Query("SELECT * FROM movie WHERE release_date LIKE '%' || :date || '%'")
    List<Movie> getMoviesByDateTest (String date);

    @Insert
    void insert(Movie movie);

    @Update
    void update(Movie movie);

    @Query("SELECT * FROM movie WHERE id = :movieId")
    Movie findMovieById(long movieId);
}
