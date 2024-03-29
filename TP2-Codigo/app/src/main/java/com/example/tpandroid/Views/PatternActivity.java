package com.example.tpandroid.Views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;


import com.example.tpandroid.R;
import com.example.tpandroid.databinding.PatternActivityBinding;
import java.util.List;

public class PatternActivity extends AppCompatActivity {

    private PatternActivityBinding binding;
    PatternLockView patternLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        binding = PatternActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        patternLockView = findViewById(R.id.pattern_lock_view);

        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List progressPattern) {

            }

            @Override
            public void onComplete(List pattern) {

                Log.d(getClass().getName(), "Pattern complete: " +
                        PatternLockUtils.patternToString(patternLockView, pattern));
                if (PatternLockUtils.patternToString(patternLockView, pattern).equalsIgnoreCase("123")) {
                    patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                    Intent intent = new Intent(PatternActivity.this, BatteryActivity.class);
                    startActivity(intent);
                } else {
                    patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    Toast.makeText(PatternActivity.this, "Patron incorrecto", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCleared() {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        super.finish();
    }


}