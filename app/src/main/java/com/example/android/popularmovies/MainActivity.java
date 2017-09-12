package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.adapter.MovieAdapter;
import com.example.android.popularmovies.data.MovieModel;
import com.example.android.popularmovies.utilities.NetworkUtils;
import com.example.android.popularmovies.utilities.OpenMovieJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private boolean networkStatus;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    // 1 is Popular and 2 is Rating
    private int option;

    private String[] moveListSaved;

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
            MovieModel[] movieList = new MovieModel[moveListSaved.length];
            for (int i = 0; i < moveListSaved.length; i++) {

                MovieModel aux = new MovieModel();
                String[] testSplit = moveListSaved[i].split(" - ");
                aux.setPoster_path(testSplit[0]);
                aux.setTitle(testSplit[1]);
                aux.setOverview(testSplit[2]);
                aux.setRelease_date(testSplit[3]);
                aux.setVote_average(testSplit[4]);
                movieList[i] = aux;

            }
            mMovieAdapter.setMovieList(movieList);
        } else {
            loadMovies();
        }
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
        intentToStartMovieDetailActivity.putExtra("MovieModel", movie);
        startActivity(intentToStartMovieDetailActivity);
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
            option = 1;
            loadMovies();
            return true;
        }

        if (id == R.id.action_rating) {
            mMovieAdapter.setMovieList(null);
            option = 2;
            loadMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                MovieModel[] movieList = new MovieModel[movieData.length];
                for (int i = 0; i < movieData.length; i++) {

                    MovieModel aux = new MovieModel();
                    String[] testSplit = movieData[i].split(" - ");
                    aux.setPoster_path(testSplit[0]);
                    aux.setTitle(testSplit[1]);
                    aux.setOverview(testSplit[2]);
                    aux.setRelease_date(testSplit[3]);
                    aux.setVote_average(testSplit[4]);
                    movieList[i] = aux;

                }
                mMovieAdapter.setMovieList(movieList);
            } else {
                showErrorMessage();
            }
        }
    }


}
