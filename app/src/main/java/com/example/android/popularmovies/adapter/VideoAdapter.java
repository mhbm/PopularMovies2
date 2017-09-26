package com.example.android.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieModel;

import java.util.ArrayList;

/**
 * Created by lsitec101.macedo on 26/09/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.NumberViewHolder> {
    private static final String TAG = VideoAdapter.class.getSimpleName();
    private MovieModel mMovie;
    private int mNumberItems;
    private ArrayList<String> frameVideo;

    public VideoAdapter(MovieModel movie) {
        mNumberItems = movie.getKeysVideos().size();
        mMovie = movie;
        frameVideo = new ArrayList<>();
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_video;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {

        WebView displayYoutubeVideo;

        public NumberViewHolder(View itemView) {
            super(itemView);
            displayYoutubeVideo = (WebView) itemView.findViewById(R.id.vv_movie_trailer);
        }

        void bind(int listIndex) {
            String trailerYoutube = "https://www.youtube.com/embed/" + mMovie.getKeysVideos().get(listIndex);
            String frameVideo = "<html><iframe width=\"match_parent\" height=\"match_parent\" src=\"" + trailerYoutube + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
            WebSettings webSettings = displayYoutubeVideo.getSettings();
            webSettings.setJavaScriptEnabled(true);
            displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
            displayYoutubeVideo.setVisibility(View.VISIBLE);
        }
    }
}
