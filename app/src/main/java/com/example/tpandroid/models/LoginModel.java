package com.example.tpandroid.models;

import android.content.Context;

import com.example.tpandroid.interfaces.LoginInterface;

public class LoginModel implements LoginInterface.Model {

    private LoginInterface.Presenter presenter;

    public LoginModel(LoginInterface.Presenter loginPresenter) {
        presenter = loginPresenter;
    }

    @Override
    public void login(LoginInterface request, String uri, Context context) {

    }
}
