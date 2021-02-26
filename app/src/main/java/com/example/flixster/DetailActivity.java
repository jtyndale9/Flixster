package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "";
    public static final String VIDEOS_URL = "";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;
    TextView release_date;
    TextView isAdult;
    TextView vote_count;
    String backdrop_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.ac_det_tvTitle);
        tvOverview = findViewById(R.id.ac_det_tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);
        release_date = findViewById(R.id.release_date);
        isAdult = findViewById(R.id.isAdult);
        vote_count = findViewById(R.id.vote_count);


        //String title = getIntent().getStringExtra("title");
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText("Overview: \n" + movie.getOverview());
        ratingBar.setRating((float) movie.getRating());
        release_date.setText(movie.getRelease_date());
        vote_count.setText("Out of " + movie.getVote_count() + " votes");
        if(movie.getIsAdult() == true){
            isAdult.setText("Rated Adult");
        }
        else{
            isAdult.setText("Safe for minors");
        }


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if(results.length() == 0){
                        return; //ADD ERROR HANDLING HERE, DISPLAY PLACEHOLDER IMAGE INSTEAD OF NOTHING
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youtubeKey);
                    initializeYoutube(youtubeKey);

                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON", e);
                }
            }
            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
            }
        });
    }

    private void initializeYoutube(final String youtubeKey) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity", "onInitializationSuccess");
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity", "onInitializationFailure");
            }
        });
    }
}

