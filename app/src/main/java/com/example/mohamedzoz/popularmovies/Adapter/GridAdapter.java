package com.example.mohamedzoz.popularmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.mohamedzoz.popularmovies.Data.Movie;
import com.example.mohamedzoz.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mohamed on 25/10/2016.
 */

public class GridAdapter extends BaseAdapter {

    List<Movie> needed_data ;
    Context context ;

    public GridAdapter(List<Movie> needed_datas, Context context) {
        this.needed_data = needed_datas ;
        this.context = context ;
    }


    @Override
    public int getCount() {
        return needed_data.size();
    }

    @Override
    public Movie getItem(int i) {
        return needed_data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {



        String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
        String POSTER = needed_data.get(i).getPoster_path();


        viewHolder holder = null ;
        if (view == null){
            view  = LayoutInflater.from(context).inflate(R.layout.poster, viewGroup, false);
            holder = new viewHolder(view);
            view.setTag(holder);
        }else{
            holder  = (viewHolder) view.getTag();
        }


        Picasso.with(context).load( IMAGE_URL + POSTER).into(holder.imageView);

        return view;
    }

    public class viewHolder{
        ImageView imageView ;

        public viewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.Grid_View_Poster);
        }
    }

}
