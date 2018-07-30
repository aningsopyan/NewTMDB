package com.example.aningsopyan.tmdbnewnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import com.example.aningsopyan.tmdbnewnew.adapter.Adapter;
import com.example.aningsopyan.tmdbnewnew.model.Result;
import com.example.aningsopyan.tmdbnewnew.model.TopRatedMovies;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.example.aningsopyan.tmdbnewnew.R.*;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private SearchView searchView;
    private final static String API_KEY = "b613893ebd3138d8506fe5a38c2a312a";
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        searchView = findViewById(id.search_view);
        searchView.setOnQueryTextListener(this);
        searchView.setFocusable(false);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface service = retrofit.create(ApiInterface.class);

        Call<TopRatedMovies> results = service.getTopRatedMovies(API_KEY);
        results.enqueue(new Callback<TopRatedMovies>() {

            @Override
            public void onResponse(Call<TopRatedMovies> call, Response<TopRatedMovies> response) {

                List<Result> results = response.body().getResults();
                int statuscode = response.code();
                Log.i("berhasil", String.valueOf(statuscode));
                Log.i("jsoncode", new GsonBuilder().setPrettyPrinting().create().toJson(results));

                adapter = new Adapter(results, layout.list_item, getApplicationContext());
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<TopRatedMovies> call, Throwable t) {

                Log.e("gagal", String.valueOf(t));
            }
        });

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        if (!s.equals("")){

            adapter.filter(s);
        }

        else if (s.equals("")){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface service = retrofit.create(ApiInterface.class);

            Call<TopRatedMovies> results = service.getTopRatedMovies(API_KEY);
            results.enqueue(new Callback<TopRatedMovies>() {

                @Override
                public void onResponse(Call<TopRatedMovies> call, Response<TopRatedMovies> response) {

                    List<Result> results = response.body().getResults();
                    int statuscode = response.code();
                    Log.i("berhasil", String.valueOf(statuscode));
                    Log.i("jsoncode", new GsonBuilder().setPrettyPrinting().create().toJson(results));

                    adapter = new Adapter(results, layout.list_item, getApplicationContext());
                    recyclerView.setAdapter(adapter);


                }

                @Override
                public void onFailure(Call<TopRatedMovies> call, Throwable t) {

                    Log.e("gagal", String.valueOf(t));
                }
            });
        }
        return true;
    }


}
