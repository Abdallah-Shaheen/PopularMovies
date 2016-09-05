package com.mal.popularmovies.parsers;

import android.util.Log;

import com.mal.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AbdAllah Shaheen on 8/29/2016.
 */
public class MovieParser extends Parser {

    private final String LOG_TAG = "Pop Mov App MAL Shaheen";

    @Override
    public ArrayList<Movie> parse(String moviesJsonStr) throws JSONException {

        // We need to get array of movies objects from JSON
        //every movie object we need five keys with their values
        // "poster_path" , "overview","release_date","original_title","vote_average" = rating
        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray("results");

        ArrayList<Movie> movieList = new ArrayList<>();

        for (int i = 0; i < moviesArray.length(); i++) {
            Movie movie = new Movie();

            JSONObject iMovie = moviesArray.getJSONObject(i);

            movie.setId(iMovie.getInt("id"));
            movie.setPosterPath(iMovie.getString("poster_path"));
            movie.setOriginalTitle(iMovie.getString("original_title"));
            movie.setReleaseDate(iMovie.getString("release_date"));
            movie.setOverview(iMovie.getString("overview"));
            movie.setRating(iMovie.getDouble("vote_average"));

            movieList.add(movie);

            Log.i(LOG_TAG, i + " - " + movie.getId());
            Log.i(LOG_TAG, i + " - " + movie.getOriginalTitle());
            Log.i(LOG_TAG, i + " - " + movie.getPosterPath());
            Log.i(LOG_TAG, i + " - " + movie.getReleaseDate());
            Log.i(LOG_TAG, i + " - " + movie.getOverview());
            Log.i(LOG_TAG, i + " - " + movie.getRating());

        }

        return movieList;
    }
}
