package com.example.tpandroid.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tpandroid.R;
import com.example.tpandroid.helpers.RegisterEventHelper;
import com.example.tpandroid.helpers.TokenSingleton;
import com.example.tpandroid.interfaces.LoginInterface;
import com.example.tpandroid.interfaces.RegisterInterface;
import com.example.tpandroid.presenters.LoginPresenter;
import com.example.tpandroid.retrofit.requests.LoginRequest;
import com.example.tpandroid.retrofit.responses.LoginResponse;
import com.example.tpandroid.retrofit.responses.RegisterResponse;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginInterface.View {

    EditText emailTextInput;
    EditText passwordTextInput;
    Button loginButton;
    Button registerButton;
    private static String TAG = RegisterActivity.class.getName();
    private LoginInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        emailTextInput = findViewById(R.id.editTextLoginEmail);
        passwordTextInput = findViewById(R.id.editTextLoginPassword);

        presenter = new LoginPresenter(this);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent ;

        switch (v.getId()){
            case R.id.loginButton:
                if(!validateForm()){
                    LoginRequest loginRequest= new LoginRequest();
                    loginRequest.setEmail(emailTextInput.getText().toString());
                    loginRequest.setPassword(passwordTextInput.getText().toString());
                    loginButton.setEnabled(false);
                    registerButton.setEnabled(false);
                    presenter.login(loginRequest,getString(R.string.url_register_api),this);
                }
                break;
            case R.id.registerButton:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
        }
    }


    private boolean validateForm(){
        validateEmail();
        validatePassword();
        return hasErrors(emailTextInput) || hasErrors(passwordTextInput);
    }

    private void validateEmail(){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if (emailTextInput.length() == 0){
            emailTextInput.setError("El campo es obligatorio");
            return;
        }
        if(!pattern.matcher(emailTextInput.getText().toString()).matches())
            emailTextInput.setError("El mail no tiene un formato valido");
    }

    private void validatePassword(){
        if(passwordTextInput.length() == 0){
            passwordTextInput.setError("El campo es obligatorio");
            return;
        }
        if(passwordTextInput.length() < 8)
            passwordTextInput.setError("Debe tener minimo 8 caracteres");
    }

    private boolean hasErrors(EditText editText){
        return editText.getError() != null;
    }

    @Override
    public void showResult(LoginResponse result) {
        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
        if (result.getSuccess()) {
            Toast.makeText(LoginActivity.this, "Logeo Exitoso!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Logeo Exitoso!");
             Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
             intent.putExtra("email",emailTextInput.getText().toString());
             startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this,result.getMsg() , Toast.LENGTH_LONG).show();

        }
    }


}