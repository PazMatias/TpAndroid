package com.example.tpandroid.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tpandroid.R;
import com.example.tpandroid.helpers.ConnectionHelper;
import com.example.tpandroid.retrofit.requests.RegisterRequest;
import com.example.tpandroid.retrofit.responses.RegisterResponse;
import com.example.tpandroid.services.SoaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText nombreTextInput;
    EditText apellidoTextInput;
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
        nombreTextInput = findViewById(R.id.nombreTextInput);
        apellidoTextInput = findViewById(R.id.apellidoTextInput);
        dniTextInput = findViewById(R.id.dniTextInput);
        emailTextInput = findViewById(R.id.emailTextInput);
        passwordTextInput = findViewById(R.id.passwordTextInput);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        RegisterRequest request = new RegisterRequest();

        request.setEnv("TEST");
        request.setName(nombreTextInput.getText().toString());
        request.setLastname(apellidoTextInput.getText().toString());
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