package com.mal.popularmovies.parsers;

import android.util.Log;

import com.mal.popularmovies.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AbdAllah Shaheen on 8/29/2016.
 */
public class TrailerParser extends Parser {


    @Override
    public ArrayList<Trailer> parse(String trailersJsonStr) throws JSONException {

        final String LOG_TAG = "Pop Mov App MAL Shaheen";

        // We need to get array of Trailers objects from JSON
        //every Trailer object we need two keys with their values
        // "key" , "name"
        JSONObject trailersJson = new JSONObject(trailersJsonStr);
        JSONArray trailersArray = trailersJson.getJSONArray("results");

        ArrayList<Trailer> trailerList = new ArrayList<>();

        for (int i = 0; i < trailersArray.length(); i++) {
            Trailer trailer = new Trailer();

            JSONObject iTrailer = trailersArray.getJSONObject(i);

            trailer.key = iTrailer.getString("key");
            trailer.name = iTrailer.getString("name");

            trailerList.add(trailer);

            Log.i(LOG_TAG, i + " - " + trailer.key);
            Log.i(LOG_TAG, i + " - " + trailer.name);

        }

        return trailerList;
    }
}
