package com.careem.careemmovies.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.careem.careemmovies.utility.Helper;
import com.careem.careemmovies.R;
import com.careem.careemmovies.model.entity.Movie;
import com.careem.careemmovies.view.activity.MovieListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alfred afutu on 30/10/17.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.release_date_tv)
    TextView releaseDateTv;
    @BindView(R.id.backdrop_iv)
    ImageView backDropIv;

    private MovieListActivity parentActivity;

    private View view;

    public ViewHolder(View view, MovieListActivity parentActivity) {
        super(view);
        this.view = view;
        this.parentActivity = parentActivity;
        ButterKnife.bind(this, view);
    }

    public void bindTo (Movie movie) {
        titleTv.setText(movie.getTitle());
        releaseDateTv.setText(movie.getReleaseDate());
        Helper.loadPhotoWithPicasso("w185", movie.getBackdropPath(), backDropIv, parentActivity);
        view.setTag(movie);
    }

}