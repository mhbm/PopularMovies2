package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.MovieModel;
import com.squareup.picasso.Picasso;

/**
 * Created by Mateus Macedo on 01/08/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView mTitleDisplay = (TextView) findViewById(R.id.tv_movie_title);
        TextView mOverviewDisplay = (TextView) findViewById(R.id.tv_movie_overview);
        ImageView mImageDisplay = (ImageView) findViewById(R.id.iv_movie_image);
        TextView mRatingDisplay = (TextView) findViewById(R.id.tv_movie_vote_average);
        TextView mReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        TextView mRuntimeDisplay = (TextView) findViewById(R.id.tv_movie_runtime);
        WebView displayYoutubeVideo = (WebView) findViewById(R.id.vv_movie_trailer1);
        WebView displayYoutubeVideo2 = (WebView) findViewById(R.id.vv_movie_trailer2);
        WebView displayYoutubeVideo3 = (WebView) findViewById(R.id.vv_movie_trailer3);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {

            MovieModel movie = (MovieModel) intentThatStartedThisActivity.getSerializableExtra("MovieModel");
            String mMovieTitle = movie.getTitle();
            String mMovieOverview = movie.getOverview();
            String mMovieVoteAverage = movie.getVote_average();
            String[] mMovieDate = movie.getRelease_date().split("-");
            String mMovieRuntime = movie.getRuntime();

            mTitleDisplay.setText(mMovieTitle);
            mOverviewDisplay.setText(mMovieOverview);
            Picasso.with(this).load(movie.takeUrlImage()).into(mImageDisplay);
            mRatingDisplay.setText(mMovieVoteAverage + "/10");
            mReleaseDate.setText(mMovieDate[0]);
            mRuntimeDisplay.setText(mMovieRuntime + " min");

            for (int i = 0; i < movie.getKeysVideos().size(); i++) {
                String trailerYoutube = "https://www.youtube.com/embed/" + movie.getKeysVideos().get(i);
                String frameVideo = "<html><iframe width=\"match_parent\" height=\"match_parent\" src=\"" + trailerYoutube +"\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

                switch (i)  {
                    case 0:
                        WebSettings webSettings = displayYoutubeVideo . getSettings ();
                        webSettings.setJavaScriptEnabled(true);
                        displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
                        break;
                    case 1:
                        WebSettings webSettings2 = displayYoutubeVideo2 . getSettings ();
                        webSettings2.setJavaScriptEnabled(true);
                        displayYoutubeVideo2.loadData(frameVideo, "text/html", "utf-8");
                        break;
                    case 2:
                        WebSettings webSettings3 = displayYoutubeVideo3 . getSettings ();
                        webSettings3.setJavaScriptEnabled(true);
                        displayYoutubeVideo3.loadData(frameVideo, "text/html", "utf-8");
                        break;
                }
            }

            if (movie.getKeysVideos().size() < 3) {
                displayYoutubeVideo3.setVisibility(View.INVISIBLE);
            }

        }

    }


}

