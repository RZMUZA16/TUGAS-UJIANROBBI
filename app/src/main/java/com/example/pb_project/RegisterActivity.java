package com.example.pb_project;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;

import com.google.firebase.firestore.auth.User;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class RegisterActivity extends AppCompatActivity {
    private EditText etFullName, etEmail, etPassword, etBirthDate, etLocation;
    private RadioGroup rgGender;
    private Button btnRegister;
    private Date selectedBirthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // View initialization
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etBirthDate = findViewById(R.id.etBirthDate);
        etLocation = findViewById(R.id.etLocation);
        rgGender = findViewById(R.id.rgGender);
        btnRegister = findViewById(R.id.btnRegister);

        // Date picker setup
        etBirthDate.setOnClickListener(v -> showDatePicker());

        // Register button click
        btnRegister.setOnClickListener(v -> validateAndRegister());

        // Login text click
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    selectedBirthDate = calendar.getTime();
                    etBirthDate.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                            .format(selectedBirthDate));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

    private void validateAndRegister() {
        // Input validation
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        // Gender selection
        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        String gender = "";
        if (selectedGenderId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedGenderId);
            gender = selectedRadioButton.getText().toString();
        }

        // Validation checks
        if (fullName.isEmpty()) {
            etFullName.setError("Full name required");
            return;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid email");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            return;
        }

        if (selectedBirthDate == null) {
            etBirthDate.setError("Birth date required");
            return;
        }

        if (gender.isEmpty()) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if (location.isEmpty()) {
            etLocation.setError("Location required");
            return;
        }

        // Create user object
        User newUser = new User(
                fullName,
                email,
                password,
                selectedBirthDate,
                gender,
                location
        );

        // Registration process
        showProgress();
        new Handler().postDelayed(() -> {
            hideProgress();
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 2000);
    }

    private void showProgress() {
        // Implement loading dialog
        // Example: ProgressDialog.show(this, "Loading", "Registering...", true);
    }

    private void hideProgress() {
        // Hide loading dialog
        // Example: progressDialog.dismiss();
    }
}