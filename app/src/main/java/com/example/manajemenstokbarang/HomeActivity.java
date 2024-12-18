package com.example.manajemenstokbarang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {
    private ImageView btnhome;
    private ImageView btn_inventory;
    private ImageView btn_orders;
    private ImageView btn_stokbarang;
    private ImageView btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnhome = findViewById(R.id.btn_home);
        btn_inventory = findViewById(R.id.btn_inventory);
        btn_orders = findViewById(R.id.btn_orders);
        btn_stokbarang = findViewById(R.id.btn_stokbarang);
        btnLogout = findViewById(R.id.btn_settings);

        // Aksi Tombol Home
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Barang Masuk
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(HomeActivity.this, BarangMasuk.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Barang Keluar
        btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(HomeActivity.this, BarangKeluar.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Melihat Stok Barang
        btn_stokbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(HomeActivity.this, StokBarangActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke LoginActivity
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });


        // Contoh: Anda dapat menambahkan logika tambahan di sini
    }
}
