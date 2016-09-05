package com.mal.popularmovies.parsers;

import android.util.Log;

import com.mal.popularmovies.models.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AbdAllah Shaheen on 8/29/2016.
 */
public class ReviewParser extends Parser {
    private final String LOG_TAG = "Pop Mov App MAL Shaheen";

    @Override
    public ArrayList<Review> parse(String reviewsJsonStr) throws JSONException {

        // We need to get array of Reviews objects from JSON
        //every Review object we need two keys with their values
        // "author" , "content"
        JSONObject reviewsJson = new JSONObject(reviewsJsonStr);
        JSONArray reviewsArray = reviewsJson.getJSONArray("results");

        ArrayList<Review> reviewList = new ArrayList<>();

        for (int i = 0; i < reviewsArray.length(); i++) {
            Review review = new Review();

            JSONObject iReview = reviewsArray.getJSONObject(i);

            review.author = iReview.getString("author");
            review.content = iReview.getString("content");

            reviewList.add(review);

            Log.i(LOG_TAG, i + " - " + review.author);
            Log.i(LOG_TAG, i + " - " + review.content);

        }

        return reviewList;
    }
}
