package com.careem.careemmovies.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.careem.careemmovies.view.fragment.MovieDetailFragment;
import com.careem.careemmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * An activity representing a single movie details.
 */
public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.movie_detail_toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null)
            createAndAddDetailFragment();
    }

    private void createAndAddDetailFragment() {
        Bundle arguments = new Bundle();
        arguments.putLong(MovieDetailFragment.ARG_MOVIE_ID,
                getIntent().getLongExtra(MovieDetailFragment.ARG_MOVIE_ID, 0));
        arguments.putString(MovieDetailFragment.ARG_MOVIE_POSTER_PATH,
                getIntent().getStringExtra(MovieDetailFragment.ARG_MOVIE_POSTER_PATH));
        arguments.putString(MovieDetailFragment.ARG_MOVIE_TITLE,
                getIntent().getStringExtra(MovieDetailFragment.ARG_MOVIE_TITLE));
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MovieListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
