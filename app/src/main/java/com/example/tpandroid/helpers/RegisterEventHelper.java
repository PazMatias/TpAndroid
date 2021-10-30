package com.example.tpandroid.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tpandroid.retrofit.requests.RegisterEventRequest;
import com.example.tpandroid.retrofit.responses.LoginResponse;
import com.example.tpandroid.retrofit.responses.RegisterEventResponse;
import com.example.tpandroid.services.SoaService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterEventHelper extends AsyncTask<String, Integer, String> {



    @Override
    protected String doInBackground(String... strings) {

        TokenSingleton tokenSingleton = TokenSingleton.getTokenSingleton();

        RegisterEventRequest registerEventRequest = new RegisterEventRequest();
        registerEventRequest.setEnv("TEST");
        registerEventRequest.setTypeEvents("Login");
        registerEventRequest.setDescription("El usuario se logeo");


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(strings[0])
                .build();

        SoaService soaService = retrofit.create(SoaService.class);

        Call<RegisterEventResponse> call = soaService.registerEvents("Bearer " + tokenSingleton.token,registerEventRequest);
        try {
            Response<RegisterEventResponse> response = call.execute();
            RegisterEventResponse registerEventResponse = new RegisterEventResponse();
            registerEventResponse.setEnv(response.body().getEnv());
            registerEventResponse.setSuccess(response.body().getSuccess());
            registerEventResponse.setEvent(response.body().getData());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private RegisterEventResponse doRegister(SoaService soaService){

    }
}
