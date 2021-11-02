package com.example.tpandroid.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.example.tpandroid.R;
import com.example.tpandroid.Utils.MetricsTables;
import com.example.tpandroid.helpers.PreferencesHelper;
import com.example.tpandroid.helpers.RegisterEventHelper;

public class BusDetailActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager mSensorManager;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);

        email = getIntent().getExtras().getString("email");
        String value = PreferencesHelper.LoadValue(this, MetricsTables.STOPCOUNT,this.email,"0");
        RegisterEventHelper hiloRegistraEvento = new RegisterEventHelper();
        hiloRegistraEvento.execute(getString(R.string.url_register_api),"Colectivo Parado","Se agito el celular para parar un colectivo");
        PreferencesHelper.Save(this, MetricsTables.STOPCOUNT,this.email,value);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Si detecta 0 lo represento
        if( event.values[0] == 0 ) {
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        }
        else
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}