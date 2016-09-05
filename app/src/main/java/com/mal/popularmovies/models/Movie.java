package com.mal.popularmovies.models;

import android.os.Parcel;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AbdAllah Shaheen on 8/14/2016.
 */


public class Movie extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private String posterPath;
    private String originalTitle;
    private String releaseDate;
    private String overview;
    private double rating;



    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public double getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
