package com.careem.careemmovies.view.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.careem.careemmovies.utility.Helper;
import com.careem.careemmovies.R;
import com.careem.careemmovies.listener.MovieDetailRetrieveFailedListener;
import com.careem.careemmovies.model.pojo.Genre;
import com.careem.careemmovies.model.pojo.MovieDetail;
import com.careem.careemmovies.view.activity.MovieDetailActivity;
import com.careem.careemmovies.view.activity.MovieListActivity;
import com.careem.careemmovies.viewmodel.MovieDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment implements MovieDetailRetrieveFailedListener {

    public static final String ARG_MOVIE_ID = "movie_id";
    public static final String ARG_MOVIE_POSTER_PATH = "movie_poster_path";
    public static final String ARG_MOVIE_TITLE = "movie_title";

    private String movieId;

    private MovieDetailViewModel movieDetailViewModel;

    private Unbinder unbinder;

    @BindView(R.id.genres_tv)
    TextView genresTv;
    @BindView(R.id.vote_rb)
    RatingBar voteRb;
    @BindView(R.id.vote_tv)
    TextView voteTv;
    @BindView(R.id.released_tv)
    TextView releasedTv;
    @BindView(R.id.overview_tv)
    TextView overViewTv;
    @BindView(R.id.release_date_tv)
    TextView releaseDateTv;
    @BindView(R.id.loading_pb)
    ProgressBar detailLoadingPb;
    @BindView(R.id.detail_cv)
    CardView detailCv;
    @BindView(R.id.no_internet_connection_layout)
    LinearLayout noInternetConnectionLayout;
    @BindView(R.id.retry_loading_btn)
    Button retryLoadingBtn;

    private String posterPath;
    private ImageView posterIv;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_MOVIE_ID)) {
            movieId = String.valueOf(getArguments().getLong(ARG_MOVIE_ID));
            posterPath = getArguments().getString(ARG_MOVIE_POSTER_PATH);
            String title = getArguments().getString(ARG_MOVIE_TITLE);
            Activity activity = this.getActivity();
            hookDataToActivityViews(title, activity);
        }
    }

    private void hookDataToActivityViews(String title, Activity activity) {
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.movie_detail_collapsing_toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(title);
        }
        posterIv = activity.findViewById(R.id.movie_poster_iv);
        Helper.loadPhotoWithPicasso("w500", posterPath, posterIv, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail_container, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieDetailViewModel = new MovieDetailViewModel(getActivity().getApplication(), this);
        movieDetailViewModel.getMovieDetail(movieId).observe(this, new Observer<MovieDetail>() {
            @Override
            public void onChanged(@Nullable MovieDetail movieDetail) {
                hookMovieDetailsToFragmentViews(movieDetail);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void hookMovieDetailsToFragmentViews(MovieDetail movieDetail) {
        if (movieDetail == null)
            return;
        detailLoadingPb.setVisibility(View.GONE);
        detailCv.setVisibility(View.VISIBLE);
        String voteAvg = movieDetail.getVoteAverage() + "/10";
        voteTv.setText(voteAvg);
        float voteAverageValue = (float) ((movieDetail.getVoteAverage() / 10) * 5);
        voteRb.setRating(voteAverageValue);
        releasedTv.setText(movieDetail.getStatus());
        overViewTv.setText(movieDetail.getOverview());
        releaseDateTv.setText(movieDetail.getReleaseDate());
        StringBuilder genres = new StringBuilder();
        for (Genre genre : movieDetail.getGenres()) {
            String concat = genre.getName() + " ";
            genres.append(concat);
        }
        genresTv.setText(genres);
    }

    @Override
    public void onFailedRetrieval() {
        noInternetConnectionLayout.setVisibility(View.VISIBLE);
        detailLoadingPb.setVisibility(View.GONE);
        detailCv.setVisibility(View.GONE);
    }

    @OnClick(R.id.retry_loading_btn)
    public void retryLoadFromApi() {
        movieDetailViewModel.fetchMovieDetailFromServer(movieId);
        detailCv.setVisibility(View.GONE);
        noInternetConnectionLayout.setVisibility(View.GONE);
        detailLoadingPb.setVisibility(View.VISIBLE);
        Helper.loadPhotoWithPicasso("w500", posterPath, posterIv, getContext());
    }
}
