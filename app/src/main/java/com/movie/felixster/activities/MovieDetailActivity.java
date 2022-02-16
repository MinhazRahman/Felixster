package com.movie.felixster.activities;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.movie.felixster.R;
import com.movie.felixster.databinding.ActivityMainBinding;
import com.movie.felixster.databinding.ActivityMovieDetailBinding;
import com.movie.felixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailActivity extends YouTubeBaseActivity {
    private static final String YOUTUBE_API_KEY = "AIzaSyD0_t9POZb-IS5ZtmfAarf4kYUiWC7gK_U";
    private static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final double STAR_RATING_FOR_POPULAR_MOVIE = 7.0;

    // Store the binding
    // If the XML Layout name is activity_movie_detail.xml then DataBinding class name
    // will be ActivityMovieDetailBinding.
    private ActivityMovieDetailBinding binding;

    double movieRating;

    YouTubePlayerView youTubePlayerView;
    TextView textViewTitle;
    TextView textViewOverview;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the content view (replacing `setContentView`)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        // The most basic thing we get with data binding is the elimination of findViewById
        // Store the field now without any need for casting
        youTubePlayerView = binding.player;
        textViewTitle = binding.textViewTitle;
        textViewOverview = binding.textViewOverview;
        ratingBar = binding.ratingBar;

        // Unwrap the Parcel object
        Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        // Extract the properties of the movie object
        textViewTitle.setText(movie.getTitle());
        ratingBar.setRating((float) movie.getVoteAverage());

        // set inter_word justification mode for movie overview
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            textViewOverview.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
        textViewOverview.setText(movie.getOverview());

        movieRating = movie.getVoteAverage();

        // send request to movie db api
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(String.format(VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
               JSONObject jsonObject = json.jsonObject;
                JSONArray results = null;
                try {
                    results = jsonObject.getJSONArray("results");

                    // Check if results is null or not
                    if (results.length() == 0){
                        return;
                    }
                    // get the key from the json object
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    initializeYoutube(youtubeKey, movieRating);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("MovieDetailActivity", "onFailure");
            }
        });
    }

    private void initializeYoutube(final String youtubeKey, double rating) {
        // Streaming Youtube Videos with YouTubePlayerView
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (rating >= STAR_RATING_FOR_POPULAR_MOVIE){
                    // load and automatically play the video
                    youTubePlayer.loadVideo(youtubeKey);
                }else {
                    // just load the video and the thumbnail but don't autoplay
                    youTubePlayer.cueVideo(youtubeKey);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("MovieDetailActivity", "onInitializationFailure");
            }
        });
    }
}