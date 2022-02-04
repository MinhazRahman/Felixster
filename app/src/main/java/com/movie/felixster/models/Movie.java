package com.movie.felixster.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.movie.felixster.util.ImageURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    String title;
    String posterPath;
    String backdropPath;
    String overview;

    // construct the Movie object
    public Movie(@NonNull JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        overview = jsonObject.getString("overview");
    }

    // get the List of Movies from the json array
    @NonNull
    public static List<Movie> getListOfMovies(@NonNull JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for (int i=0; i<movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }

        return movies;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        String secureBaseUrl = "https://image.tmdb.org/t/p/";
        String posterSize = "w342";

        return secureBaseUrl + posterSize + posterPath;
    }

    public String getBackdropPath() {
        String secureBaseUrl = "https://image.tmdb.org/t/p/";
        String backdropSize = "w300";

        return secureBaseUrl + backdropSize + backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", posterPath='" + getPosterPath() + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
