package com.example.pb_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ListView menuListView;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        checkCurrentUser();

        // Initialize views
        Toolbar toolbar = findViewById(R.id.toolbar);
        menuListView = findViewById(R.id.user_menu);
        searchBar = findViewById(R.id.search_bar);

        // Setup toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("User Dashboard");
        }

        // Setup menu items
        String[] menuItems = {
                "Profile",
                "Financial Tracker",
                "Memo",
                "Settings",
                "Logout"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                menuItems
        );

        menuListView.setAdapter(adapter);

        // Handle menu item clicks
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Profile
                        startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
                        break;
                    case 1: // Financial Tracker
                        startActivity(new Intent(DashboardActivity.this, FinanceActivity.class));
                        break;
                    case 2: // Memo
                        startActivity(new Intent(DashboardActivity.this, MemoActivity.class));
                        break;
                    case 3: // Settings
                        startActivity(new Intent(DashboardActivity.this, SettingsActivity.class));
                        break;
                    case 4: // Logout
                        logoutUser();
                        break;
                }
            }
        });
    }

    private void checkCurrentUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }
    }

    private void logoutUser() {
        mAuth.signOut();
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkCurrentUser();
    }
}