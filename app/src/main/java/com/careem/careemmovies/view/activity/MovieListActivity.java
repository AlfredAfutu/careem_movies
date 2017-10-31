package com.careem.careemmovies.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.careem.careemmovies.listener.InfiniteScrollListener;
import com.careem.careemmovies.listener.MovieDetailRetrieveFailedListener;
import com.careem.careemmovies.model.entity.Movie;
import com.careem.careemmovies.view.adapter.MovieItemRecyclerViewAdapter;
import com.careem.careemmovies.R;
import com.careem.careemmovies.viewmodel.MoviesViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity implements MovieDetailRetrieveFailedListener, SearchView.OnQueryTextListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @BindView(R.id.movie_list)
    View recyclerView;

    @BindView(R.id.loading_pb)
    ProgressBar loadingPb;

    @BindView(R.id.no_internet_connection_layout)
    LinearLayout noInternetConnectionLayout;

    @BindView(R.id.retry_loading_btn)
    Button retryLoadingBtn;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MoviesViewModel moviesViewModel;

    private int defaultPageNumber = 1;

    private Unbinder unbinder;

    private MovieItemRecyclerViewAdapter movieItemRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesViewModel = new MoviesViewModel(getApplication(), this);
        setContentView(R.layout.activity_movie_list);
        unbinder = ButterKnife.bind(this);

        setUpToolBar();

        if (findViewById(R.id.movie_detail_container) != null)
            mTwoPane = true;

        fetchMoviesByPageNumber(moviesViewModel, defaultPageNumber);
        movieItemRecyclerViewAdapter = setMovieListObserver(moviesViewModel);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView, movieItemRecyclerViewAdapter);
    }

    private void fetchMoviesByPageNumber(MoviesViewModel moviesViewModel, int pageNumber) {
        moviesViewModel.setPageNumber(pageNumber);
        moviesViewModel.fetchMoviesFromServer();
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    @NonNull
    private MovieItemRecyclerViewAdapter setMovieListObserver(MoviesViewModel moviesViewModel) {
        final MovieItemRecyclerViewAdapter movieItemRecyclerViewAdapter = new MovieItemRecyclerViewAdapter(mTwoPane, this);
        moviesViewModel.getMovieList().observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(@Nullable PagedList<Movie> movies) {
                if (movies == null)
                    return;
                if (movies.size() > 0) {
                    showRecyclerView();
                    movieItemRecyclerViewAdapter.setList(movies);
                }
            }
        });
        return movieItemRecyclerViewAdapter;
    }

    private void showRecyclerView() {
        loadingPb.setVisibility(View.GONE);
        if (noInternetConnectionLayout.getVisibility() == View.VISIBLE)
            noInternetConnectionLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, MovieItemRecyclerViewAdapter movieItemRecyclerViewAdapter) {
        recyclerView.setAdapter(movieItemRecyclerViewAdapter);
        recyclerView.addOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchMoviesByPageNumber(moviesViewModel, moviesViewModel.getPageNumber() + 1);
                    }
                }, 2000);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onFailedRetrieval() {
        if (loadingPb != null)
            loadingPb.setVisibility(View.GONE);
        if (noInternetConnectionLayout != null && noInternetConnectionLayout.getVisibility() == View.GONE &&
                recyclerView.getVisibility() == View.GONE)
            noInternetConnectionLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.retry_loading_btn)
    public void retryLoadingFromApi() {
        fetchMoviesByPageNumber(moviesViewModel, defaultPageNumber);
        recyclerView.setVisibility(View.GONE);
        noInternetConnectionLayout.setVisibility(View.GONE);
        loadingPb.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_list_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.filter_by_date_menu);
        setUpFilterSearchView(searchItem);
        return true;
    }

    private void setUpFilterSearchView(MenuItem searchItem) {
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.filter_by_date_hint));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        moviesViewModel.getMovieFilteredByDateList(newText).observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(@Nullable PagedList<Movie> movies) {
                movieItemRecyclerViewAdapter.setList(movies);
                movieItemRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        return true;
    }

}
