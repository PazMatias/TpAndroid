package com.example.tpandroid;

import android.content.Intent;
import android.os.Bundle;


import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Toast;

import com.example.tpandroid.databinding.ActivityMainBinding;
import com.example.tpandroid.Views.LoginActivity;

import java.util.List;

public class PatternActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    PatternLockView patternLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
                    Intent intent = new Intent(PatternActivity.this, LoginActivity.class);
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
}