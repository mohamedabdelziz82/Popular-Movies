package com.example.mohamedzoz.popularmovies.Activities;

/**
 *  Created by Mohamed on 25/10/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mohamedzoz.popularmovies.Data.Movie;
import com.example.mohamedzoz.popularmovies.Fragments.MovieDetailFragment;
import com.example.mohamedzoz.popularmovies.Fragments.Populer_MainFragment;
import com.example.mohamedzoz.popularmovies.R;

public class MainActivity extends AppCompatActivity implements Populer_MainFragment.Callback {
    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            if (findViewById(R.id.Movie_Detail_Fragment) == null) {
                mTwoPane = false;
            } else {
                mTwoPane = true;
            }
    }

    @Override
    public void selectMovie(Movie m, int check) {
        if (!mTwoPane) {
            Intent Mo_In = new Intent(MainActivity.this, DetailActivity.class)
                    .putExtra("Data form Movie", m)
                    .putExtra("check",check);
            startActivity(Mo_In);
        } else {
            MovieDetailFragment Fragment = MovieDetailFragment.getInstance(m, check);
            getSupportFragmentManager().beginTransaction().replace(R.id.Movie_Detail_Fragment, Fragment).commit();
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
