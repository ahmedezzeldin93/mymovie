package com.example.ahmedezzeldin.mymovie;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmedezzeldin on 19/12/15.
 */
public class MainFragment extends android.support.v4.app.Fragment {

    private com.example.ahmedezzeldin.mymovie.MovieGridAdapter movieGridAdapter;

    private static final String POPULARITY_DESC = "popularity.desc";
    private static final String RATING_DESC = "vote_average.desc";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        movieGridAdapter = new com.example.ahmedezzeldin.mymovie.MovieGridAdapter(getContext(),new ArrayList());
        new FetchMoviesTask().execute(POPULARITY_DESC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.movies_grid);
        gridview.setAdapter(movieGridAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movieClicked = movieGridAdapter.getItem(position);
                Intent intent = new Intent(getContext(),DetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString("title",movieClicked.getTitle());
                extras.putString("date",movieClicked.getDate());
                extras.putString("overview",movieClicked.getOverview());
                extras.putString("backdrop",movieClicked.getBackdrop());
                extras.putString("rating", Double.toString(movieClicked.getVoteAverage()));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by_popularity:
                new FetchMoviesTask().execute(POPULARITY_DESC);
                return true;
            case R.id.action_sort_by_rating:
                new FetchMoviesTask().execute(RATING_DESC);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateAdapter(List<Movie> moviesList){
        movieGridAdapter.setData(moviesList);
    }

    public class FetchMoviesTask extends AsyncTask<String,Void,List<Movie>> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected List<Movie> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonStr = null;
            try {
                final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_BY_PARAM = "sort_by";
                final String API_KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_BY_PARAM, params[0])
                        .appendQueryParameter(API_KEY_PARAM, getString(R.string.api_key))
                        .build();

                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                jsonStr = buffer.toString();
                Log.v(LOG_TAG,jsonStr);
            } catch (IOException e) {
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
                return getMoviesFromJson(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        private ArrayList<Movie> getMoviesFromJson(String jsonStr) throws JSONException {
            JSONObject moviesObject = new JSONObject(jsonStr);
            JSONArray resultsArray = moviesObject.getJSONArray("results");
            ArrayList<Movie> moviesArray= new ArrayList<>();

            for(int i=0; i< resultsArray.length(); i++){
                JSONObject movieObject = resultsArray.getJSONObject(i);
                Movie movie = new Movie(movieObject);
                moviesArray.add(movie);
            }
            return moviesArray;
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            updateAdapter(movieList);
        }
    }
}