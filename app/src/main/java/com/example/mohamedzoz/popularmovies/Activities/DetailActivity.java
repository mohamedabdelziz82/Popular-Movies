package com.example.mohamedzoz.popularmovies.Activities;
/**
*  Created by Mohamed on 25/10/2016.
*/
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.example.mohamedzoz.popularmovies.Data.Movie;
import com.example.mohamedzoz.popularmovies.Fragments.MovieDetailFragment;
import com.example.mohamedzoz.popularmovies.R;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent MO_IN=getIntent();
        Movie m = MO_IN.getExtras().getParcelable("Data form Movie");
        int check = MO_IN.getIntExtra("check", 1);
        Log.i("VALUE OF CHECK IN D_A" , String.valueOf(check));
        MovieDetailFragment movieDetailFragment= MovieDetailFragment.getInstance(m,check);
        getSupportFragmentManager().beginTransaction().replace(R.id.Movie_Detail_Fragment , movieDetailFragment).commit();


    }


}
