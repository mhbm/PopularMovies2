package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.adapter.MovieAdapter;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieModel;
import com.example.android.popularmovies.data.ReviewModel;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.example.android.popularmovies.utilities.OpenMovieJsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor> {

    private boolean networkStatus;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    // 1 is Popular and 2 is Rating
    private int option;

    private String[] moveListSaved;

    private static final int MOVIE_LOADER_ID = 0;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        option = 1;

        if (savedInstanceState != null) {
            moveListSaved = savedInstanceState.getStringArray("EXTRA_MOVIES");
            setMoviestoShow(moveListSaved);
        } else {
            loadMovies();
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void loadMovies() {
        showMovieView();
        new FetchMovieTask().execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("EXTRA_MOVIES", moveListSaved);
    }

    @Override
    public void onClick(MovieModel movie) {
        Context context = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToStartMovieDetailActivity = new Intent(context, destinationClass);
        openDetailFilm(movie);
        openVideoFilm(movie);
        openReviewFilm(movie);
        intentToStartMovieDetailActivity.putExtra("MovieModel", movie);
        startActivity(intentToStartMovieDetailActivity);
    }

    private void openDetailFilm(MovieModel movie) {
        URL movieDetailURL = NetworkUtils.buildUrlMovieDetail(Integer.parseInt(movie.getId()));

        System.out.println(movieDetailURL.toString());
        try {
            String jsonMovieResponse = NetworkUtils
                    .getResponseFromHttpUrl(movieDetailURL);

            String runtimeMovie = OpenMovieJsonUtils.getRuntimeMovie(jsonMovieResponse);

            movie.setRuntime(runtimeMovie);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openVideoFilm(MovieModel movie) {
        URL movieDetailURL = NetworkUtils.buildUrlVideoMovie(Integer.parseInt(movie.getId()));

        try {
            String jsonMovieResponse = NetworkUtils
                    .getResponseFromHttpUrl(movieDetailURL);

            ArrayList<String> keysVideosMovie = OpenMovieJsonUtils.getVideoMovie(jsonMovieResponse);

            movie.setKeysVideos(keysVideosMovie);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openReviewFilm(MovieModel movie) {
        URL movieDetailURL = NetworkUtils.buildUrlReviewMovie(Integer.parseInt(movie.getId()));

        try {
            String jsonMovieResponse = NetworkUtils
                    .getResponseFromHttpUrl(movieDetailURL);

            ArrayList<ReviewModel> keysReviewMovie = OpenMovieJsonUtils.getReviewMovie(jsonMovieResponse);

            movie.setReviewMovie(keysReviewMovie);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMovieView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void checkNetworkAvailable() {
        networkStatus = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                networkStatus = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    networkStatus = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            networkStatus = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.movies, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_popular) {
            mMovieAdapter.setMovieList(null);
            mMovieAdapter.setFavorite(false);
            option = 1;
            loadMovies();
            return true;
        } else if (id == R.id.action_rating) {
            mMovieAdapter.setMovieList(null);
            mMovieAdapter.setFavorite(false);
            option = 2;
            loadMovies();
            return true;
        } else if (id == R.id.action_favorite) {
            mMovieAdapter.setMovieList(null);
            mMovieAdapter.setFavorite(true);
            option = 3;
            getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setMoviestoShow(String[] moveListSaved) {
        String charSpecial = "###";
        MovieModel[] movieList = new MovieModel[moveListSaved.length];
        for (int i = 0; i < moveListSaved.length; i++) {
            MovieModel aux = new MovieModel();
            String[] testSplit = moveListSaved[i].split(charSpecial);
            aux.setPoster_path(testSplit[0]);
            aux.setTitle(testSplit[1]);
            aux.setOverview(testSplit[2]);
            aux.setRelease_date(testSplit[3]);
            aux.setVote_average(testSplit[4]);
            aux.setId(testSplit[5]);
            movieList[i] = aux;
        }
        mMovieAdapter.setMovieList(movieList);
    }

    private class FetchMovieTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            URL weatherRequestUrl = NetworkUtils.buildUrl(option);

            checkNetworkAvailable();

            if (!networkStatus) {
                return null;
            }

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonMovieData = OpenMovieJsonUtils.getMovieFromJson(jsonMovieResponse);

                moveListSaved = simpleJsonMovieData;

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (movieData != null) {
                showMovieView();
                setMoviestoShow(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolders
        mMovieAdapter.swapCursor(data);
    }


    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.
     * onLoaderReset removes any references this activity had to the loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieAdapter.swapCursor(null);
    }


}
