// File: StokBarangActivity.java

package com.example.manajemenstokbarang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class StokBarangActivity extends AppCompatActivity {
    private ImageView btnhome;
    private ImageView btn_inventory;
    private ImageView btn_orders;
    private ImageView btn_stokbarang;
    private ImageView btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_barang);
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
                Intent intent = new Intent(StokBarangActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Barang Masuk
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(StokBarangActivity.this, BarangMasuk.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Barang Keluar
        btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(StokBarangActivity.this, BarangKeluar.class);
                startActivity(intent);
                finish();
            }
        });

        // Aksi Tombol Melihat Stok Barang
        btn_stokbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(StokBarangActivity.this, StokBarangActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke LoginActivity
                Intent intent = new Intent(StokBarangActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Inisialisasi TableLayout
        TableLayout tableData = findViewById(R.id.table_data);

        // Menambahkan baris ke tabel secara dinamis
        for (int i = 0; i < 15; i++) { // Data contoh
            TableRow row = new TableRow(this);

            // Kolom 1: No
            TextView no = new TextView(this);
            no.setText(String.valueOf(i + 1)); // Nomor urut
            no.setPadding(16, 16, 16, 16);
            row.addView(no);

            // Kolom 2: ID Barang
            TextView idBarang = new TextView(this);
            idBarang.setText("A00" + (i + 1));
            idBarang.setPadding(16, 16, 16, 16);
            row.addView(idBarang);

            // Kolom 3: Nama Barang
            TextView namaBarang = new TextView(this);
            namaBarang.setText("Barang " + (i + 1));
            namaBarang.setPadding(16, 16, 16, 16);
            row.addView(namaBarang);

            // Kolom 4: Jumlah
            TextView jumlah = new TextView(this);
            jumlah.setText(String.valueOf(10 + i)); // Jumlah Barang
            jumlah.setPadding(16, 16, 16, 16);
            row.addView(jumlah);

            // Kolom 5: Lokasi Gudang
            TextView lokasiGudang = new TextView(this);
            lokasiGudang.setText(i % 2 == 0 ? "Kalideres" : "Ciledug"); // Lokasi
            lokasiGudang.setPadding(16, 16, 16, 16);
            row.addView(lokasiGudang);

            // Menambahkan baris ke TableLayout
            tableData.addView(row);
        }
    }
}
