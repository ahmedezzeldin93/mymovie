package com.example.ahmedezzeldin.mymovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.ahmedezzeldin.mymovie.MainFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ahmedezzeldin on 31/12/15.
 */
public class Movie implements Parcelable {

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

    public int getMovieId() {
        return movieId;
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

    public float getRating() {

        float rating = (float)voteAverage;
        return rating;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public Movie(Cursor cursor) {
        this.movieId = cursor.getInt(MainFragment.COL_MOVIE_ID);
        this.title = cursor.getString(MainFragment.COL_TITLE);
        this.poster = cursor.getString(MainFragment.COL_IMAGE);
        this.backdrop = cursor.getString(MainFragment.COL_IMAGE2);
        this.overview = cursor.getString(MainFragment.COL_OVERVIEW);
        this.voteAverage = cursor.getInt(MainFragment.COL_RATING);
        this.date = cursor.getString(MainFragment.COL_DATE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(backdrop);
        dest.writeString(overview);
        dest.writeDouble(voteAverage);
        dest.writeString(date);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        movieId = in.readInt();
        title = in.readString();
        poster = in.readString();
        backdrop = in.readString();
        overview = in.readString();
        voteAverage = in.readDouble();
        date = in.readString();
    }
}