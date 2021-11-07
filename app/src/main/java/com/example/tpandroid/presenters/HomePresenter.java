package com.example.tpandroid.presenters;

import static android.content.Context.SENSOR_SERVICE;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.tpandroid.Views.BusDetailActivity;
import com.example.tpandroid.Views.HomeActivity;

public class HomePresenter implements SensorEventListener {


    private SensorManager mSensor;
    private HomeActivity homeView;


    private float x, y, z;

    private final static int ACC = 20;
    private long ultimaActualizacion;

    public HomePresenter(HomeActivity view) {

        homeView = view;

        mSensor = (SensorManager) homeView.getSystemService(SENSOR_SERVICE);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;

        long tiempoActual = System.currentTimeMillis();

        if ((tiempoActual - ultimaActualizacion) > 1000) {
            ultimaActualizacion = tiempoActual;
            x = values[0];
            y = values[1];
            z = values[2];
            float velocidadCalculada = (float) Math.sqrt((double) (x * x + y * y + z * z));

            if (velocidadCalculada > ACC) {

                homeView.changeToBusDetail();
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void registerSensor() {
        mSensor.registerListener(this, mSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

    }

    public void unregisterSensor() {

        mSensor.unregisterListener(this);
    }
}
