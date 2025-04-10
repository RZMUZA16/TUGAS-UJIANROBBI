package com.example.pb_project;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
 //
public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView nameTextView, emailTextView, userIdTextView;
    private TextView phoneTextView, createdAtTextView, lastLoginTextView;
    private Button editProfileButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Sesuaikan dengan nama layout XML Anda

        // Inisialisasi view
        profileImageView = findViewById(R.id.profileImageView);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        userIdTextView = findViewById(R.id.userIdTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        createdAtTextView = findViewById(R.id.createdAtTextView);
        lastLoginTextView = findViewById(R.id.lastLoginTextView);
        editProfileButton = findViewById(R.id.editProfileButton);
        logoutButton = findViewById(R.id.logoutButton);

        loadUserData();

        // Tambahkan onClickListener untuk tombol
        logoutButton.setOnClickListener(v -> logout());
        editProfileButton.setOnClickListener(v -> editProfile());
    }

    private void loadUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Set data dasar
            nameTextView.setText(user.getDisplayName() != null ? user.getDisplayName() : "Nama belum diatur");
            emailTextView.setText(user.getEmail());
            userIdTextView.setText(user.getUid());

            // Set nomor telepon jika ada
            phoneTextView.setText(user.getPhoneNumber() != null ? user.getPhoneNumber() : "Belum diatur");

            // Format tanggal
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

            // Tanggal pembuatan akun
            long createTime = user.getMetadata().getCreationTimestamp();
            createdAtTextView.setText(sdf.format(new Date(createTime)));

            // Terakhir login
            long lastLogin = user.getMetadata().getLastSignInTimestamp();
            lastLoginTextView.setText("Terakhir login: " + sdf.format(new Date(lastLogin)));

            // Load foto profil jika ada
            if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .placeholder(R.drawable.ic_profile_placeholder)
                        .into(profileImageView);
            }
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        // Redirect ke login activity
        finish();
    }

    private void editProfile() {
        // Implementasi edit profile
        // Contoh: startActivity(new Intent(this, EditProfileActivity.class));
    }
}