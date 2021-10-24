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
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SoaService {


    @Headers({"content-type: application/json"})
    @POST("api/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);
    @Headers({"content-type: application/json"})
    @POST("api/login")
    Call<LoginResponse> login(@Body LoginRequest request);
    @Headers({"content-type: application/json"})
    @PUT("api/refresh")
    Call<RefreshTokenResponse> refreshToken(@Header("Authorization") String authToken);
    @Headers({"content-type: application/json"})
    @POST("api/event")
    Call<RegisterEventResponse> registerEvents(@Header("Authorization") String authToken,@Body RegisterEventRequest request);
}
