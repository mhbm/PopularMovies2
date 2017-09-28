package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.adapter.ReviewAdapter;
import com.example.android.popularmovies.adapter.VideoAdapter;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieModel;
import com.squareup.picasso.Picasso;

/**
 * Created by Mateus Macedo on 01/08/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MovieDetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<MovieModel> {


    /* A constant to save and restore the URL that is being displayed */
    private static final String SEARCH_MOVIE_URL_EXTRA = "MovieModel";

    private static final int MOVIE_SEARCH_LOADER = 22;

    private TextView mTitleDisplay;
    private TextView mOverviewDisplay;
    private ImageView mImageDisplay;
    private TextView mRatingDisplay;
    private TextView mReleaseDate;
    private TextView mRuntimeDisplay;
    private MovieModel movie;

    private Context mContext;

    private ReviewAdapter mAdapter;
    private VideoAdapter mVideoAdapter;
    private RecyclerView mReviewList;
    private RecyclerView mReviewVideo;
    private ImageButton mFavoriteButton;

    boolean isEnable;

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

        mFavoriteButton = (ImageButton) findViewById(R.id.favorite);
        Intent intentThatStartedThisActivity = getIntent();


        if (intentThatStartedThisActivity != null) {
            movie = (MovieModel) intentThatStartedThisActivity.getSerializableExtra("MovieModel");
            verifyFilmInContentProvider(movie);
            mContext = this;
            mReviewList = (RecyclerView) findViewById(R.id.rv_movies_review);
            mReviewVideo = (RecyclerView) findViewById(R.id.rv_movie_video);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mReviewList.setLayoutManager(layoutManager);
            mReviewList.setHasFixedSize(true);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
            mReviewVideo.setLayoutManager(layoutManager2);
            mReviewVideo.setHasFixedSize(true);
            System.out.println(movie);
            mAdapter = new ReviewAdapter(movie);
            mVideoAdapter = new VideoAdapter(movie);
            mReviewList.setAdapter(mAdapter);
            mReviewVideo.setAdapter(mVideoAdapter);

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
    public Loader<MovieModel> onCreateLoader(int id, final Bundle args) {

        return new AsyncTaskLoader<MovieModel>(this) {
            @Override
            protected void onStartLoading() {
                if (args == null)
                    return;
                forceLoad();
            }

            @Override
            public MovieModel loadInBackground() {
                if (movie == null) {
                    return null;
                }
                return movie;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<MovieModel> loader, MovieModel data) {
        makeDetailMovie();
    }

    @Override
    public void onLoaderReset(Loader<MovieModel> loader) {
    }

    public void onClickFavoriteButton(View view) {
        // Not yet implemented
        // Check if EditText is empty, if not retrieve input and store it in a ContentValues object
        // If the EditText input is empty -> don't create an entry

        // Insert new task data via a ContentResolver
        // Create new empty ContentValues object
        ContentValues contentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(MovieContract.MovieEntry.COLUMN_NAME, movie.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_IMAGE, movie.getPoster_path());
        contentValues.put(MovieContract.MovieEntry.COLUMN_DATE, movie.getRelease_date());
        contentValues.put(MovieContract.MovieEntry.COLUMN_RUNTIME, movie.getRuntime());
        contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVote_average());
        contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_IDMOVIE, movie.getId());

        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

        // Display the URI that's returned with a Toast
        // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
        if(uri != null) {
            Toast.makeText(getBaseContext(), "Add Favorite", Toast.LENGTH_LONG).show();
        }

        if (isEnable){
            mFavoriteButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
        }else{
            mFavoriteButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
        }
        isEnable = !isEnable;

        // Finish activity (this returns back to MainActivity)
//        finish();

    }

    public void verifyFilmInContentProvider(MovieModel movie) {
        String[] args = {movie.getId()};
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, MovieContract.MovieEntry.COLUMN_IDMOVIE + "=?", args, MovieContract.MovieEntry.COLUMN_IDMOVIE);

        if (cursor.moveToFirst() == false) {
            mFavoriteButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
            isEnable = false;
        } else {
            mFavoriteButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
            isEnable = true;
        }

    }
}

