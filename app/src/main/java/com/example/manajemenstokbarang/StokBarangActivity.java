package com.example.manajemenstokbarang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class StokBarangActivity extends AppCompatActivity {

    // Deklarasi widget
    private ImageView btnhome, btn_inventory, btn_orders, btn_stokbarang, btnLogout;
    private ScrollView scrollView;
    private TableLayout tableData;
    private TextView titleStokBarang;

    // Firestore
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_barang);

        // Inisialisasi widget
        btnhome = findViewById(R.id.btn_home);
        btn_inventory = findViewById(R.id.btn_inventory);
        btn_orders = findViewById(R.id.btn_orders);
        btn_stokbarang = findViewById(R.id.btn_stokbarang);
        btnLogout = findViewById(R.id.btn_settings);
        scrollView = findViewById(R.id.scrollView);
        tableData = findViewById(R.id.table_data);
        titleStokBarang = findViewById(R.id.title_stok_barang);

        // Inisialisasi Firestore
        db = FirebaseFirestore.getInstance();

        // Navigasi antar layar
        setupNavigation();

        // Memuat data barang dari Firestore
        fetchDataFromFirestore();
    }

    private void setupNavigation() {
        // Navigasi ke layar lain
        btnhome.setOnClickListener(v -> {
            startActivity(new Intent(StokBarangActivity.this, DashboardActivity.class));
            finish();
        });

        btn_inventory.setOnClickListener(v -> {
            startActivity(new Intent(StokBarangActivity.this, BarangMasuk.class));
            finish();
        });

        btn_orders.setOnClickListener(v -> {
            startActivity(new Intent(StokBarangActivity.this, BarangKeluar.class));
            finish();
        });

        btn_stokbarang.setOnClickListener(v -> {
            startActivity(new Intent(StokBarangActivity.this, StokBarangActivity.class));
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(StokBarangActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void fetchDataFromFirestore() {
        db.collection("Manajemen Stok Gudang") // Nama koleksi sesuai dengan Firestore
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        int index = 1; // Nomor urut
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            addRowToTable(index++, document);
                        }
                    } else {
                        Log.e("Firestore", "Error getting documents: ", task.getException());
                    }
                });
    }

    private void addRowToTable(int index, DocumentSnapshot document) {
        // Membuat baris baru untuk tabel
        TableRow row = new TableRow(this);

        // Kolom 1: No
        TextView no = new TextView(this);
        no.setText(String.valueOf(index));
        no.setPadding(16, 16, 16, 16);
        row.addView(no);

        // Kolom 2: ID Barang
        TextView idBarang = new TextView(this);
        idBarang.setText(document.getString("ID Barang"));
        idBarang.setPadding(16, 16, 16, 16);
        row.addView(idBarang);

        // Kolom 3: Nama Barang
        TextView namaBarang = new TextView(this);
        namaBarang.setText(document.getString("Nama Barang"));
        namaBarang.setPadding(16, 16, 16, 16);
        row.addView(namaBarang);

        // Kolom 4: Jumlah
        TextView jumlah = new TextView(this);
        jumlah.setText(String.valueOf(document.get("Jumlah")));
        jumlah.setPadding(16, 16, 16, 16);
        row.addView(jumlah);

        // Kolom 5: Lokasi Gudang
        TextView lokasiGudang = new TextView(this);
        lokasiGudang.setText(document.getString("Lokasi Gudang"));
        lokasiGudang.setPadding(16, 16, 16, 16);
        row.addView(lokasiGudang);

        // Menambahkan baris ke dalam tabel
        tableData.addView(row);
    }
}
