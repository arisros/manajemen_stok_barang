package com.example.manajemenstokbarang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BarangMasuk extends AppCompatActivity {

    private Button btnConfirm;
    private EditText etIdBarang, etNamaBarang, etJumlahBarang, etLokasiBarang;
    private ImageView btnhome, btn_inventory, btn_orders, btn_stokbarang, btnLogout;
    private Spinner spinnerProductName;
//    private getproducts(){
////        firestore.collection("Manajemen Stok Barang")
////                .get().addOnCompleteListener(
////
////
////                )
//    }


    private FirebaseFirestore firestore; // Referensi Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_masuk);
        // Data untuk Spinner (contoh data)
        String[] products = {"Product 1", "Product 2", "Product 3", "Product 4", "Product 5"};

        // Adapter untuk Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, products);
        spinnerProductName.setAdapter(adapter);

        // Listener untuk tombol konfirmasi
        btnConfirm.setOnClickListener(v -> {
            String product = spinnerProductName.getSelectedItem().toString();
        });
//
//        // Inisialisasi Firestore
//        firestore = FirebaseFirestore.getInstance();
//
//        // Inisialisasi komponen UI
//        btnConfirm = findViewById(R.id.btn_confirm);
//        etIdBarang = findViewById(R.id.et_id_barang);
//        etNamaBarang = findViewById(R.id.et_nama_barang);
//        etJumlahBarang = findViewById(R.id.et_jumlah_barang);
//        etLokasiBarang = findViewById(R.id.et_keterangan_barang);
//
//        btnhome = findViewById(R.id.btn_home);
//        btn_inventory = findViewById(R.id.btn_inventory);
//        btn_orders = findViewById(R.id.btn_orders);
//        btn_stokbarang = findViewById(R.id.btn_stokbarang);
//        btnLogout = findViewById(R.id.btn_settings);
//
//        // Tombol konfirmasi untuk menambahkan data ke Firestore
//        btnConfirm.setOnClickListener(v -> addDataToFirestore());
//
//        // Navigasi tombol lainnya
//        btnhome.setOnClickListener(v -> navigateTo(DashboardActivity.class));
//        btn_inventory.setOnClickListener(v -> navigateTo(BarangMasuk.class));
//        btn_orders.setOnClickListener(v -> navigateTo(BarangKeluar.class));
//        btn_stokbarang.setOnClickListener(v -> navigateTo(StokBarangActivity.class));
//        btnLogout.setOnClickListener(v -> navigateTo(LoginActivity.class));
    }

    private void addDataToFirestore() {
        String idBarang = etIdBarang.getText().toString().trim();
        String namaBarang = etNamaBarang.getText().toString().trim();
        String jumlahBarang = etJumlahBarang.getText().toString().trim();
        String lokasiBarang = etLokasiBarang.getText().toString().trim();

        if (idBarang.isEmpty() || namaBarang.isEmpty() || jumlahBarang.isEmpty() || lokasiBarang.isEmpty()) {
            Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat objek data barang
        Map<String, Object> barangData = new HashMap<>();
        barangData.put("NamaBarang", namaBarang);
        barangData.put("Jumlah", jumlahBarang);
        barangData.put("LokasiGudang", lokasiBarang);

        // Menambahkan data ke Firestore
        DocumentReference docRef = firestore.collection("BarangMasuk").document(idBarang);
        docRef.set(barangData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                clearForm();
            } else {
                Toast.makeText(this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        etIdBarang.setText("");
        etNamaBarang.setText("");
        etJumlahBarang.setText("");
        etLokasiBarang.setText("");
    }

    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(BarangMasuk.this, activityClass);
        startActivity(intent);
        finish();
    }
}
