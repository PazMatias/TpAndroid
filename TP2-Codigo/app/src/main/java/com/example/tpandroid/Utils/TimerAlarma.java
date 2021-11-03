package com.example.tpandroid.Utils;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.tpandroid.Views.HomeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class TimerAlarma extends TimerTask {
    private Thread t;
    private Timer timer;
    private Button cancelButton;
    public TimerAlarma(Thread t, Timer timer,Button button){
        this.t = t;
        this.timer = timer;
        cancelButton = button;
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    timer.cancel();
                    t.interrupt();

            }
        });
    }

    public void run() {
        if (t != null && t.isAlive()) {
            t.interrupt();
            timer.cancel();
        }
    }

}