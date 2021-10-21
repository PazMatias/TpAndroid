package com.example.tpandroid.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tpandroid.PatternActivity;
import com.example.tpandroid.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent ;

        switch (v.getId()){
            case R.id.loginButton:
                Toast.makeText(LoginActivity.this, "Apretaste el boton de login capo", Toast.LENGTH_LONG).show();
                break;
            case R.id.registerButton:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
        }
    }
}