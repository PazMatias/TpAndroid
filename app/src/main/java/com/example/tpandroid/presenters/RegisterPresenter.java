package com.example.tpandroid.presenters;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tpandroid.R;
import com.example.tpandroid.Views.RegisterActivity;
import com.example.tpandroid.interfaces.RegisterInterface;
import com.example.tpandroid.models.RegisterModel;
import com.example.tpandroid.retrofit.requests.RegisterRequest;
import com.example.tpandroid.retrofit.responses.RegisterResponse;
import com.example.tpandroid.services.SoaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterPresenter implements RegisterInterface.Presenter {

    private RegisterInterface.View view;
    private RegisterInterface.Model model;

    public RegisterPresenter(RegisterInterface.View view) {
        this.view = view;
        model = new RegisterModel(this);
    }

    @Override
    public void showResult(RegisterResponse result) {
        if (view != null)
            view.showResult(result);
    }

    @Override
    public void register(RegisterRequest request, String uri, Context context) {
        if (model != null)
            model.register(request, uri,context);
    }
}
