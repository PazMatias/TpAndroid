package com.example.tpandroid.interfaces;

import android.content.Context;


public interface BatteryInterface {

    interface View {
        void showResult(String msgBattery);
    }
    interface Presenter {
        void showResult(String result);
        void calculateBattery(Context context);
    }
    interface Model {
        void calculateBattery(Context context);
    }
}
