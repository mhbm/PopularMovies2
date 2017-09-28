package com.example.android.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lsitec101.macedo on 26/09/17.
 */

public class TrailerContract {

    public static final String AUTHORITY = "com.example.android.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_REVIEW = "trailer";

    public static final class TrailerEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String TABLE_NAME = "trailers";

        public static final String COLUMN_KEY = "key";
        public static final String COLUMN_ID_MOVIE = "id_movie";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
