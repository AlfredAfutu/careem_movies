package com.careem.careemmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.careem.careemmovies.R;
import com.careem.careemmovies.application.CareemMovies;
import com.careem.careemmovies.listener.MovieDetailRetrieveFailedListener;
import com.careem.careemmovies.listener.MovieService;
import com.careem.careemmovies.model.database.CareemMoviesDatabase;
import com.careem.careemmovies.model.entity.Movie;
import com.careem.careemmovies.model.pojo.Movies;
import com.careem.careemmovies.task.InsertMoviesIntoDBTask;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by alfred afutu on 30/10/17.
 */

public class MoviesViewModel extends AndroidViewModel {

    private int pageNumber;
    private CareemMoviesDatabase careemMoviesDatabase;
    private LiveData<PagedList<Movie>> movieList;

    private MovieDetailRetrieveFailedListener movieDetailRetrieveFailedListener;

    @Inject
    Retrofit retrofit;

    public MoviesViewModel(Application application, MovieDetailRetrieveFailedListener movieDetailRetrieveFailedListener) {
        super(application);
        ((CareemMovies) application).getNetworkComponent().inject(this);
        this.movieDetailRetrieveFailedListener = movieDetailRetrieveFailedListener;
        careemMoviesDatabase = CareemMoviesDatabase.getDatabaseInstance(application);
        movieList = careemMoviesDatabase.movieDao().getAllMovies().create(0, pagedListBuilder.build());
    }

    public MoviesViewModel(Application application) {
        super(application);
        ((CareemMovies) application).getNetworkComponent().inject(this);
        ;
        careemMoviesDatabase = CareemMoviesDatabase.getDatabaseInstance(application);
        movieList = careemMoviesDatabase.movieDao().getAllMovies().create(0, pagedListBuilder.build());
    }

    private PagedList.Config.Builder pagedListBuilder = new PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(20)
            .setPrefetchDistance(15);

    public LiveData<PagedList<Movie>> getMovieList() {
        return movieList;
    }

    public void fetchMoviesFromServer() {
        MovieService movieService = retrofit.create(MovieService.class);
        Call<Movies> movies = movieService.getAllMovies(getApplication().getString(R.string.server_api_key), String.valueOf(pageNumber));
        movies.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        new InsertMoviesIntoDBTask(careemMoviesDatabase).execute(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable throwable) {
                movieDetailRetrieveFailedListener.onFailedRetrieval();
            }
        });
    }

    public LiveData<PagedList<Movie>> getMovieFilteredByDateList(String queryFilter) {
        return careemMoviesDatabase.movieDao().getMoviesByDate(queryFilter).create(0, pagedListBuilder.build());
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
