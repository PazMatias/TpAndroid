package com.example.tpandroid.models;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tpandroid.R;
import com.example.tpandroid.Utils.MetricsTables;
import com.example.tpandroid.Views.RegisterActivity;
import com.example.tpandroid.helpers.ConnectionHelper;
import com.example.tpandroid.helpers.RegisterEventHelper;
import com.example.tpandroid.helpers.TokenSingleton;
import com.example.tpandroid.helpers.PreferencesHelper;
import com.example.tpandroid.interfaces.LoginInterface;
import com.example.tpandroid.retrofit.requests.LoginRequest;
import com.example.tpandroid.retrofit.responses.LoginResponse;
import com.example.tpandroid.services.SoaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginModel implements LoginInterface.Model {

    private LoginInterface.Presenter presenter;
    private static String TAG = RegisterActivity.class.getName();

    private LoginResponse loginResponse;

    public LoginModel(LoginInterface.Presenter loginPresenter) {
        presenter = loginPresenter;
        loginResponse = new LoginResponse();
    }

    @Override
    public void login(LoginRequest request, String uri, Context context) {

        if (ConnectionHelper.isOnline(context)) {

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(uri)
                    .build();


            SoaService soaService = retrofit.create(SoaService.class);

            Call<LoginResponse> call = soaService.login(request);
            call.enqueue(new Callback<LoginResponse>() {
                @Override

                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if (response.isSuccessful()) {

                        loginResponse.setSuccess(response.body().getSuccess());
                        loginResponse.setToken(response.body().getToken());
                        loginResponse.setToken_refresh(response.body().getToken_refresh());
                        String value = PreferencesHelper.LoadValue(context, MetricsTables.LOGINCOUNT,request.getEmail().toString(),"1");
                        PreferencesHelper.Save(context,MetricsTables.LOGINCOUNT,request.getEmail().toString(),value.equals("1")?String.valueOf(Integer.parseInt(value) + 1):value);

                        TokenSingleton tokenSingleton = TokenSingleton.getInstance(response.body().getToken(),response.body().getToken_refresh());

                        RegisterEventHelper hiloRegistraEvento = new RegisterEventHelper();
                        hiloRegistraEvento.execute(uri,"Probando Log","Se ejecuto el hilo que logea jeje");
                    }
                    else{

                        loginResponse.setSuccess(false);
                        loginResponse.setMsg("Logeo Fallido");

                        Log.e(TAG,"Logeo Fallido" );
                    }

                    presenter.showResult(loginResponse);
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {


                    loginResponse.setSuccess(false);
                    loginResponse.setMsg("Logeo Fallido");

                    Log.e(TAG, t.getMessage());
                    presenter.showResult(loginResponse);

                }
            });

        } else {
            loginResponse.setSuccess(false);
            loginResponse.setMsg("No se cuenta con conexion a internet");

            presenter.showResult(loginResponse);
        }

    }
}
