package com.example.tpandroid.services;

import com.example.tpandroid.retrofit.SoaRequest;
import com.example.tpandroid.retrofit.SoaResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SoaService {



    @POST("api/register")
    Call<SoaResponse> register(@Body SoaRequest request);
}
