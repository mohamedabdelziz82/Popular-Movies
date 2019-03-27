package com.example.mohamedzoz.popularmovies.Fragments;
/**
 *  Created by Mohamed on 25/10/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mohamedzoz.popularmovies.Activities.DetailActivity;
import com.example.mohamedzoz.popularmovies.Activities.FavouriteActivity;
import com.example.mohamedzoz.popularmovies.Activities.MainActivity;
import com.example.mohamedzoz.popularmovies.Adapter.GridAdapter;
import com.example.mohamedzoz.popularmovies.Data.Movie;
import com.example.mohamedzoz.popularmovies.R;

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
import java.util.List;


public class Populer_MainFragment extends Fragment {


    GridView gridView;
    List<Movie> needed_data;
    ProgressDialog Wait;
    GridAdapter gridAdapter;

    public Populer_MainFragment() {
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) V.findViewById(R.id.gridView_popularity);
        needed_data = new ArrayList<>();


        if (isOnline()) {
            new Fetch_Movies_Data().execute("http://api.themoviedb.org/3/movie/popular?");
        } else {
            Toast.makeText(getActivity(), "Please Check Your Connection,Then Press On Refresh Icon", Toast.LENGTH_SHORT).show();
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Movie M = null;
                M = (Movie) parent.getItemAtPosition(i);
                ((MainActivity)getActivity()).selectMovie(M , 1);

            }
        });


        return V;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_most_popular) {

            if (isOnline()) {
                needed_data.clear();
                new Fetch_Movies_Data().execute("http://api.themoviedb.org/3/movie/popular?");
            } else {
                Toast.makeText(getActivity(), "Please Check Your Connection,Then Press On Refresh Icon", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        if (id == R.id.action_highest_rated) {

            if (isOnline()) {
                needed_data.clear();
                new Fetch_Movies_Data().execute("http://api.themoviedb.org/3/movie/top_rated?");
            } else {
                Toast.makeText(getActivity(), "Please Check Your Connection,Then Press On Refresh Icon", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (id == R.id.action_favorite) {
            Intent intent=new Intent(getActivity(), FavouriteActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public class Fetch_Movies_Data extends AsyncTask<String, Void, List<Movie>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            Wait = ProgressDialog.show(getActivity(), "Loading", "Please Wait", true, false);
            Log.i("PROGRESS DIALOG ", " PROCCESSING ");
        }

        @Override
        protected List<Movie> doInBackground(String... param) {
            String s = param[0];

            StringBuilder sb = new StringBuilder();
            String Url = s;
            String API_KEY_BARAM = "api_key";
            String MY_API_KEY = "24aa47d1d76ff9c2b66501ac46a377c8";

            Uri URL_AFTER_PARSE = Uri.parse(Url).buildUpon().appendQueryParameter(API_KEY_BARAM, MY_API_KEY).build();

            URL url = null;
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


            } catch (IOException e) {
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
            final String MO_RESULT = "results";
            final String MO_POSTER_PATH = "poster_path";
            final String MO_OVERVIEW = "overview";
            final String MO_RELEASE_DATE = "release_date";
            final String MO_ID = "id";
            final String MO_ORIGINAL_TITLE = "original_title";
            final String MO_VOTE_AVERAGE = "vote_average";

            try {
                JSONObject jsonObject = new JSONObject(Result);
                JSONArray jsonArray = jsonObject.getJSONArray(MO_RESULT);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String poster_path = jsonObject1.getString(MO_POSTER_PATH);
                    String overview = jsonObject1.getString(MO_OVERVIEW);
                    String release_date = jsonObject1.getString(MO_RELEASE_DATE);
                    int id = jsonObject1.getInt(MO_ID);
                    String title = jsonObject1.getString(MO_ORIGINAL_TITLE);
                    String vote_average = jsonObject1.getString(MO_VOTE_AVERAGE);

                    needed_data.add(new Movie(poster_path, overview, release_date, id, title, vote_average));
                    Log.i(" CHECK POSTERS " + i + " :  ", needed_data.get(i).getPoster_path());
                    Log.i(" CHECK ID " + i + " : ", String.valueOf(needed_data.get(i).getId()));

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


            return needed_data;
        }


        @Override
        protected void onPostExecute(List<Movie> needed_data) {
            super.onPostExecute(needed_data);
            gridAdapter = new GridAdapter(needed_data, getContext());

            gridView.setAdapter(gridAdapter);

            Wait.dismiss();

        }

    }
    public interface  Callback{
        public void selectMovie(Movie m , int check);
    }

}
