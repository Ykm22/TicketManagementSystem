package com.example.ticketmanagementsystem_android.activities.app_connection_activities;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketmanagementsystem_android.R;
import com.example.ticketmanagementsystem_android.api_clients.UserApiClient;
import com.example.ticketmanagementsystem_android.callbacks.RegisterCallback;
import com.example.ticketmanagementsystem_android.models.RegisterResponse;
import com.example.ticketmanagementsystem_android.services.UserService;

public class RegistrationActivity extends AppCompatActivity implements RegisterCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button createAccountButton = findViewById(R.id.buttonCreateAccount);
        createAccountButton.setOnClickListener(v -> {
            // Get the data from the EditText fields
            EditText emailEditText = findViewById(R.id.editTextEmail);
            EditText passwordEditText = findViewById(R.id.editTextPassword);
            EditText retypePasswordEditText = findViewById(R.id.editTextRetypePassword);
            EditText ageEditText = findViewById(R.id.editTextAge);
            EditText sexEditText = findViewById(R.id.editTextSex);

            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String retypePassword = retypePasswordEditText.getText().toString().trim();
            String ageString = ageEditText.getText().toString().trim();
            String sex = sexEditText.getText().toString().trim();

            // Perform validation checks
            if (!isValidEmail(email)) {
                Toast.makeText(RegistrationActivity.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 7 || !containsNumber(password)) {
                Toast.makeText(RegistrationActivity.this, "Password must be at least 7 characters long and contain at least 1 number.", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(retypePassword)) {
                Toast.makeText(RegistrationActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            } else if (ageString.isEmpty() || Integer.parseInt(ageString) < 15) {
                Toast.makeText(RegistrationActivity.this, "Age must be 15 years or older.", Toast.LENGTH_SHORT).show();
            } else if (!sex.equalsIgnoreCase("m") && !sex.equalsIgnoreCase("f")) {
                Toast.makeText(RegistrationActivity.this, "Sex must be 'M' or 'F'.", Toast.LENGTH_SHORT).show();
            } else {
                // All data is valid, create the account or perform further actions
                // For now, just display a success message in a Toast.
                String successMessage = "Registration successful!\n" +
                        "Email: " + email + "\n" +
                        "Password: " + password + "\n" +
                        "Age: " + ageString + "\n" +
                        "Sex: " + sex;
                UserApiClient.registerUser(email, password, sex, Integer.parseInt(ageString), this);
//                Toast.makeText(RegistrationActivity.this, successMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean containsNumber(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRegistrationSuccess(RegisterResponse registerResponse) {
        Toast.makeText(this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRegistrationFailure(RegisterResponse registerResponse) {
        Toast.makeText(this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
