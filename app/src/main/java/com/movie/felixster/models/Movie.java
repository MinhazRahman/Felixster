package com.movie.felixster.models;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    int movieId;
    String title;
    String posterPath;
    String backdropPath;
    String overview;
    double voteAverage;

    // Empty constructor required by the Parceler library
    public Movie(){}

    // construct the Movie object
    public Movie(@NonNull JSONObject jsonObject) throws JSONException {
        title = jsonObject.getString("title");
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
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

    public int getMovieId(){
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        String secureBaseUrl = "https://image.tmdb.org/t/p/";
        String posterSize = "w500";

        return secureBaseUrl + posterSize + posterPath;
    }

    public String getBackdropPath() {
        String secureBaseUrl = "https://image.tmdb.org/t/p/";
        String backdropSize = "w780";

        return secureBaseUrl + backdropSize + backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
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
