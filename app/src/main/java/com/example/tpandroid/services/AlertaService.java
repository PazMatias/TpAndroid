package com.example.tpandroid.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.tpandroid.R;

import java.util.Timer;
import java.util.TimerTask;

public class AlertaService extends Service {


     Vibrator mVibrator;
     MediaPlayer mPlayer;
     public static Boolean state = false;

    @Override
    public void onCreate() {
        Log.i("Service Alerta","ESTOY EN EL SERVICE GIL");
        mPlayer = MediaPlayer.create(this, R.raw.get_over_here);
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mPlayer.setVolume(1.0f, 1.0f);
            }
        });
        mPlayer.setLooping(true);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }
    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque)
    {
        state = true;
        mPlayer.start();
        mVibrator.vibrate(800);

        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {

        mPlayer.stop();
        mVibrator.cancel();
        state = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
