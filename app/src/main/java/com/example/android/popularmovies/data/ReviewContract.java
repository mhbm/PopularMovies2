package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lsitec101.macedo on 26/09/17.
 */

public class ReviewContract {

    public static final String AUTHORITY = "com.example.android.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_REVIEW = "review";

    public static final class ReviewEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String TABLE_NAME = "reviews";

        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_ID_MOVIE = "idMovie";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
