package com.careem.careemmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.careem.careemmovies.R;
import com.careem.careemmovies.application.CareemMovies;
import com.careem.careemmovies.listener.MovieDetailRetrieveFailedListener;
import com.careem.careemmovies.listener.MovieService;
import com.careem.careemmovies.model.pojo.MovieDetail;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by alfred afutu on 30/10/17.
 */

public class MovieDetailViewModel extends AndroidViewModel {


    private MutableLiveData<MovieDetail> movieDetail;

    private MovieDetailRetrieveFailedListener movieDetailRetrieveFailedListener;

    @Inject
    Retrofit retrofit;

    public MovieDetailViewModel(Application application, MovieDetailRetrieveFailedListener listener) {
        super(application);
        ((CareemMovies) getApplication()).getNetworkComponent().inject(this);
        this.movieDetailRetrieveFailedListener = listener;
    }

    public LiveData<MovieDetail> getMovieDetail(String movieId) {
        if (movieDetail == null) {
            movieDetail = new MutableLiveData<>();
            fetchMovieDetailFromServer(movieId);
        }
        return movieDetail;
    }

    public void fetchMovieDetailFromServer(String movieId) {
        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieDetail> movies = movieService.getMovie(movieId, getApplication().getString(R.string.server_api_key));
        movies.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetail> call, @NonNull Response<MovieDetail> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        movieDetail.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetail> call, @NonNull Throwable throwable) {
                movieDetailRetrieveFailedListener.onFailedRetrieval();
            }
        });
    }

}
