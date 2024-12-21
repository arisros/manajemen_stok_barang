package com.example.manajemenstokbarang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText etId, etUsername, etPassword;
//    private Spinner spinnerPosition;
    private CheckBox checkboxRemember;
    private Button btnLogin;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        if (getUserRole() != null && !getUserRole().isEmpty()) {
            // User already logged in
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish(); // Close LoginActivity
        }

        // Initialize Views
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        checkboxRemember = findViewById(R.id.checkbox_remember);
        btnLogin = findViewById(R.id.btn_login);

        // Login Button Action
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
//                String position = spinnerPosition.getSelectedItem().toString();

                // Validate user input
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Harap isi semua data!", Toast.LENGTH_SHORT).show();
                } else {
                    // Validate user with Firestore
                    validateUser(username, password);
                }
            }
        });
    }

    private void storeUserRole(String role) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userRole", role); // Save the user role
        editor.apply(); // Apply the changes
    }

    private String getUserRole() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("userRole", ""); // Default to empty string if not found
    }

    private void validateUser(String username, String password) {
        db.collection("Login") // Replace "users" with your Firestore collection nam
                .whereEqualTo("Username", username)
                .whereEqualTo("Password", password)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
//                        get role
                        String position = queryDocumentSnapshots.getDocuments().get(0).getString("Posisi");
                        // Login success
                        Toast.makeText(LoginActivity.this, "Login Berhasil!\nPosisi: " + position, Toast.LENGTH_SHORT).show();

                        // Navigate to DashboardActivity
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        storeUserRole(position); // Store user role in SharedPreferences
                        startActivity(intent);
                        finish(); // Close LoginActivity
                    } else {
                        // Invalid credentials
                        Toast.makeText(LoginActivity.this, "ID, Username, atau Password salah!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle Firestore errors
                    Toast.makeText(LoginActivity.this, "Terjadi kesalahan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
