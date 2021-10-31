package com.example.tpandroid.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.BoringLayout;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.example.tpandroid.R;
import com.example.tpandroid.Views.Fragments.LinesFragment;
import com.example.tpandroid.Views.Fragments.MetricsFragment;
import com.example.tpandroid.Views.Fragments.TipsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements SensorEventListener, CompoundButton.OnCheckedChangeListener , LinesFragment.RegisterSensor {

    private final static float ACC = 12;

    public String email;

    private MediaPlayer mPlayer;
    private SensorManager mSensor;
    private Vibrator mVibrator;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        email = getIntent().getExtras().getString("email");
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        mSensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        mPlayer = MediaPlayer.create(this,R.raw.minecrafteating);

        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mPlayer.setVolume(1.0f, 1.0f);
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        Fragment fragment = new MetricsFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.tabsContainer,fragment).commit();
    }
    @Override public void onBackPressed() { moveTaskToBack(true); }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null ;
            switch(item.getItemId())
            {
                case R.id.metrics_page:
                    Bundle bundle = new Bundle();
                    bundle.putString("email",email);
                    fragment = new MetricsFragment();
                    fragment.setArguments(bundle);
                    break;
                case R.id.lines_page:
                    fragment = new LinesFragment();
                    break;
                case R.id.tips_page:
                    fragment = new TipsFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.tabsContainer,fragment).commit();
            return true;
        }
    };

    @Override
    protected void onStop()
    {
        unregisterSenser();
        if (mPlayer != null)
        {
            mPlayer.release();
        }
        super.onStop();
    }

    public void registerSenser()
    {
        boolean done;
        done = mSensor.registerListener((SensorEventListener) this, mSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

        Log.i("sensor", "register");
    }

    public void unregisterSenser()
    {
        mSensor.unregisterListener((SensorEventListener) this);
        Log.i("sensor", "unregister");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        int sensorType = event.sensor.getType();

        float[] values = event.values;

        if (sensorType == Sensor.TYPE_ACCELEROMETER)
        {
            if ((Math.abs(values[0]) > ACC || Math.abs(values[1]) > ACC || Math.abs(values[2]) > ACC))
            {
                Log.i("sensor", "running");
                mPlayer.start();
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}