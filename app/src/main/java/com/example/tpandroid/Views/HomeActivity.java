package com.example.tpandroid.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.tpandroid.R;
import com.example.tpandroid.Utils.MetricsTables;
import com.example.tpandroid.Views.Fragments.LinesFragment;
import com.example.tpandroid.Views.Fragments.MetricsFragment;
import com.example.tpandroid.Views.Fragments.TipsFragment;
import com.example.tpandroid.helpers.RegisterEventHelper;
import com.example.tpandroid.helpers.PreferencesHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.seismic.ShakeDetector;

public class HomeActivity extends AppCompatActivity implements SensorEventListener, CompoundButton.OnCheckedChangeListener, LinesFragment.RegisterSensor {

    private final static int ACC = 15000;

    public String email;
    private int counter = 0;
    private SensorManager mSensor;
    private BottomNavigationView bottomNavigationView;

    private float  x, y, z;

    private float mLastX=-1.0f, mLastY=-1.0f, mLastZ=-1.0f;
    private int mShakeCount = 0;
    private long lastUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        email = getIntent().getExtras().getString("email");
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        mSensor = (SensorManager) getSystemService(SENSOR_SERVICE);



        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        bottomNavigationView.setOnNavigationItemReselectedListener(bottomReselectedNavMethod);
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        Fragment fragment = new MetricsFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.tabsContainer, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.metrics_page:
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
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

            getSupportFragmentManager().beginTransaction().replace(R.id.tabsContainer, fragment).commit();
            return true;
        }
    };

    private BottomNavigationView.OnNavigationItemReselectedListener bottomReselectedNavMethod = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {

        }
    };

    @Override
    protected void onStop() {
        unregisterSenser();
        super.onStop();
    }

    @Override
    public void registerSenser() {

        mSensor.registerListener((SensorEventListener) this, mSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

        Log.i("sensor", "register");
    }

    @Override
    public void unregisterSenser() {
        mSensor.unregisterListener((SensorEventListener) this);
        Log.i("sensor", "unregister");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values;

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                x = values[0];
                y = values[1];
                z = values[2];
                float speed = Math.abs(x + y + z - mLastX - mLastY - mLastZ) / diffTime * 10000;

                mLastX = x;
                mLastY = y;
                mLastZ = z;
                Toast.makeText(this, "Speed " + speed, Toast.LENGTH_SHORT).show();
                if (speed > ACC) {

                    Log.i("sensor", "running");
                    Intent intent = new Intent(this, BusDetailActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
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