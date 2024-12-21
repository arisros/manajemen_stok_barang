package com.example.manajemenstokbarang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class BarangMasuk extends AppCompatActivity {

    private Button btnConfirm;

    private EditText etIdBarang, etNamaBarang, etJumlahBarang, etLokasiBarang, etAddedStock;
    private ImageView btnhome, btn_inventory, btn_orders, btn_stokbarang, btnLogout;
    private Spinner spinnerProductName;

    private FirebaseFirestore firestore; // Referensi Firestore
    private Map<String, String> productIdMap = new HashMap<>(); // Map for spinner display names to Firestore IDs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_masuk);

        // Initialize views
        spinnerProductName = findViewById(R.id.spinner_product_name);
        etNamaBarang = findViewById(R.id.et_nama_barang);
        etJumlahBarang = findViewById(R.id.et_jumlah_barang);
        etLokasiBarang = findViewById(R.id.et_lokasi);
        etAddedStock = findViewById(R.id.et_add_qty);

        firestore = FirebaseFirestore.getInstance();

        // Load products into the spinner
        getProducts();

        // Spinner item selection handler
        spinnerProductName.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProduct = (String) parent.getItemAtPosition(position);
                String productId = productIdMap.get(selectedProduct);
                if (productId != null) {
                    fetchProductDetails(productId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                clearForm();
            }
        });

        btnConfirm = findViewById(R.id.btn_confirm);

        // Navigation button setup
        btnhome = findViewById(R.id.btn_home);
        btn_inventory = findViewById(R.id.btn_inventory);
        btn_orders = findViewById(R.id.btn_orders);
        btn_stokbarang = findViewById(R.id.btn_stokbarang);
        btnLogout = findViewById(R.id.btn_settings);

        btnhome.setOnClickListener(v -> navigateTo(DashboardActivity.class));
        btn_inventory.setOnClickListener(v -> navigateTo(BarangMasuk.class));
        btn_orders.setOnClickListener(v -> navigateTo(BarangKeluar.class));
        btn_stokbarang.setOnClickListener(v -> navigateTo(StokBarangActivity.class));
        btnLogout.setOnClickListener(v -> navigateTo(LoginActivity.class));

        btnConfirm.setOnClickListener(v -> {
            String productId = productIdMap.get(spinnerProductName.getSelectedItem().toString());
            int stockToAdd = Integer.parseInt(etAddedStock.getText().toString());
            int currentStock = Integer.parseInt(etJumlahBarang.getText().toString());
            addStockJumlah(currentStock, stockToAdd, productId);
        });
    }
    
    private void addStockJumlah(int currentStock, int stockToAdd, String productId) {
        int addedStock = currentStock + stockToAdd;
        firestore.collection("Manajemen Stok Gudang").document(productId)
                .update("Jumlah", addedStock)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(BarangMasuk.this, "Stok barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    clearForm();
//
//                  need to intent dashboard
                    Intent intent = new Intent(BarangMasuk.this, DashboardActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error adding stock: ", e);
//                    Toast.makeText("Gagal menambahkan stok barang", Toast.LENGTH_SHORT).show();
                });
    }

    private void getProducts() {
        firestore.collection("Manajemen Stok Gudang")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        List<String> products = new ArrayList<>();
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            String productId = document.getId(); // Firestore document ID
                            String productName = document.getString("ID Barang"); // Product name field
                            if (productName != null) {
                                products.add(productName);
                                productIdMap.put(productName, productId); // Map product name to Firestore ID
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, products);
                        spinnerProductName.setAdapter(adapter);
                    } else {
                        Log.e("Firestore", "Error getting products: ", task.getException());
                    }
                });
    }

    private void fetchProductDetails(String productId) {
        firestore.collection("Manajemen Stok Gudang").document(productId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot document = task.getResult();
                        etNamaBarang.setText(document.getString("Nama Barang"));
                        etLokasiBarang.setText(document.getString("Lokasi Gudang"));
                        etJumlahBarang.setText(String.valueOf(document.get("Jumlah")));

                    } else {
                        Log.e("Firestore", "Error getting product details: ", task.getException());
                    }
                });
    }


    private void clearForm() {
        etNamaBarang.setText("");
        etJumlahBarang.setText("");
        etLokasiBarang.setText("");
        etAddedStock.setText("");
    }

    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(BarangMasuk.this, activityClass);
        startActivity(intent);
        finish();
    }
}
