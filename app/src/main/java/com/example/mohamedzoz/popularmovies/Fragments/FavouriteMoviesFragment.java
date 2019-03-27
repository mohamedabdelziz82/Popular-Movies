package com.example.mohamedzoz.popularmovies.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.mohamedzoz.popularmovies.Activities.FavouriteActivity;
import com.example.mohamedzoz.popularmovies.Adapter.GridAdapter;
import com.example.mohamedzoz.popularmovies.Data.Movie;
import com.example.mohamedzoz.popularmovies.Data.*;
import com.example.mohamedzoz.popularmovies.Activities.MainActivity;
import com.example.mohamedzoz.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteMoviesFragment extends Fragment {

    GridView Favourite_GridView ;
    List<Movie> needed_data ;



    public FavouriteMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_favourite_movies, container, false);

          needed_data = new ArrayList<>();
        Favourite_GridView = (GridView) v.findViewById(R.id.gridView_favourite);



        return v ;

    }

    private void View_Favourite_Movies() {




        Cursor c = getActivity().getContentResolver().query(MovieTable.CONTENT_URI, null, null, null, null);
        List<Movie> movieRows = MovieTable.getRows(c ,false);
        c.close();


        Favourite_GridView.setAdapter(new GridAdapter(movieRows , getContext()));
        Favourite_GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie M = null;
                M = (Movie) adapterView.getItemAtPosition(i);
                ((FavouriteActivity)getActivity()).selectFavoriteMovie(M , 0);

            }
        });
    }





    @Override
    public void onResume() {
        super.onResume();
        View_Favourite_Movies();

    }
    public interface  Callback{
        public void selectFavoriteMovie(Movie m , int check);
    }
}
