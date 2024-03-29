package com.example.tpandroid.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tpandroid.R;
import com.example.tpandroid.Utils.MetricsTables;
import com.example.tpandroid.Utils.SonarAlertaColectivo;
import com.example.tpandroid.Utils.TimerAlarma;
import com.example.tpandroid.helpers.ConnectionHelper;
import com.example.tpandroid.helpers.PreferencesHelper;
import com.example.tpandroid.helpers.RegisterEventHelper;
import com.example.tpandroid.retrofit.requests.LoginRequest;

import java.util.Timer;

public class BusDetailActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {
    SensorManager mSensorManager;
    Sensor mProximity;
    private String email;
    private static final float SENSOR_SENSITIVITY = 0.5f;
    private Vibrator mVibrator;
    private MediaPlayer mPlayer;
    private Button cancelarButton;

    private Timer timer;
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);
        cancelarButton = findViewById(R.id.cancelButton);

        email = getIntent().getExtras().getString("email");
        Log.i("Save StopCount", email);
        String value = PreferencesHelper.LoadValue(this, MetricsTables.STOPCOUNT, this.email, "0");
        PreferencesHelper.Save(this, MetricsTables.STOPCOUNT, this.email, String.valueOf(Integer.parseInt(value) + 1));

        if (ConnectionHelper.isOnline(this)) {
            RegisterEventHelper hiloRegistraEvento = new RegisterEventHelper();
            hiloRegistraEvento.execute(getString(R.string.url_register_api), "Colectivo Parado", "Se agito el celular para parar un colectivo");
        }
        else
            Log.e("ERROR","No se cuenta con internet para registrar el evento");


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer = MediaPlayer.create(this, R.raw.get_over_here);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mPlayer.setVolume(1.0f, 1.0f);
            }
        });
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.release();
        }
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                t = new Thread(new SonarAlertaColectivo(mVibrator, mPlayer));
                timer = new Timer();
                timer.schedule(new TimerAlarma(t, timer, cancelarButton), 15 * 1000);
                t.start();
                mSensorManager.unregisterListener(this);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_button:

                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                break;
        }
    }


}