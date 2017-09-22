package com.example.android.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieModel;

/**
 * Created by mateus.macedo on 22/09/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.NumberViewHolder> {

    private MovieModel mMovie;

    private static final String TAG = ReviewAdapter.class.getSimpleName();

    private int mNumberItems;

    public ReviewAdapter(MovieModel movie) {
        mNumberItems = movie.getReviewMovie().size();
        mMovie = movie;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_review;
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
        
        TextView listItemAuthor;
        TextView listItemContent;


        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemAuthor = (TextView) itemView.findViewById(R.id.tv_authorReview);
            listItemContent = (TextView) itemView.findViewById(R.id.tv_contentReview);
        }

        void bind(int listIndex) {
            listItemAuthor.setText(String.valueOf(mMovie.getReviewMovie().get(listIndex).getAuthor()));
            listItemContent.setText(String.valueOf(mMovie.getReviewMovie().get(listIndex).getContent()));
        }
    }

}


