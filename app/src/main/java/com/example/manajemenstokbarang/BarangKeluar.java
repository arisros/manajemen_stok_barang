package com.example.manajemenstokbarang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BarangKeluar extends AppCompatActivity {

    private Spinner spinnerProductName;
    private EditText inputQty;
    private Button btnConfirm;
    private ImageView btnhome;
    private ImageView btn_inventory;
    private ImageView btn_orders;
    private ImageView btn_stokbarang;
    private ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_keluar);

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
                Intent intent = new Intent(BarangKeluar.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Barang Masuk
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(BarangKeluar.this, BarangMasuk.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Barang Keluar
        btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(BarangKeluar.this, BarangKeluar.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Melihat Stok Barang
        btn_stokbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(BarangKeluar.this, StokBarangActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke LoginActivity
                Intent intent = new Intent(BarangKeluar.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Menutup DashboardActivity
            }
        });

        // Hubungkan komponen dengan ID
        spinnerProductName = findViewById(R.id.spinner_product_name);
        inputQty = findViewById(R.id.input_qty);
        btnConfirm = findViewById(R.id.btn_confirm);

        // Data untuk Spinner (contoh data)
        String[] products = {"Product 1", "Product 2", "Product 3", "Product 4", "Product 5"};

        // Adapter untuk Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, products);
        spinnerProductName.setAdapter(adapter);

        // Listener untuk tombol konfirmasi
        btnConfirm.setOnClickListener(v -> {
            String product = spinnerProductName.getSelectedItem().toString();
            String qty = inputQty.getText().toString();

            if (qty.isEmpty()) {
                Toast.makeText(this, "Qty tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Barang Keluar:\n" + product + " - Qty: " + qty, Toast.LENGTH_LONG).show();
            }
        });
    }
}
