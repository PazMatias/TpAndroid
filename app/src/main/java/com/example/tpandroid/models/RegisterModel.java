package com.example.tpandroid.models;

import static androidx.core.content.res.TypedArrayUtils.getString;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tpandroid.R;
import com.example.tpandroid.Views.RegisterActivity;
import com.example.tpandroid.helpers.ConnectionHelper;
import com.example.tpandroid.interfaces.RegisterInterface;
import com.example.tpandroid.retrofit.requests.RegisterRequest;
import com.example.tpandroid.retrofit.responses.RegisterResponse;
import com.example.tpandroid.services.SoaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterModel implements RegisterInterface.Model {

    private RegisterInterface.Presenter presenter;
    private static String TAG = RegisterActivity.class.getName();

    private RegisterResponse responseModel;
    public RegisterModel(RegisterInterface.Presenter registerPresenter) {
        presenter = registerPresenter;
        responseModel = new RegisterResponse();
    }


    @Override
    public void register(RegisterRequest request, String uri, Context context) {

        if (ConnectionHelper.isOnline(context)){

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(uri)
                .build();
        SoaService soaService = retrofit.create(SoaService.class);

        Call<RegisterResponse> call = soaService.register(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override

            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()){

                responseModel.setEnv(response.body().getEnv());
                responseModel.setToken(response.body().getToken());
                responseModel.setToken_refresh(response.body().getToken_refresh());
                responseModel.setSuccess(response.body().getSuccess());
                }
                else{

                    responseModel.setSuccess(false);
                    responseModel.setEnv(request.getEnv());
                    responseModel.setMsg("Registro Fallido");

                    Log.e(TAG,response.message());
                }
                presenter.showResult(responseModel);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {


                responseModel.setSuccess(false);
                responseModel.setEnv(request.getEnv());
                responseModel.setMsg("Registro Fallido");

                Log.e(TAG,t.getMessage());
                presenter.showResult(responseModel);

            }
        });

        }
        else{
            responseModel.setSuccess(false);
            responseModel.setEnv(request.getEnv());
            responseModel.setMsg("No se cuenta con conexion a internet");

            presenter.showResult(responseModel);
        }

    }
}
