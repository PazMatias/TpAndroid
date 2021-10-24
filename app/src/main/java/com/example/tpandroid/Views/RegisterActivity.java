package com.example.tpandroid.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tpandroid.R;
import com.example.tpandroid.helpers.ConnectionHelper;
import com.example.tpandroid.retrofit.requests.RegisterRequest;
import com.example.tpandroid.retrofit.responses.RegisterResponse;
import com.example.tpandroid.services.SoaService;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText nameTextInput;
    EditText surnameTextInput;
    EditText dniTextInput;
    EditText emailTextInput;
    EditText passwordTextInput;
    private static String TAG = RegisterActivity.class.getName();

     Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.registerButton2);
        nameTextInput = findViewById(R.id.nameTextInput);
        surnameTextInput = findViewById(R.id.surnameTextInput);
        dniTextInput = findViewById(R.id.dniTextInput);
        emailTextInput = findViewById(R.id.emailTextInput);
        passwordTextInput = findViewById(R.id.passwordTextInput);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        RegisterRequest request = new RegisterRequest();
        if(validateForm()){
            Toast.makeText(RegisterActivity.this, "Datos incorrectos", Toast.LENGTH_LONG).show();
        }
        else{

        request.setEnv("TEST");
        request.setName(nameTextInput.getText().toString());
        request.setLastname(surnameTextInput.getText().toString());
        request.setDni(Long.parseLong(dniTextInput.getText().toString()));
        request.setEmail(emailTextInput.getText().toString());
        request.setPassword(passwordTextInput.getText().toString());
        request.setCommission(3900L);
        request.setGroup(13);



       Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://so-unlam.net.ar/api/")
                .build();
        SoaService soaService = retrofit.create(SoaService.class);

        Call<RegisterResponse> call = soaService.register(request);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()){

                    Toast.makeText(RegisterActivity.this, "SALIO TODO BIEN CAPO", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                Toast.makeText(RegisterActivity.this, "CACHAPUM", Toast.LENGTH_LONG).show();
                Log.e(TAG,t.getMessage());
            }
        });
        }
    }

    private boolean validateForm(){
        validateName();
        validateEmail();
        validateSurname();
        validatePassword();
        validateDni();
        return hasErrors(nameTextInput) || hasErrors(surnameTextInput) || hasErrors(dniTextInput) || hasErrors(emailTextInput) || hasErrors(passwordTextInput);
    }

    private void validateName(){
        if (nameTextInput.length() == 0)
            nameTextInput.setError("El campo es obligatorio");
    }

    private void validateSurname(){
        if (surnameTextInput.length() == 0)
            surnameTextInput.setError("El campo es obligatorio");
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


    private void validateDni(){
        if (dniTextInput.length() == 0)
            dniTextInput.setError("El campo es obligatorio");
    }

    private void validatePassword(){
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if(passwordTextInput.length() == 0){
            passwordTextInput.setError("El campo es obligatorio");
            return;
        }
        if(passwordTextInput.length() < 8){
            passwordTextInput.setError("Debe tener minimo 8 caracteres");
            return;
        }
        pattern.matcher(passwordTextInput.getText().toString());
        if( pattern.matcher(passwordTextInput.getText().toString()).matches())
            passwordTextInput.setError("Debe ser alfanúmerico");

    }

    private boolean hasErrors(EditText editText){
        return editText.getError() != null;
    }

}