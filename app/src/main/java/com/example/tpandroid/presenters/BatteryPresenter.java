package com.example.tpandroid.presenters;

import android.content.Context;

import com.example.tpandroid.interfaces.BatteryInterface;
import com.example.tpandroid.models.BatteryModel;

public class BatteryPresenter implements BatteryInterface.Presenter {

    private BatteryInterface.View batteryView;
    private BatteryInterface.Model batteryModel;

    public BatteryPresenter(BatteryInterface.View view){
        batteryView=view;
        batteryModel = new BatteryModel(this);
    }

    @Override
    public void showResult(String result) {
        if (batteryView != null)
            batteryView.showResult(result);
    }

    @Override
    public void calculateBattery(Context context) {
        if (batteryModel != null)
            batteryModel.calculateBattery(context);

    }
}
