package com.movie.felixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.movie.felixster.adapters.MovieAdapter;
import com.movie.felixster.models.Movie;
import com.movie.felixster.util.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String URL_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";

    List<Movie> movies;
    RecyclerView recyclerViewMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        movies = new ArrayList<>();

        // create an adapter
        // this refers to MainActivity, which is an instance of Context
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // set the adapter on the RecyclerView
        recyclerViewMovies.setAdapter(movieAdapter);

        // set a layout manager on the RecyclerView
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this));

        // decorate each item using decorators attached to the recyclerview
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        // attach a decorator to  the recyclerview for adding consistent spacing around items displayed
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);

        recyclerViewMovies.addItemDecoration(itemDecoration);
        recyclerViewMovies.addItemDecoration(decoration);

        // sending a GET request to the Movie Database API
        // create an AsyncHttpClient, and
        // then execute a request specifying an anonymous class as a callback
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(URL_NOW_PLAYING, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");

                    // update the list of movies
                    movies.addAll(Movie.getListOfMovies(results));

                    // whenever the list of movies is updated, notify the adapter
                    movieAdapter.notifyDataSetChanged();

                    Log.i(TAG, "Results: " + results);
                    Log.i(TAG, String.valueOf(movies));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}