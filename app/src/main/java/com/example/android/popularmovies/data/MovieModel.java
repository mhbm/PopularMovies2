package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Mateus Macedo on 28/07/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MovieModel implements Parcelable {
    private static final String BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String SIZE_IMAGE = "w185/";
    private String id;
    private String runtime;
    private String poster_path;
    private String overview;
    private String release_date;
    private String title;
    private String vote_average;
    private ArrayList<String> keysVideos;
    private ArrayList<ReviewModel> reviewMovie;

    public ArrayList<ReviewModel> getReviewMovie() {
        return reviewMovie;
    }

    public void setReviewMovie(ArrayList<ReviewModel> reviewMovie) {
        this.reviewMovie = reviewMovie;
    }

    public ArrayList<String> getKeysVideos() {
        return keysVideos;
    }

    public void setKeysVideos(ArrayList<String> keysVideos) {
        this.keysVideos = keysVideos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String takeUrlImage() {
        return BASE_URL + SIZE_IMAGE + "/" + this.getPoster_path();
    }

    public static String formatUrlImagetoPicasso(String path) {
        return BASE_URL + SIZE_IMAGE + "/" + path;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.runtime);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeString(this.title);
        dest.writeString(this.vote_average);
        dest.writeStringList(this.keysVideos);
        dest.writeList(this.reviewMovie);
    }

    public MovieModel() {
    }

    protected MovieModel(Parcel in) {
        this.id = in.readString();
        this.runtime = in.readString();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.title = in.readString();
        this.vote_average = in.readString();
        this.keysVideos = in.createStringArrayList();
        this.reviewMovie = new ArrayList<ReviewModel>();
        in.readList(this.reviewMovie, ReviewModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
