package com.movie.felixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.movie.felixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailActivity extends AppCompatActivity {
    TextView textViewTitle;
    TextView textViewOverview;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOverview = findViewById(R.id.textViewOverview);
        ratingBar = findViewById(R.id.ratingBar);

        // Unwrap the Parcel object
        Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        // Extract the properties of the movie object
        textViewTitle.setText(movie.getTitle());
        ratingBar.setRating((float) movie.getVoteAverage());
        textViewOverview.setText(movie.getOverview());

    }
}