package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.data.MovieModel;
import com.example.android.popularmovies.data.ReviewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        String charSpecial = "###";

        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject movie = movieArray.getJSONObject(i);

            MovieModel movieGet = new MovieModel();
            movieGet.setPoster_path(movie.getString("poster_path"));
            movieGet.setTitle(movie.getString("title"));
            movieGet.setOverview(movie.getString("overview"));
            movieGet.setVote_average(movie.getString("vote_average"));
            movieGet.setRelease_date(movie.getString("release_date"));
            movieGet.setId(movie.getString("id"));

            parsedMovieData[i] = movieGet.getPoster_path() + charSpecial + movieGet.getTitle() + charSpecial + movieGet.getOverview() + charSpecial + movieGet.getRelease_date() + charSpecial + movieGet.getVote_average() + charSpecial + movieGet.getId();

        }
        return parsedMovieData;
    }


    public static String getRuntimeMovie(String movieJsonStr) throws JSONException {
        JSONObject movieJson = new JSONObject(movieJsonStr);
        return movieJson.getString("runtime");
    }

    public static ArrayList<String> getVideoMovie(String movieJsonStr) throws JSONException {
        ArrayList<String> parsedMovieVideo = new ArrayList<>();

        JSONObject movieJson = new JSONObject(movieJsonStr);

        JSONArray movieArray = movieJson.getJSONArray("results");

        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject movie = movieArray.getJSONObject(i);

            parsedMovieVideo.add(movie.getString("key"));

        }
        return parsedMovieVideo;
    }

    public static ArrayList<ReviewModel> getReviewMovie(String movieJsonStr) throws JSONException {
        ArrayList<ReviewModel> parsedMovieReview = new ArrayList<>();
        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieArray = movieJson.getJSONArray("results");

        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i);
            ReviewModel review = new ReviewModel();
            review.setAuthor(movie.getString("author"));
            review.setContent(movie.getString("content"));
            parsedMovieReview.add(review);
        }
        return parsedMovieReview;
    }

}