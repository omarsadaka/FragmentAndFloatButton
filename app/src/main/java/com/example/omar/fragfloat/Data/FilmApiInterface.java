package com.example.omar.fragfloat.Data;

import com.example.omar.fragfloat.Model.Films;
import com.example.omar.fragfloat.Model.Movies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Omar on 12/18/2018.
 */

public interface FilmApiInterface {
    @GET("alphabet?letter=A&api_key=903158a0e8a012a94df73c8281ce1c17")
    Call<Films> getcontacts();

}
