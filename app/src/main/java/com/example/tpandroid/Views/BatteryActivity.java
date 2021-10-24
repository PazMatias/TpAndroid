package com.example.tpandroid.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.example.tpandroid.Views.PatternActivity;
import com.example.tpandroid.R;

import java.text.DecimalFormat;

public class BatteryActivity extends AppCompatActivity {

    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, iFilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        int batteryPct = (level * 100 / scale);
        DecimalFormat formatPercentageBattery = new DecimalFormat("#.");
        TextView tv = findViewById(R.id.textViewPercentageBattery);
        String msgBattery = String.valueOf(batteryPct)+ "%";
        tv.setText(msgBattery);

        (new Handler()).postDelayed(this::afterWait, 2000);
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
}