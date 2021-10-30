package com.example.tpandroid.models;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.example.tpandroid.interfaces.BatteryInterface;
import com.example.tpandroid.presenters.BatteryPresenter;

public class BatteryModel implements BatteryInterface.Model {

    private BatteryInterface.Presenter batteryPresenter;
    private int level;
    private int scale;
    private int batteryPct;
    public BatteryModel(BatteryInterface.Presenter batteryPresenter) {
        this.batteryPresenter = batteryPresenter;
    }

    @Override
    public void calculateBattery(Context context) {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);
        level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        batteryPct = (level * 100 / scale);

        batteryPresenter.showResult(batteryPct + "%");
    }
}
