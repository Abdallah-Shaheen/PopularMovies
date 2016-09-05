package com.mal.popularmovies.parsers;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by AbdAllah Shaheen on 8/29/2016.
 */
public abstract class Parser<T> {
    public abstract ArrayList<T> parse(String dataJsonStr) throws JSONException;
}
