package com.example.manajemenstokbarang;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class BarangMasuk extends AppCompatActivity {

    private Button btnSelectDate;
    private TextView tvSelectedDate;
    private ImageView btnhome;
    private ImageView btn_inventory;
    private ImageView btn_orders;
    private ImageView btn_stokbarang;
    private ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_masuk);

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
                Intent intent = new Intent(BarangMasuk.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Barang Masuk
        btn_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(BarangMasuk.this, BarangMasuk.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Barang Keluar
        btn_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(BarangMasuk.this, BarangKeluar.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Melihat Stok Barang
        btn_stokbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(BarangMasuk.this, StokBarangActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke LoginActivity
                Intent intent = new Intent(BarangMasuk.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnSelectDate = findViewById(R.id.btn_select_date);
        tvSelectedDate = findViewById(R.id.tv_selected_date);

        btnSelectDate.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        // Mendapatkan tanggal saat ini
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Membuka dialog DatePicker
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            // Format tanggal yang dipilih
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            tvSelectedDate.setText(selectedDate); // Menampilkan hasil tanggal
        }, year, month, day);

        // Menampilkan dialog
        datePickerDialog.show();
    }
}
