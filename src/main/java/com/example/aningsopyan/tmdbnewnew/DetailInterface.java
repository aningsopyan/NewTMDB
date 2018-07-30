package com.example.aningsopyan.tmdbnewnew;

import com.example.aningsopyan.tmdbnewnew.moviedetail.MovieDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DetailInterface {
    @GET("3/movie/{movie_id}?api_key=b613893ebd3138d8506fe5a38c2a312a")
    Call<MovieDetail> getMovieDetails(@Path("movie_id") int movie_id);
}
