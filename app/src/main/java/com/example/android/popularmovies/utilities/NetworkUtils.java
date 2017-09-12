package com.example.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Mateus Macedo on 28/07/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private final static String APIKEY = "ENTER YOU APIKEY";

    private final static String popularURL = "https://api.themoviedb.org/3/movie/popular";

    private final static String topRatedURL = "https://api.themoviedb.org/3/movie/top_rated";

    private final static String PARAM_QUERY = "api_key";


    public static URL buildUrl(int option) {
        Uri builtUri = null;
        if (option == 1) { ///Popular
            builtUri = Uri.parse(popularURL).buildUpon()
                    .appendQueryParameter(PARAM_QUERY, APIKEY).build();
            
        } else if (option == 2) { ///Top Rated
            builtUri = Uri.parse(topRatedURL).buildUpon()
                    .appendQueryParameter(PARAM_QUERY, APIKEY).build();
        }
        
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Built URI " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
