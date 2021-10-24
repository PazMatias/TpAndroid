package com.example.tpandroid.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tpandroid.R;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailTextInput;
    EditText passwordTextInput;
    Button loginButton;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        emailTextInput = findViewById(R.id.editTextLoginEmail);
        passwordTextInput = findViewById(R.id.editTextLoginPassword);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent ;

        switch (v.getId()){
            case R.id.loginButton:
                if(validateForm()){
                    Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Apretaste el boton de login capo", Toast.LENGTH_LONG).show();
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
    protected void onDestroy() {
        super.onDestroy();
    }
}