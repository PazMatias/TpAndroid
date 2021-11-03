package com.example.tpandroid.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.example.tpandroid.Views.PatternActivity;
import com.example.tpandroid.R;
import com.example.tpandroid.interfaces.BatteryInterface;
import com.example.tpandroid.presenters.BatteryPresenter;

import java.text.DecimalFormat;

public class BatteryActivity extends AppCompatActivity implements BatteryInterface.View {

    private BatteryInterface.Presenter batteryPresenter;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        batteryPresenter = new BatteryPresenter(this);
        tv = findViewById(R.id.textViewPercentageBattery);

        batteryPresenter.calculateBattery(this);
    }

    private void afterWait() {
        Intent intent = new Intent(BatteryActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        super.finish();
    }

    @Override
    public void showResult(String msgBattery) {
        tv.setText(msgBattery);
        (new Handler()).postDelayed(this::afterWait, 2000);

    }
}