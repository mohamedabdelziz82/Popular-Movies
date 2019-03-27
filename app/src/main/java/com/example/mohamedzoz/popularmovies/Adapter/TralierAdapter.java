package com.example.mohamedzoz.popularmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mohamedzoz.popularmovies.Data.Tralier;
import com.example.mohamedzoz.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by Mohamed on 09/11/2016.
 */
public class TralierAdapter extends BaseAdapter {

    Context context ;
    ArrayList<Tralier> tralier_attributes ;

    public TralierAdapter(Context context, ArrayList<Tralier> tralier_attributes) {

        this.context = context ;
        this.tralier_attributes = tralier_attributes ;
    }

    @Override
    public int getCount() {
        return tralier_attributes.size();
    }

    @Override
    public Tralier getItem(int i) {
        return tralier_attributes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder = null ;
        if (view == null){
            view  = LayoutInflater.from(context).inflate(R.layout.tralier_row, viewGroup, false);
            holder = new viewHolder(view);
            view.setTag(holder);
        }else {
            holder  = (viewHolder) view.getTag();
        }

        Tralier item = getItem(i);
        holder.trailer_name.setText(item.getName());

        return view;
    }



    public class viewHolder{

//        ImageView trailer_icon ;
        TextView  trailer_name ;

        public viewHolder(View v) {

//            trailer_icon = (ImageView) v.findViewById(R.id.Trailer_Icon);
            trailer_name = (TextView) v.findViewById(R.id.Trailer_Name);
        }
    }
}
