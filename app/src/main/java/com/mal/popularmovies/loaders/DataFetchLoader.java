package com.mal.popularmovies.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.mal.popularmovies.parsers.Parser;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by AbdAllah Shaheen on 8/29/2016.
 */
public class DataFetchLoader<T> extends AsyncTaskLoader<ArrayList<T>>{

    URL dataUrl;
    Parser<T> parseType;
    private final String LOG_TAG = "Pop Mov App MAL Shaheen";

    public DataFetchLoader(Context context, URL dataUrl, Parser<T> parseType){
        super(context);
        this.dataUrl = dataUrl;
        this.parseType = parseType;
    }

    @Override
    public ArrayList<T> loadInBackground() {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String dataJsonStr = null;

        try {


            URL url = new URL(dataUrl.toString());

            Log.i(LOG_TAG, url.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            dataJsonStr = buffer.toString();

            Log.v(LOG_TAG, "Data Json String: " + dataJsonStr);

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }

        }

        try {
            Log.i(LOG_TAG, "retern move in doinB");
            Parser<T> parser = parseType;
            return parser.parse(dataJsonStr);
        } catch (JSONException e) {
            Log.i(LOG_TAG,"no retern move in doinB");
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }


        return null;
    }

}

