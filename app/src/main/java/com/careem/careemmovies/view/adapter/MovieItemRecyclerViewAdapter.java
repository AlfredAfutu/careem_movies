package com.careem.careemmovies.view.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.careem.careemmovies.R;
import com.careem.careemmovies.model.entity.Movie;
import com.careem.careemmovies.view.activity.MovieDetailActivity;
import com.careem.careemmovies.view.activity.MovieListActivity;
import com.careem.careemmovies.view.fragment.MovieDetailFragment;
import com.careem.careemmovies.view.viewholder.ViewHolder;


/**
 * Created by alfred afutu on 29/10/17.
 */

public class MovieItemRecyclerViewAdapter extends PagedListAdapter<Movie, ViewHolder>{

    private MovieListActivity mParentActivity;
    private boolean mTwoPane;

    public MovieItemRecyclerViewAdapter(boolean mTwoPane, MovieListActivity parentActivity) {
        super(MOVIE_DIFF_CALLBACK);
        this.mTwoPane = mTwoPane;
        this.mParentActivity = parentActivity;
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Movie movie = (Movie) view.getTag();
            if (mTwoPane)
                transactMovieDetailFragment(movie, mParentActivity);
            else
                startMovieDetailActivity(view, movie);
        }
    };

    private void transactMovieDetailFragment(Movie movie, MovieListActivity mParentActivity) {
        Bundle arguments = new Bundle();
        arguments.putLong(MovieDetailFragment.ARG_MOVIE_ID, movie.getId());
        arguments.putString(MovieDetailFragment.ARG_MOVIE_TITLE, movie.getTitle());
        arguments.putString(MovieDetailFragment.ARG_MOVIE_POSTER_PATH, movie.getPosterPath());
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(arguments);
        mParentActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_detail_container, fragment)
                .commit();
    }

    private void startMovieDetailActivity(View view, Movie movie) {
        Context context = view.getContext();
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MovieDetailFragment.ARG_MOVIE_ID, movie.getId());
        intent.putExtra(MovieDetailFragment.ARG_MOVIE_TITLE, movie.getTitle());
        intent.putExtra(MovieDetailFragment.ARG_MOVIE_POSTER_PATH, movie.getPosterPath());
        context.startActivity(intent);
    }

    private static final DiffCallback<Movie> MOVIE_DIFF_CALLBACK = new DiffCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            return oldMovie.getId() == newMovie.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            return oldMovie.equals(newMovie);
        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_content, parent, false);
        return new ViewHolder(view, mParentActivity);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Movie movie = getItem(position);
        if (movie != null)
            (holder).bindTo(movie);
        holder.itemView.setOnClickListener(mOnClickListener);
    }
}
