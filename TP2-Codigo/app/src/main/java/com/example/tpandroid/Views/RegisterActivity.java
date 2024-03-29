package com.example.tpandroid.Views;

import androidx.annotation.NonNull;
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
import com.example.tpandroid.Utils.Global;
import com.example.tpandroid.helpers.ConnectionHelper;
import com.example.tpandroid.interfaces.RegisterInterface;
import com.example.tpandroid.presenters.RegisterPresenter;
import com.example.tpandroid.retrofit.requests.RegisterRequest;
import com.example.tpandroid.retrofit.responses.RegisterResponse;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterInterface.View {

    private static String TAG = RegisterActivity.class.getName();

    Button registerButton;
    private EditText nameTextInput;
    private EditText surnameTextInput;
    private EditText dniTextInput;
    private EditText emailTextInput;
    private EditText passwordTextInput;
    private RegisterInterface.Presenter presenter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton = findViewById(R.id.registerButton2);

        nameTextInput = findViewById(R.id.nameTextInput);
        surnameTextInput = findViewById(R.id.surnameTextInput);
        dniTextInput = findViewById(R.id.dniTextInput);
        emailTextInput = findViewById(R.id.emailTextInput);
        passwordTextInput = findViewById(R.id.passwordTextInput);

        registerButton.setOnClickListener(this);

        presenter = new RegisterPresenter(this);
    }

    @Override
    public void onClick(View v) {

        RegisterRequest request = getRegisterRequest();
        if (request != null) {
            registerButton.setEnabled(false);
            presenter.register(request, getString(R.string.url_register_api), this);

        }
    }

    @NonNull
    private RegisterRequest getRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        if (validateForm()) {
            Toast.makeText(RegisterActivity.this, "Datos incorrectos", Toast.LENGTH_LONG).show();
            return null;
        } else {

            request.setEnv(Global.ENVIRONMENT);
            request.setName(nameTextInput.getText().toString());
            request.setLastname(surnameTextInput.getText().toString());
            request.setDni(Long.parseLong(dniTextInput.getText().toString()));
            request.setEmail(emailTextInput.getText().toString());
            request.setPassword(passwordTextInput.getText().toString());
            request.setCommission(3900L);
            request.setGroup(13);
            return request;
        }
    }

    @Override
    public void showResult(RegisterResponse result) {

        registerButton.setEnabled(true);
        if (result.getSuccess()) {
            Toast.makeText(RegisterActivity.this, "Registro Exitoso!", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Registro Exitoso!");
            intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(RegisterActivity.this, result.getMsg(), Toast.LENGTH_LONG).show();

        }
    }


    private boolean validateForm() {
        validateName();
        validateEmail();
        validateSurname();
        validatePassword();
        validateDni();
        return hasErrors(nameTextInput) || hasErrors(surnameTextInput) || hasErrors(dniTextInput) || hasErrors(emailTextInput) || hasErrors(passwordTextInput);
    }

    private void validateName() {
        if (nameTextInput.length() == 0)
            nameTextInput.setError("El campo es obligatorio");
    }

    private void validateSurname() {
        if (surnameTextInput.length() == 0)
            surnameTextInput.setError("El campo es obligatorio");
    }

    private void validateEmail() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if (emailTextInput.length() == 0) {
            emailTextInput.setError("El campo es obligatorio");
            return;
        }
        if (!pattern.matcher(emailTextInput.getText().toString()).matches())
            emailTextInput.setError("El mail no tiene un formato valido");
    }


    private void validateDni() {
        if (dniTextInput.length() == 0)
            dniTextInput.setError("El campo es obligatorio");
    }

    private void validatePassword() {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if (passwordTextInput.length() == 0) {
            passwordTextInput.setError("El campo es obligatorio");
            return;
        }
        if (passwordTextInput.length() < 8) {
            passwordTextInput.setError("Debe tener minimo 8 caracteres");
            return;
        }
        pattern.matcher(passwordTextInput.getText().toString());
        if (pattern.matcher(passwordTextInput.getText().toString()).matches())
            passwordTextInput.setError("Debe ser alfanúmerico");

    }

    private boolean hasErrors(EditText editText) {
        return editText.getError() != null;
    }

}