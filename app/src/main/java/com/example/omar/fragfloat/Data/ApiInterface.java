package com.example.omar.fragfloat.Data;

import com.example.omar.fragfloat.Model.Movies;

import java.util.List;

import retrofit2.http.GET;

/**
 * Created by OMAR on 10/17/2018.
 */

public interface ApiInterface {
    @GET("data/organizations.json")
    retrofit2.Call<List<Movies>> getcontacts();
}
