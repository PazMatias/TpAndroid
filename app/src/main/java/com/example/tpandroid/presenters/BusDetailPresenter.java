package com.example.tpandroid.presenters;


import static android.content.Context.SENSOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;

import com.example.tpandroid.Views.BusDetailActivity;

public class BusDetailPresenter implements SensorEventListener {

    SensorManager mSensorManager;
    Sensor mProximity;
    private BusDetailActivity busDetailView;

    public CountDownTimer timer;
    private static final float SENSOR_SENSITIVITY = 4f;

    public BusDetailPresenter(BusDetailActivity busDetailView){

        this.busDetailView = busDetailView;

        mSensorManager = (SensorManager) this.busDetailView.getSystemService(SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    public void registerSensor() {

        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterSensor() {

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {

                busDetailView.startService(busDetailView.intent);
                mSensorManager.unregisterListener(this);
                timer = new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        busDetailView.detenerMusica();
                    }
                }.start();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
