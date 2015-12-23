package com.example.ahmedezzeldin.mymovie;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by ahmedezzeldin on 22/12/15.
 */
public class DetailFragment extends android.support.v4.app.Fragment {


    String title;
    String overview;
    String date;
    String backdrop_path;
    String ratingString;
    float rating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras =  getArguments();
        if (extras != null) {
            title = extras.getString("title");
            date = extras.getString("date");
            overview = extras.getString("overview");
            backdrop_path = extras.getString("backdrop");
            ratingString = extras.getString("rating");
            rating = (float)Double.parseDouble(ratingString);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView title_textView = (TextView) rootView.findViewById(R.id.title_text_view);
        title_textView.setText(title);

        TextView date_textView = (TextView) rootView.findViewById(R.id.date_text_view);
        date_textView.setText(date);

        ImageView backdrop_imageView = (ImageView) rootView.findViewById(R.id.backdrop_image_view);
        String postersBaseUrl = "http://image.tmdb.org/t/p/w1000";
        String posterUrl = postersBaseUrl+ backdrop_path;
        Picasso.with(getContext()).load(posterUrl).into(backdrop_imageView);

        TextView overview_textView = (TextView) rootView.findViewById(R.id.overview_text_view);
        overview_textView.setText(overview);

        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.rating_rating_bar);
        Drawable progress = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, Color.YELLOW);
        ratingBar.setRating(rating/2);

        TextView rating_textView = (TextView) rootView.findViewById(R.id.rating_text_view);
        rating_textView.setText(ratingString);

        return rootView;
    }
}