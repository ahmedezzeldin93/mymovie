package com.example.ahmedezzeldin.mymovie.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmedezzeldin.mymovie.R;
import com.example.ahmedezzeldin.mymovie.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ahmedezzeldin on 19/12/15.
 */
public class MovieGridAdapter extends BaseAdapter {



    private Context mContext;
    private List<Movie> moviesList;
    LayoutInflater inflater;
    private final String LOG_TAG = MovieGridAdapter.class.getSimpleName();

    public MovieGridAdapter(Context context, List moviesList) {
        this.mContext = context;
        this.moviesList = moviesList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Movie getItem(int position) {
        return moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(Movie movie) {
        synchronized (moviesList) {
            moviesList.add(movie);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        synchronized (moviesList){
            moviesList.clear();
        }
        notifyDataSetChanged();
    }

    public void setData(List<Movie> data) {
        clear();
        for (Movie movie : data) {
            add(movie);
        }
        Log.v(LOG_TAG,moviesList.toString());
    }

//    public void setData(List movies){
//        moviesList.clear();
//        moviesList.addAll(movies);
//        Log.v(LOG_TAG,moviesList.toString());
//    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.grid_view_item, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        final Movie movie = getItem(position);
        String postersBaseUrl = "http://image.tmdb.org/t/p/w185";
        String posterPath = movie.getPoster();
        String posterUrl = postersBaseUrl+posterPath;
        viewHolder = (ViewHolder) view.getTag();
        Picasso.with(mContext).load(posterUrl).into(viewHolder.imageView);
        viewHolder.titleView.setText(movie.getTitle());
        Log.v(LOG_TAG,"Image at "+ posterPath +" loaded");
        return view;
    }

    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView titleView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.grid_image_view);

            titleView = (TextView) view.findViewById(R.id.grid_text_view);
        }
    }
}