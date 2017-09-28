package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mateus Macedo on 26/09/17.
 */

public class MovieContract {

    public static final String AUTHORITY = "com.example.android.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String TABLE_NAME = "movies";


        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_IDMOVIE = "idmovie";
    }

}
