package com.example.aningsopyan.tmdbnewnew.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aningsopyan.tmdbnewnew.DetailInterface;
import com.example.aningsopyan.tmdbnewnew.MainActivity;
import com.example.aningsopyan.tmdbnewnew.R;
import com.example.aningsopyan.tmdbnewnew.moviedetail.Genre;
import com.example.aningsopyan.tmdbnewnew.moviedetail.MovieDetail;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    TextView tvtitle,tvoverview,tvrelease,tvpopularity,tvvoteavg,tvvotecount;
    ImageView imageView;
    private String url_image = "http://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvtitle = findViewById(R.id.mtitle);
        tvoverview = findViewById(R.id.moverview);
        tvrelease = findViewById(R.id.mrelease);
        tvpopularity = findViewById(R.id.mpopularity);
        tvvoteavg = findViewById(R.id.mvoteavg);
        tvvotecount = findViewById(R.id.mvotecount);
        imageView = findViewById(R.id.toolbarImage);



        Intent intent = getIntent();
        int movie_id = intent.getExtras().getInt("id");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        DetailInterface detailInterface = retrofit.create(DetailInterface.class);
        Call<MovieDetail> call = detailInterface.getMovieDetails(movie_id);
        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                String title = response.body().getTitle();
                String overview = response.body().getOverview();
                String release = response.body().getReleaseDate();
                String popularity = response.body().getPopularity().toString();
                String voteavg = response.body().getVoteAverage().toString();
                String votecount = response.body().getVoteCount().toString();

//                Log.i("xxx", title);
                Log.i("yyy", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                tvtitle.setText(title);
                tvoverview.setText(overview);
                tvrelease.setText(release);
                tvpopularity.setText(popularity);
                tvvoteavg.setText(voteavg);
                tvvotecount.setText(votecount);
                Picasso.with(DetailActivity.this)
                        .load(url_image+response.body().getBackdropPath())
                        .into(imageView);
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {

                Log.e("gagal", String.valueOf(t));
            }
        });

    }

    public void home(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
