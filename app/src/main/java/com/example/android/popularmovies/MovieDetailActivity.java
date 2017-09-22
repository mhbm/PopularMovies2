package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.adapter.ReviewAdapter;
import com.example.android.popularmovies.data.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mateus Macedo on 01/08/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MovieDetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<String>> {


    /* A constant to save and restore the URL that is being displayed */
    private static final String SEARCH_MOVIE_URL_EXTRA = "MovieModel";

    private static final int MOVIE_SEARCH_LOADER = 22;

    private TextView mTitleDisplay;
    private TextView mOverviewDisplay;
    private ImageView mImageDisplay;
    private TextView mRatingDisplay;
    private TextView mReleaseDate;
    private TextView mRuntimeDisplay;
    private WebView displayYoutubeVideo;
    private WebView displayYoutubeVideo2;
    private WebView displayYoutubeVideo3;
    private MovieModel movie;

    private Context mContext;

    private ReviewAdapter mAdapter;
    private RecyclerView mReviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mTitleDisplay = (TextView) findViewById(R.id.tv_movie_title);
        mOverviewDisplay = (TextView) findViewById(R.id.tv_movie_overview);
        mImageDisplay = (ImageView) findViewById(R.id.iv_movie_image);
        mRatingDisplay = (TextView) findViewById(R.id.tv_movie_vote_average);
        mReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        mRuntimeDisplay = (TextView) findViewById(R.id.tv_movie_runtime);
        displayYoutubeVideo = (WebView) findViewById(R.id.vv_movie_trailer1);
        displayYoutubeVideo2 = (WebView) findViewById(R.id.vv_movie_trailer2);
        displayYoutubeVideo3 = (WebView) findViewById(R.id.vv_movie_trailer3);

        displayYoutubeVideo.setVisibility(View.INVISIBLE);
        displayYoutubeVideo2.setVisibility(View.INVISIBLE);
        displayYoutubeVideo3.setVisibility(View.INVISIBLE);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            movie = (MovieModel) intentThatStartedThisActivity.getSerializableExtra("MovieModel");
            mContext = this;
            mReviewList = (RecyclerView) findViewById(R.id.rv_movies_review);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mReviewList.setLayoutManager(layoutManager);
            mReviewList.setHasFixedSize(true);
            System.out.println(movie);
            mAdapter = new ReviewAdapter(movie);
            mReviewList.setAdapter(mAdapter);
        }

        /*
         * Initialize the loader
         */
        getSupportLoaderManager().initLoader(MOVIE_SEARCH_LOADER, null, this);

        Bundle queryBundle = new Bundle();

        queryBundle.putSerializable(SEARCH_MOVIE_URL_EXTRA, movie);

        LoaderManager loaderManager = getSupportLoaderManager();

        Loader<String> movieSearchLoader = loaderManager.getLoader(MOVIE_SEARCH_LOADER);

        if (movieSearchLoader == null) {
            loaderManager.initLoader(MOVIE_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(MOVIE_SEARCH_LOADER, queryBundle, this);
        }

    }

    private void makeDetailMovie() {
        String mMovieTitle = movie.getTitle();
        String mMovieOverview = movie.getOverview();
        String mMovieVoteAverage = movie.getVote_average();
        String[] mMovieDate = movie.getRelease_date().split("-");
        String mMovieRuntime = movie.getRuntime();
        Picasso.with(mContext).load(movie.takeUrlImage()).into(mImageDisplay);
        mTitleDisplay.setText(mMovieTitle);
        mOverviewDisplay.setText(mMovieOverview);

        mRatingDisplay.setText(mMovieVoteAverage + "/10");
        mReleaseDate.setText(mMovieDate[0]);
        mRuntimeDisplay.setText(mMovieRuntime + " min");
    }


    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<ArrayList<String>>(this) {
            @Override
            protected void onStartLoading() {
                if (args == null)
                    return;
                forceLoad();
            }

            @Override
            public ArrayList<String> loadInBackground() {
                if (movie == null) {
                    return null;
                }

                ArrayList<String> frameVideo = new ArrayList<String>();
                for (int i = 0; i < movie.getKeysVideos().size(); i++) {
                    String trailerYoutube = "https://www.youtube.com/embed/" + movie.getKeysVideos().get(i);
                    String characterSplit = "###";
                    frameVideo.add("<html><iframe width=\"match_parent\" height=\"match_parent\" src=\"" + trailerYoutube + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>");
                }
                return frameVideo;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> data) {

        makeDetailMovie();
        if (data != null) {

            for (int i = 0; i < data.size(); i++) {
                switch (i) {
                    case 0:
                        WebSettings webSettings = displayYoutubeVideo.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        displayYoutubeVideo.loadData(data.get(i), "text/html", "utf-8");
                        displayYoutubeVideo.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        WebSettings webSettings2 = displayYoutubeVideo2.getSettings();
                        webSettings2.setJavaScriptEnabled(true);
                        displayYoutubeVideo2.loadData(data.get(i), "text/html", "utf-8");
                        displayYoutubeVideo2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        WebSettings webSettings3 = displayYoutubeVideo3.getSettings();
                        webSettings3.setJavaScriptEnabled(true);
                        displayYoutubeVideo3.loadData(data.get(i), "text/html", "utf-8");
                        displayYoutubeVideo3.setVisibility(View.VISIBLE);
                        break;
                }
            }

        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader) {

    }
}

