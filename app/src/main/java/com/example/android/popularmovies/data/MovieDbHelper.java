package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lsitec101.macedo on 26/09/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 5;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to movie data
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieContract.MovieEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_IMAGE + " TEXT NOT NULL  UNIQUE, " +
                MovieContract.MovieEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_RUNTIME + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_IDMOVIE + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);


//        // Create a table to trailer data
//        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + TrailerContract.TrailerEntry.TABLE_NAME + " (" +
//                TrailerContract.TrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                TrailerContract.TrailerEntry.COLUMN_KEY + " TEXT NOT NULL, " +
//                TrailerContract.TrailerEntry.COLUMN_ID_MOVIE + " INTEGER NOT NULL "+
////                " FOREIGN KEY ("+ TrailerContract.TrailerEntry.COLUMN_ID_MOVIE +") REFERENCES "+ MovieContract.MovieEntry.TABLE_NAME +" ("+ MovieContract.MovieEntry._ID +")" +
//                "); ";
//
//        sqLiteDatabase.execSQL(SQL_CREATE_TRAILER_TABLE);
//
//        // Create a table to review data
//        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " + ReviewContract.ReviewEntry.TABLE_NAME + " (" +
//                ReviewContract.ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                ReviewContract.ReviewEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
//                ReviewContract.ReviewEntry.COLUMN_CONTENT + " TEXT NOT NULL, " +
//                ReviewContract.ReviewEntry.COLUMN_ID_MOVIE + " INTEGER NOT NULL, " +
//                ReviewContract.ReviewEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
////                " FOREIGN KEY ("+ ReviewContract.ReviewEntry.COLUMN_ID_MOVIE +") REFERENCES "+ MovieContract.MovieEntry.TABLE_NAME +" ("+ MovieContract.MovieEntry._ID +") " +
//                "); ";
//
//        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
