package com.example.manajemenstokbarang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private EditText etId, etUsername, etPassword;
    private Spinner spinnerPosition;
    private CheckBox checkboxRemember;
    private Button btnLogin;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        TODO REMOVE THIS
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish(); // Close LoginActivity

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        // Initialize Views
        etId = findViewById(R.id.et_id);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        spinnerPosition = findViewById(R.id.spinner_position);
        checkboxRemember = findViewById(R.id.checkbox_remember);
        btnLogin = findViewById(R.id.btn_login);

        // Setup Spinner (Dropdown)
        String[] positions = {"Project Manager", "Admin Gudang", "Kepala Gudang", "Staff Gudang"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, positions);
        spinnerPosition.setAdapter(adapter);

        // Login Button Action
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etId.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String position = spinnerPosition.getSelectedItem().toString();

                // Validate user input
                if (id.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Harap isi semua data!", Toast.LENGTH_SHORT).show();
                } else {
                    // Validate user with Firestore
                    validateUser(id, username, password, position);
                }
            }
        });
    }

    private void validateUser(String id, String username, String password, String position) {
        db.collection("Login") // Replace "users" with your Firestore collection nam
                .whereEqualTo("ID", id)
                .whereEqualTo("Username", username)
                .whereEqualTo("Password", password)
                .whereEqualTo("Posisi", position)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Login success
                        Toast.makeText(LoginActivity.this, "Login Berhasil!\nPosisi: " + position, Toast.LENGTH_SHORT).show();

                        // Navigate to DashboardActivity
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
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
