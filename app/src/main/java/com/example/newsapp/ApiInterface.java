package com.example.newsapp;

import com.example.newsapp.Model.Headines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("top-headlines")
    Call<Headines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<Headines> getSpecificData(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );



}