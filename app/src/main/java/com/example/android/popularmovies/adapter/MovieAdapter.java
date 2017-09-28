package com.example.android.popularmovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieModel;
import com.squareup.picasso.Picasso;

/**
 * Created by Mateus Macedo on 31/07/17.
 */


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private final MovieAdapterOnClickHandler mClickHandler;
    private MovieModel[] mMovieList;
    private Context mContext;
    private Cursor mCursor;
    private boolean mFavorite;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {

        if (isFavorite()) {
            int idIndex = mCursor.getColumnIndex(MovieContract.MovieEntry._ID);
            int imageIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE);
            mCursor.moveToPosition(position);
            final int id = mCursor.getInt(idIndex);
            String image = mCursor.getString(imageIndex);
            holder.itemView.setTag(id);
            Picasso.with(mContext).load(MovieModel.formatUrlImagetoPicasso(image)).into(holder.mImageView);

        } else {
            MovieModel movieClicked = mMovieList[position];
            Picasso.with(mContext).load(movieClicked.takeUrlImage()).into(holder.mImageView);
        }

    }

    @Override
    public int getItemCount() {

        if (isFavorite()) {

            if (mCursor == null) {
                return 0;
            }

            return mCursor.getCount();

        } else {

            if (null == mMovieList) {
                return 0;
            } else {
                return mMovieList.length;
            }
        }
    }

    public void setMovieList(MovieModel[] movie) {
        mMovieList = movie;
        notifyDataSetChanged();
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean mFavorite) {
        this.mFavorite = mFavorite;
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(MovieModel movie);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitleMovie;
        TextView mDateMovie;
        TextView mRuntimeMovie;
        TextView mVoteAverageMovie;
        TextView mOverviewMovie;
        ImageView mImageView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mTitleMovie = view.findViewById(R.id.tv_movie_title);
            mImageView = view.findViewById(R.id.iv_movie);
            mDateMovie = view.findViewById(R.id.tv_movie_release_date);
            mRuntimeMovie = view.findViewById(R.id.tv_movie_runtime);
            mVoteAverageMovie = view.findViewById(R.id.tv_movie_vote_average);
            mOverviewMovie = view.findViewById(R.id.tv_movie_overview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (isFavorite()) {
                int adapterPosition = getAdapterPosition();
                MovieModel movieClicked = new MovieModel();
                int idIndex = mCursor.getColumnIndex(MovieContract.MovieEntry._ID);
                int nameIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME);
                int imageIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE);
                int dateIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATE);
                int runtimeIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RUNTIME);
                int voteAverageIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE);
                int overviewIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);
                int idMovieIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IDMOVIE);

                mCursor.moveToPosition(adapterPosition);

                final int id = mCursor.getInt(idIndex);
                String name = mCursor.getString(nameIndex);
                String image = mCursor.getString(imageIndex);
                String date = mCursor.getString(dateIndex);
                String runtime = mCursor.getString(runtimeIndex);
                String voteaverage = mCursor.getString(voteAverageIndex);
                String overview = mCursor.getString(overviewIndex);
                String idmovie = mCursor.getString(idMovieIndex);

                movieClicked.setTitle(name);
                movieClicked.setPoster_path(image);
                movieClicked.setRelease_date(date);
                movieClicked.setRuntime(runtime);
                movieClicked.setVote_average(voteaverage);
                movieClicked.setOverview(overview);
                movieClicked.setId(idmovie);
                mClickHandler.onClick(movieClicked);
            } else {
                int adapterPosition = getAdapterPosition();
                MovieModel movieClicked = mMovieList[adapterPosition];
                mClickHandler.onClick(movieClicked);
            }
        }
    }
}
