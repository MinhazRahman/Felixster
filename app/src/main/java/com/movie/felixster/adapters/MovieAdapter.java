package com.movie.felixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.movie.felixster.activities.MovieDetailActivity;
import com.movie.felixster.R;
import com.movie.felixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    Context context;
    List<Movie> movies;
    int POPULAR = 1, LESS_POPULAR = 0, STARS = 7;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    // inflate item layout from XML and return the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (viewType == POPULAR){
            View movieView1 = layoutInflater.inflate(R.layout.item_image_movie, parent, false);
            viewHolder = new ViewHolder(movieView1);
        }
        else {
            View movieView2 = layoutInflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(movieView2);
        }

        //View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);

        return viewHolder;
    }

    // populate the data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the movie at the given position
        Movie movie = movies.get(position);

        // bind the movie data in the ViewHolder based on the popularity
        if (holder.getItemViewType() == POPULAR){
            holder.bindPopular(movie);
        }
        else {
            holder.bind(movie);
        }
    }

    // returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).getVoteAverage() > STARS){
            return POPULAR;
        }else {
            return LESS_POPULAR;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout containerMovie;
        RelativeLayout containerMovieImage;
        ImageView imageViewPoster;
        TextView textViewMovieTitle;
        TextView textVieMovieOverview;
        ImageView imageViewBackdrop;
        ImageView iconPlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            containerMovie = itemView.findViewById(R.id.container_movie);
            containerMovieImage = itemView.findViewById(R.id.container_movie_image);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewMovieTitle = itemView.findViewById(R.id.textViewMovieTitle);
            textVieMovieOverview = itemView.findViewById(R.id.textViewMovieOverview);
            imageViewBackdrop = itemView.findViewById(R.id.imageViewBackdrop);
            iconPlay = itemView.findViewById(R.id.iconPlay);

        }

        public void bind(Movie movie) {
            textViewMovieTitle.setText(movie.getTitle());
            // set inter_word justification mode for overview
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                textVieMovieOverview.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            }
            textVieMovieOverview.setText(movie.getOverview());

            // get image url depending on landscape or portrait mode
            String imageUrl;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
            }
            else {
                imageUrl = movie.getPosterPath();
            }

            // Both new CenterInside() and new CenterCrop() work to get rounder corner images
            Glide.
                    with(context)
                    .load(imageUrl)
                    .transform(new CenterInside(), new RoundedCorners(24))
                    .placeholder(R.drawable.ic_placeholder_image_128)
                    .into(imageViewPoster);

            // Register click listener on whole row
            containerMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to the MovieDetailActivity on tap
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    // Wrap movie object with Parcels.wrap()
                    intent.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent);
                }
            });

        }

        public void bindPopular(Movie movie){
            // get image url depending on landscape or portrait mode
            String imageUrl;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
            }
            else {
                imageUrl = movie.getPosterPath();
            }

            // Both new CenterInside() and new CenterCrop() work to get rounder corner images
            Glide.
                    with(context)
                    .load(imageUrl)
                    .transform(new CenterCrop(), new RoundedCorners(24))
                    .placeholder(R.drawable.ic_placeholder_image_128)
                    .into(imageViewBackdrop);

            // Register click listener on whole row
            containerMovieImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Navigate to the MovieDetailActivity on tap
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    // Wrap movie object with Parcels.wrap()
                    intent.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent);
                }
            });
        }
    }
}
