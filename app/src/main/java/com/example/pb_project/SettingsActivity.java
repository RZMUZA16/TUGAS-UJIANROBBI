package com.example.pb_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();


        findViewById(R.id.toolbar).setOnClickListener(v -> onBackPressed());


        SwitchMaterial notificationSwitch = findViewById(R.id.notification_switch);
        View accountSettings = findViewById(R.id.account_settings);
        View btnLogout = findViewById(R.id.btn_logout);


        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

        });

        accountSettings.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(SettingsActivity.this, AuthActivity.class));
            finishAffinity();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}