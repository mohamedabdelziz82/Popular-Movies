package com.example.mohamedzoz.popularmovies.FetchTraliersReviews;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mohamedzoz.popularmovies.Adapter.TralierAdapter;
import com.example.mohamedzoz.popularmovies.Data.Movie;
import com.example.mohamedzoz.popularmovies.Data.Tralier;
import com.linearlistview.LinearListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mohamed on 09/11/2016.
 */
public class FetchTrailers {

    ArrayList<Tralier> tralier_attributes = new ArrayList<>();

    private int id;
    Context context;
    LinearListView linearListView;

    public FetchTrailers(Context context, Movie movie, LinearListView Trailer_list ) {
        this.context = context;
        this.linearListView = Trailer_list;
        new Fetch_Trailers_Data().execute(movie);
    }


    public class Fetch_Trailers_Data extends AsyncTask<Movie, Void, ArrayList<Tralier>> {
        @Override
        protected ArrayList<Tralier> doInBackground(Movie... m) {


            StringBuilder sb = new StringBuilder();
            id = m[0].getId();


            String URL = "https://api.themoviedb.org/3/movie/" + id + "/videos?";
            String API_KEY_PARAM = "api_key";
            String MY_KEY = "24aa47d1d76ff9c2b66501ac46a377c8";

            Uri URL_AFTER_PARSE = Uri.parse(URL).buildUpon().appendQueryParameter(API_KEY_PARAM, MY_KEY).build();


            java.net.URL url = null;
            try {
                url = new URL(URL_AFTER_PARSE.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }


            con.setDoInput(true);


            try {
                con.connect();
            } catch (IOException e) {
                e.printStackTrace();


            }

            try {
                con.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }


            BufferedReader BR = null;
            try {
                InputStream IS = con.getInputStream();
                InputStreamReader ISR = new InputStreamReader(IS);
                BR = new BufferedReader(ISR);
                if (IS == null) {
                    Log.i("CHECK INPUT STREAM", "INPUT STREAM = NULL");
                    return null;
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            String Line = "";
            try {
                while ((Line = BR.readLine()) != null) {
                    sb.append(Line);

                    Log.i("CHECK STRING BUFFER", "I FOUND OUTPUT");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            String Result = sb.toString();

            try {
                JSONObject jsonObject = new JSONObject(Result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String key = jsonObject1.getString("key");
                    String name = jsonObject1.getString("name");


                    tralier_attributes.add(new Tralier(name ,"http://www.youtube.com/watch?v="+key));

                    Log.i(" CHECK TRALIERS ", tralier_attributes.get(i).getKey());


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (con != null) {
                con.disconnect();
            }
            if (BR != null) {
                try {
                    BR.close();
                } catch (final IOException e) {
                    Log.e("error", "Error closing stream", e);
                }
            }

            return tralier_attributes;
        }


        @Override
        protected void onPostExecute(ArrayList<Tralier> tralier_attributes) {
            super.onPostExecute(tralier_attributes);


            linearListView.setAdapter(new TralierAdapter(context, tralier_attributes));


        }



    }

}