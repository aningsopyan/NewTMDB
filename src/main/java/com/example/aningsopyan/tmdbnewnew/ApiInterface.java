package com.example.aningsopyan.tmdbnewnew;

import android.renderscript.Sampler;

import com.example.aningsopyan.tmdbnewnew.model.TopRatedMovies;
import com.example.aningsopyan.tmdbnewnew.search.MovieSearch;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("3/movie/top_rated")
    Call<TopRatedMovies> getTopRatedMovies (@Query("api_key") String apiKey);

    @GET("3/search/movie")
    Call<MovieSearch> getMovieSearch(String s, @Query("api_key") String apiKey);

}
