package com.example.tpandroid.interfaces;

import android.content.Context;

import com.example.tpandroid.retrofit.requests.RegisterRequest;
import com.example.tpandroid.retrofit.responses.RegisterResponse;

public interface RegisterInterface {

    interface View {
        void showResult(RegisterResponse result);
    }
    interface Presenter {
        void showResult(RegisterResponse result);
        void register(RegisterRequest request, String uri, Context context);
    }
    interface Model {
        void register(RegisterRequest request, String uri,Context context);
    }
}
