package com.example.tpandroid.presenters;

import android.content.Context;

import com.example.tpandroid.Views.LoginActivity;
import com.example.tpandroid.interfaces.LoginInterface;
import com.example.tpandroid.models.LoginModel;
import com.example.tpandroid.retrofit.requests.LoginRequest;
import com.example.tpandroid.retrofit.responses.LoginResponse;

public class LoginPresenter implements LoginInterface.Presenter {

    private LoginInterface.View view;
    private LoginInterface.Model model;

    public LoginPresenter(LoginInterface.View view) {
        this.view = view;
        model = new LoginModel(this);
    }

    @Override
    public void showResult(LoginResponse result) {
        if (view != null)
            view.showResult(result);

    }

    @Override
    public void login(LoginRequest request, String uri, Context context) {
        if (model != null)
            model.login(request, uri,context);
    }
}
