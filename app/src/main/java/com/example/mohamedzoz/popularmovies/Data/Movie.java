package com.example.mohamedzoz.popularmovies.Data;

import android.os.Parcel;
import android.os.Parcelable;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 *  Created by Mohamed on 25/10/2016.
 */

@SimpleSQLTable(table = "Movie", provider = "MovieProvider")
public class Movie implements Parcelable{

    @SimpleSQLColumn(value = "col_poster_path")
    private String poster_path ;
    @SimpleSQLColumn(value = "col_overview")
    private String overview ;
    @SimpleSQLColumn(value = "col_release_date")
    private String release_date ;
    @SimpleSQLColumn(value = "col_id" , primary = true)
    private int  id ;
    @SimpleSQLColumn(value = "col_title")
    public String title ;
    @SimpleSQLColumn(value = "col_vote_average")
    private String vote_average ;


    public Movie() {
    }

    public Movie(String poster_path, String overview, String release_date, int id, String title, String vote_average) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.id = id;
        this.title = title;
        this.vote_average = vote_average;
    }


    protected Movie(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        id = in.readInt();
        title = in.readString();
        vote_average = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(vote_average);
    }
}
