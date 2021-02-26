package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    String backdropPath;
    String posterPath;
    String title;
    String overview;
    double rating;
    int movieId;
    String release_date;
    boolean isAdult;
    int vote_count;
    String backdrop_path;

    // empty constructor needed by the Parceler library
    public Movie(){
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
        release_date = jsonObject.getString("release_date");
        isAdult = jsonObject.getBoolean("adult");
        vote_count = jsonObject.getInt("vote_count");
        backdrop_path = jsonObject.getString("backdrop_path");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getPosterPath() {
        //SHOULD be appending the size (w342) then append path, but not for this exercise
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating(){
        return rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getRelease_date() {
        return release_date;
    }

    public boolean getIsAdult() {
        return isAdult;
    }

    public int getVote_count(){
        return vote_count;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }
}
