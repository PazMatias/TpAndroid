package com.example.tpandroid.services;

import com.example.tpandroid.retrofit.requests.LoginRequest;
import com.example.tpandroid.retrofit.requests.RegisterEventRequest;
import com.example.tpandroid.retrofit.responses.LoginResponse;
import com.example.tpandroid.retrofit.responses.RefreshTokenResponse;
import com.example.tpandroid.retrofit.responses.RegisterEventResponse;
import com.example.tpandroid.retrofit.responses.RegisterResponse;
import com.example.tpandroid.retrofit.requests.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SoaService {



    @POST("api/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);
    @POST("api/login")
    Call<LoginResponse> login(@Body LoginRequest request);
    @PUT("api/refresh")
    Call<RefreshTokenResponse> refreshToken();
    @POST("api/event")
    Call<RegisterEventResponse> registerEvents(@Body RegisterEventRequest request);
}
