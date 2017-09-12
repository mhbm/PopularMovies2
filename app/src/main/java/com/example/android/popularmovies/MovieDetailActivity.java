package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.MovieModel;
import com.squareup.picasso.Picasso;

/**
 * Created by Mateus Macedo on 01/08/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MovieDetailActivity  extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView mTitleDisplay = (TextView) findViewById(R.id.tv_movie_title);
        TextView mOverviewDisplay = (TextView) findViewById(R.id.tv_movie_overview);
        ImageView mImageDisplay = (ImageView) findViewById(R.id.iv_movie_image);
        TextView mRatingDisplay = (TextView) findViewById(R.id.tv_movie_vote_average);
        TextView mReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {

                MovieModel movie = (MovieModel) intentThatStartedThisActivity.getSerializableExtra("MovieModel");
            String mMovieTitle = movie.getTitle();
            String mMovieOverview = movie.getOverview();
            String mMovieVoteAverage = movie.getVote_average();
            String mMovieDate = movie.getRelease_date();

                mTitleDisplay.setText(mMovieTitle);
                mOverviewDisplay.setText(mMovieOverview);
                Picasso.with(this).load(movie.takeUrlImage()).into(mImageDisplay);
                mRatingDisplay.setText(mMovieVoteAverage);
                mReleaseDate.setText(mMovieDate);



        }


    }


}

