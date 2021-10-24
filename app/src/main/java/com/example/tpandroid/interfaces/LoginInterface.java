package com.example.tpandroid.interfaces;

import android.content.Context;

import com.example.tpandroid.retrofit.requests.LoginRequest;
import com.example.tpandroid.retrofit.responses.LoginResponse;


public interface LoginInterface {

    interface View {
        void showResult(LoginResponse result);

    }
    interface Presenter {
        void showResult(LoginResponse result);
        void login(LoginRequest request, String uri, Context context);
    }
    interface Model {
        void login(LoginInterface request, String uri,Context context);
    }
}
