package com.example.manajemenstokbarang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manajemenstokbarang.adapters.ProductAdapter;
import com.example.manajemenstokbarang.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    // Tombol navigasi
    private ImageView btnHome;
    private ImageView btnInventory;
    private ImageView btnOrders;
    private ImageView btnLogout;
    private ImageView btn_stokbarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inisialisasi tombol navigasi
        btnHome = findViewById(R.id.btn_home);
        btnInventory = findViewById(R.id.btn_inventory);
        btnOrders = findViewById(R.id.btn_orders);
        btn_stokbarang = findViewById(R.id.btn_stokbarang);
        btnLogout = findViewById(R.id.btn_settings);

        // Aksi Tombol Home
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Aksi Tombol Barang Masuk
        btnInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, BarangMasuk.class);
                startActivity(intent);
                finish();
            }
        });
        // Aksi Tombol Melihat Stok Barang
        btn_stokbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, StokBarangActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Aksi Tombol Barang Keluar
        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, BarangKeluar.class);
                startActivity(intent);
                finish();
            }
        });

        // Aksi Tombol Logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke LoginActivity
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Membuat data dummy
        productList = new ArrayList<>();
        productList.add(new Product("Lampu PJU", 15, R.drawable.ic_lampu_pju2));
        productList.add(new Product("Tiang PJU", 10, R.drawable.ic_logo_tiangpju));
        productList.add(new Product("Angkur", 15, R.drawable.ic_logo_angkur));
        productList.add(new Product("Lampu", 10, R.drawable.ic_logo_lampu));
        productList.add(new Product("MCB", 15, R.drawable.ic_logo_mcb));
        productList.add(new Product("Panel", 10, R.drawable.ic_logo_panel));


        // Inisialisasi Adapter
        productAdapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);
    }
}
