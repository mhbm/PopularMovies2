package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.data.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mateus Macedo on 31/07/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public final class OpenMovieJsonUtils {

    public static String[] getMovieFromJson(String movieJsonStr) throws JSONException {
        String[] parsedMovieData;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        JSONArray movieArray = movieJson.getJSONArray("results");

        parsedMovieData = new String[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject movie = movieArray.getJSONObject(i);

            MovieModel movieGet = new MovieModel();
            movieGet.setPoster_path(movie.getString("poster_path"));
            movieGet.setTitle(movie.getString("title"));
            movieGet.setOverview(movie.getString("overview"));
            movieGet.setRelease_date(movie.getString("release_date"));
            movieGet.setVote_average(movie.getString("vote_average"));

            parsedMovieData[i] = movieGet.getPoster_path() + " - " + movieGet.getTitle() + " - " + movieGet.getOverview() + " - " + movieGet.getRelease_date() + " - " + movieGet.getVote_average();

        }
        return parsedMovieData;
    }
}