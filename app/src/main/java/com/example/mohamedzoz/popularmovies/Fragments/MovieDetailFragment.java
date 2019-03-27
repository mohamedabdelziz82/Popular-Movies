package com.example.mohamedzoz.popularmovies.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mohamedzoz.popularmovies.Data.Movie;
import com.example.mohamedzoz.popularmovies.Data.MovieTable;
import com.example.mohamedzoz.popularmovies.Data.Tralier;
import com.example.mohamedzoz.popularmovies.FetchTraliersReviews.FetchReviews;
import com.example.mohamedzoz.popularmovies.FetchTraliersReviews.FetchTrailers;
import com.example.mohamedzoz.popularmovies.R;
import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;

/**
 *  Created by Mohamed on 25/10/2016.
 */  public  class MovieDetailFragment extends Fragment {

    private static final String MOVIES_SHARE_HASHTAG = " #PopularMoviesApp";

    private ShareActionProvider mShareActionProvider;
    String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    TextView title;
    TextView avge_rate;
    TextView reslease_date;
    TextView overview;
    ImageView small_poster;
    LinearListView Trailer_list ;
    LinearListView Review_List ;
    ToggleButton Favourite ;
    private Movie movie;
    int check ;


    public MovieDetailFragment() {
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if (movie != null) {
            mShareActionProvider.setShareIntent(createShareMoviesIntent());
        }
    }
    private Intent createShareMoviesIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, movie + MOVIES_SHARE_HASHTAG);
        return shareIntent;
    }

    public static MovieDetailFragment getInstance(Movie m  , int check ){

        MovieDetailFragment Fragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie",m);
        bundle.putInt("recive" , check);
        Log.i("VALUE OF CHECK IN D_F" , String.valueOf(check));
        Fragment.setArguments(bundle);

        return Fragment ;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);

            title = (TextView) root.findViewById(R.id.Movie_Title);
            avge_rate = (TextView) root.findViewById(R.id.Movie_Rating);
            reslease_date = (TextView) root.findViewById(R.id.Movie_Release_Date);
            overview = (TextView) root.findViewById(R.id.Movie_Overview);
            small_poster = (ImageView) root.findViewById(R.id.Movie_Small_Poster);
            Trailer_list = (LinearListView) root.findViewById(R.id.Trailers_List);
            Review_List = (LinearListView) root.findViewById(R.id.Reviews_List);
            Favourite = (ToggleButton) root.findViewById(R.id.Favourite_Button);



        Trailer_list.setOnItemClickListener(new LinearListView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView parent, View view, int position , long id) {
                if (parent.getId() == R.id.Trailers_List){

                    Tralier item = (Tralier) parent.getAdapter().getItem(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(item.getKey()));
                    intent = Intent.createChooser(intent, " Play With ");
                    startActivity(intent);

                }
            }
        });

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movie = getArguments().getParcelable("movie");
        check = getArguments().getInt("recive");
        Log.i("CHECK RECIVE VALUE" , String.valueOf(check));
        if (check == 0){

        }else {

            new FetchReviews(getActivity(), movie, Review_List);
            new FetchTrailers(getActivity(), movie, Trailer_list);
        }

        title.setText(movie.getTitle());
        avge_rate.setText("Rating [ " + movie.getVote_average() + " / 10 ]");
        reslease_date.setText("Date [ " + movie.getRelease_date() + " ]");
        overview.setText(movie.getOverview());
        Picasso.get().load(IMAGE_URL + movie.getPoster_path()).into(small_poster);


        String [] title = {"col_title"};
        String selction = "col_title"+"=?";
        String [] args = {movie.getTitle()};
        Cursor c = getActivity().getContentResolver().query(MovieTable.CONTENT_URI, title, selction, args , null);



        if (c.moveToFirst()){

            Favourite.setBackgroundColor(Color.parseColor("#00838F"));
            Favourite.setChecked(true);
        }



        Favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Favourite.setBackgroundColor(Color.parseColor("#00838F"));
                    getActivity().getContentResolver().insert(MovieTable.CONTENT_URI , MovieTable.getContentValues(movie ,false)) ;

                    Toast.makeText(getActivity(), "Saved In Favourite", Toast.LENGTH_SHORT).show();
                } else if (!isChecked){
                    Favourite.setBackgroundColor(Color.parseColor("#90CAF9"));
                    String selction = "col_title"+"=?";
                    String [] args = {movie.getTitle()};
                    getActivity().getContentResolver().delete(MovieTable.CONTENT_URI, selction , args );
                    Toast.makeText(getActivity(), "Removed", Toast.LENGTH_SHORT).show();
                }

            }
        });



}}