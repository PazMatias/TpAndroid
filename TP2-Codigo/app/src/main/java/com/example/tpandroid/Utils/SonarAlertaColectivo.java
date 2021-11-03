package com.example.tpandroid.Utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

public class SonarAlertaColectivo implements Runnable {


    private Vibrator mVibrator;
    private MediaPlayer mPlayer;

    public SonarAlertaColectivo(Vibrator mVibrator,MediaPlayer mPlayer){
        this.mVibrator=mVibrator;
        this.mPlayer=mPlayer;
    }
    @Override
    public void run() {


        try {
            while (!Thread.interrupted()) {
                mPlayer.start();
                mVibrator.vibrate(800);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            // log error
        }
    }
}