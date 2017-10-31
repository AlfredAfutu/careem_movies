package com.careem.careemmovies.listener;

import com.careem.careemmovies.model.pojo.MovieDetail;
import com.careem.careemmovies.model.pojo.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alfred afutu on 29/10/17.
 */

public interface MovieService {

    @GET("movie/now_playing")
    Call<Movies> getAllMovies(@Query("api_key") String apiKey, @Query("page") String page);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovie(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

}


