package com.example.ticketmanagementsystem_android.activities.app_connection_activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketmanagementsystem_android.R;
import com.example.ticketmanagementsystem_android.activities.customer_activities.EventsDisplayActivity;
import com.example.ticketmanagementsystem_android.api_clients.UserApiClient;
import com.example.ticketmanagementsystem_android.callbacks.LoginCallback;
import com.example.ticketmanagementsystem_android.models.LoginResponse;

public class LoginActivity extends AppCompatActivity implements LoginCallback {
    private EditText editTextEmail;
    private EditText editTextPassword;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if(email.equals("") || password.equals("")){
                Toast.makeText(LoginActivity.this, "C'mon pal show some interest.", Toast.LENGTH_SHORT).show();
                return;
            }

            UserApiClient.loginUser(editTextEmail.getText().toString(), editTextPassword.getText().toString(), this);
        });

        TextView registerInsteadLink = findViewById(R.id.textViewRegisterInstead);
        registerInsteadLink.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onLoginSuccess(LoginResponse loginResponse) {
        Log.i("Login Success", loginResponse.getMessage());
        Intent intent = new Intent(this, EventsDisplayActivity.class);
        intent.putExtra("customerId", loginResponse.getMessage());
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(LoginResponse loginResponse) {
        Log.i("Login Failure", loginResponse.getMessage());
        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
