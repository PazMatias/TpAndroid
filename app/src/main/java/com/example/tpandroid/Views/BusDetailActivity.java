package com.example.tpandroid.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tpandroid.R;
import com.example.tpandroid.Utils.MetricsTables;
import com.example.tpandroid.helpers.ConnectionHelper;
import com.example.tpandroid.helpers.PreferencesHelper;
import com.example.tpandroid.helpers.RegisterEventHelper;
import com.example.tpandroid.presenters.BusDetailPresenter;
import com.example.tpandroid.services.AlertaService;

public class BusDetailActivity extends AppCompatActivity {

    private String email;
    private Button cancelarButton;

    private BusDetailPresenter presenter;

    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_detail);
        cancelarButton = findViewById(R.id.cancelButton);

        email = getIntent().getExtras().getString("email");
        Log.i("Save StopCount", email);
        String value = PreferencesHelper.LoadValue(this, MetricsTables.STOPCOUNT, this.email, "0");
        PreferencesHelper.Save(this, MetricsTables.STOPCOUNT, this.email, String.valueOf(Integer.parseInt(value) + 1));

        if (ConnectionHelper.isOnline(this)) {
            RegisterEventHelper hiloRegistraEvento = new RegisterEventHelper();
            hiloRegistraEvento.execute(getString(R.string.url_register_api), "Colectivo Parado", "Se agito el celular para parar un colectivo");
        } else
            Log.e("ERROR", "No se cuenta con internet para registrar el evento");


        presenter = new BusDetailPresenter(this);

        cancelarButton.setOnClickListener(cancelButtonListener);

        intent = new Intent(BusDetailActivity.this, AlertaService.class);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.registerSensor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (AlertaService.state){
            stopService(intent);
            presenter.timer.cancel();
        }
        presenter.unregisterSensor();
    }

    private View.OnClickListener cancelButtonListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cancelButton:
                    if (AlertaService.state) {
                        detenerMusica();
                    }
                    changeToHomeActivity();
                    break;

                default:
                    Log.e("Error Sensor Proximidad", "Error en el Listener del boton cancelar");
            }
        }
    };

    public void detenerMusica() {
        stopService(intent);
        presenter.timer.cancel();
    }

    @Override
    public void onBackPressed()
    {
        changeToHomeActivity();
    }
    public void changeToHomeActivity(){
        Intent intent2 = new Intent(BusDetailActivity.this, HomeActivity.class);
        intent2.putExtra("email", email);
        startActivity(intent2);
    }


}