package com.example.mohamedzoz.popularmovies.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mohamedzoz.popularmovies.Data.Movie;
import com.example.mohamedzoz.popularmovies.Fragments.FavouriteMoviesFragment;
import com.example.mohamedzoz.popularmovies.Fragments.MovieDetailFragment;
import com.example.mohamedzoz.popularmovies.R;

public class FavouriteActivity extends AppCompatActivity implements  FavouriteMoviesFragment.Callback {
    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_favourite, new FavouriteMoviesFragment())
                    .commit();
        }
        Intent i = getIntent();
    }

    @Override
    public void selectFavoriteMovie(Movie m, int check) {
        if (!mTwoPane) {
            Intent Mo_In = new Intent(FavouriteActivity.this, DetailActivity.class)
                    .putExtra("Data form Movie", m)
                    .putExtra("check", check);
            startActivity(Mo_In);
        } else {
            MovieDetailFragment Fragment = MovieDetailFragment.getInstance(m, check);
            getSupportFragmentManager().beginTransaction().replace(R.id.Movie_Detail_Fragment, Fragment).commit();
        }

    }
}