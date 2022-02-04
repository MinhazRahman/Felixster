package com.movie.felixster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.movie.felixster.R;
import com.movie.felixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    // inflate item layout from XML and return the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(movieView);
    }

    // populate the data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the movie at the given position
        Movie movie = movies.get(position);

        // bind the movie data in the ViewHolder
        holder.bind(movie);
    }

    // returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewPoster;
        TextView textViewMovieTitle;
        TextView textVieMovieOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewMovieTitle = itemView.findViewById(R.id.textViewMovieTitle);
            textVieMovieOverview = itemView.findViewById(R.id.textViewMovieOverview);
        }

        public void bind(Movie movie) {
            textViewMovieTitle.setText(movie.getTitle());
            textVieMovieOverview.setText(movie.getOverview());
            Glide.with(context).load(movie.getPosterPath()).into(imageViewPoster);
        }
    }
}
