package com.example.tpandroid.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.example.tpandroid.R;
import com.example.tpandroid.Views.Fragments.LinesFragment;
import com.example.tpandroid.Views.Fragments.MetricsFragment;
import com.example.tpandroid.Views.Fragments.TipsFragment;
import com.example.tpandroid.presenters.HomePresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements  CompoundButton.OnCheckedChangeListener, LinesFragment.RegisterSensor {


    public String email;

    private BottomNavigationView bottomNavigationView;

    private HomePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        email = getIntent().getExtras().getString("email");
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        presenter = new HomePresenter(this);

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
        super.onStop();
        presenter.unregisterSensor();
    }

    @Override
    public void registerSenser() {

        presenter.registerSensor();
        Log.i("sensor", "register");
    }

    @Override
    public void unregisterSenser() {
        presenter.unregisterSensor();
        Log.i("sensor", "unregister");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }


    public void changeToBusDetail(){
        Log.i("sensor", "running");
        Intent intent = new Intent(this, BusDetailActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}