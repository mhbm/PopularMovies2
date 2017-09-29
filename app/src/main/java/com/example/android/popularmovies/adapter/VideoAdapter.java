package com.example.android.popularmovies.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieModel;

import java.util.ArrayList;

/**
 * Created by Mateus Macedo on 26/09/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.NumberViewHolder> {
    private static final String TAG = VideoAdapter.class.getSimpleName();
    private MovieModel mMovie;
    private int mNumberItems;
    private ArrayList<String> frameVideo;
    private Context mContext;

    public VideoAdapter(MovieModel movie, Context context) {
        mNumberItems = movie.getKeysVideos().size();
        mMovie = movie;
        frameVideo = new ArrayList<>();
        mContext = context;
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

        ImageButton displayYoutubeVideo;
        TextView displayYoutubeName;

        public NumberViewHolder(View itemView) {
            super(itemView);
            displayYoutubeVideo = (ImageButton) itemView.findViewById(R.id.ib_movie_trailer);
            displayYoutubeName = (TextView) itemView.findViewById(R.id.tv_trailer);
        }

        void bind(final int listIndex) {

//            String trailerYoutube = "https://www.youtube.com/embed/" + mMovie.getKeysVideos().get(listIndex);
//            String frameVideo = "<html><iframe width=\"match_parent\" height=\"match_parent\" src=\"" + trailerYoutube + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
//            WebSettings webSettings = displayYoutubeVideo.getSettings();
//            webSettings.setJavaScriptEnabled(true);
//            displayYoutubeVideo.loadData(frameVideo, "text/html", "utf-8");
//            displayYoutubeVideo.setVisibility(View.VISIBLE);

            displayYoutubeName.setText("Trailer " + (listIndex + 1));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mMovie.getKeysVideos().get(listIndex)));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + mMovie.getKeysVideos().get(listIndex)));


                    try {

                        mContext.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        mContext.startActivity(webIntent);
                    }
                }

            });

        }
    }
}
