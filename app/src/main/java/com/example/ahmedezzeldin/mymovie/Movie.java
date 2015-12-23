package com.example.ahmedezzeldin.mymovie;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ahmedezzeldin on 31/12/15.
 */
public class Movie {

    private int movieId;
    private String title;
    private String date;
    private String overview;
    private String poster;
    private String backdrop;
    private double popularity;
    private double voteAverage;

    private final String LOG_TAG = Movie.class.getSimpleName();

    public Movie(){}

    public Movie(JSONObject movie) throws JSONException {
        this.movieId = movie.getInt("id");
        this.title = movie.getString("original_title");
        this.date = movie.getString("release_date");
        this.overview = movie.getString("overview");
        this.poster = movie.getString("poster_path");
        this.backdrop = movie.getString("backdrop_path");
        this.popularity = movie.getDouble("popularity");
        this.voteAverage = movie.getDouble("vote_average");
        Log.v(LOG_TAG, this.toString());
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", overview='" + overview + '\'' +
                ", poster='" + poster + '\'' +
                ", backdropImage='" + backdrop + '\'' +
                ", popularity=" + popularity +
                ", voteAverage=" + voteAverage +
                ", LOG_TAG='" + LOG_TAG + '\'' +
                '}';
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public double getPopularity() {

        return popularity;
    }

    public String getOverview() {

        return overview;
    }

    public String getPoster() {

        return poster;
    }

    public String getDate() {

        return date;
    }

    public String getTitle() {

        return title;
    }

    public String getBackdrop() {
        return backdrop;
    }

}