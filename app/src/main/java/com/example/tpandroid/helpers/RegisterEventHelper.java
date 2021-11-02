package com.example.tpandroid.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tpandroid.Utils.Global;
import com.example.tpandroid.Views.RegisterActivity;
import com.example.tpandroid.retrofit.requests.RegisterEventRequest;
import com.example.tpandroid.retrofit.responses.LoginResponse;
import com.example.tpandroid.retrofit.responses.RefreshTokenResponse;
import com.example.tpandroid.retrofit.responses.RegisterEventResponse;
import com.example.tpandroid.services.SoaService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterEventHelper extends AsyncTask<String, Integer, String> {


    private static String TAG = RegisterEventHelper.class.getName();
    private final int EVENTCREATED = 201;
    private final int EVENTOK = 200;

    @Override
    protected synchronized String doInBackground(String... strings) {

        TokenSingleton tokenSingleton = TokenSingleton.getTokenSingleton();

        RegisterEventRequest registerEventRequest = new RegisterEventRequest();
        registerEventRequest.setEnv(Global.ENVIRONMENT);
        registerEventRequest.setTypeEvents(strings[1]);
        registerEventRequest.setDescription(strings[2]);


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(strings[0])
                .build();

        SoaService soaService = retrofit.create(SoaService.class);

        Call<RegisterEventResponse> callRegisterEvent = soaService.registerEvents("Bearer " + tokenSingleton.token, registerEventRequest);
        try {
            Response<RegisterEventResponse> response = callRegisterEvent.execute();
            if (response.code() == EVENTCREATED) {
                RegisterEventResponse registerEventResponse = new RegisterEventResponse();
                registerEventResponse.setEnv(response.body().getEnv());
                registerEventResponse.setSuccess(response.body().getSuccess());
                registerEventResponse.setEvent(response.body().getData());
                Log.i(TAG,"SE REGISTRO TODO BIEN" + strings[2] );
            } else {
                Log.e(TAG, "Token Vencido");
                Call<RefreshTokenResponse> callRefreshToken = soaService.refreshToken("Bearer" + tokenSingleton.token_refresh);
                Response<RefreshTokenResponse> responseToken = callRefreshToken.execute();
                if (responseToken.code() == EVENTOK) {
                    tokenSingleton.setToken(responseToken.body().getToken());
                } else {
                    Log.e(TAG, "fallo el refresco del toke");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
