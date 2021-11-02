package com.example.tpandroid.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class TimerAlarma extends TimerTask {
    private Thread t;
    private Timer timer;

    public TimerAlarma(Thread t, Timer timer){
        this.t = t;
        this.timer = timer;
    }

    public void run() {
        if (t != null && t.isAlive()) {
            t.interrupt();
            timer.cancel();
        }
    }
}