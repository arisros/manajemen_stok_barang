package com.example.manajemenstokbarang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manajemenstokbarang.utils.NavigationHelper;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BarangKeluar extends AppCompatActivity {

    private Spinner spinnerProductName;
    private EditText inputQty;
    private Button btnConfirm;
    private ImageView btnhome;
    private ImageView btn_inventory;
    private ImageView btn_orders;
    private ImageView btn_stokbarang;
    private ImageView btnLogout;

    private int currentStockSelected;

    private FirebaseFirestore db;

    private void stockOutJumlah(int currentStock, int stockToOut, String productId) {
        int addedStock = currentStock - stockToOut;
        db.collection("Manajemen Stok Gudang").document(productId)
                .update("Jumlah", addedStock)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(BarangKeluar.this, "Stok barang berhasil di keluarkan", Toast.LENGTH_SHORT).show();
//                    clearForm();
//
//                  need to intent dashboard
                    Intent intent = new Intent(BarangKeluar.this, DashboardActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error adding stock: ");
//                    Toast.makeText("Gagal menambahkan stok barang", Toast.LENGTH_SHORT).show();
                });
    }

    private void getProducts() {
        db.collection("Manajemen Stok Gudang")
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
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, products);
                        spinnerProductName.setAdapter(adapter);
                    } else {
                        Log.e("Firestore", "Error getting products: ", task.getException());
                    }
                });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_keluar);

        btnhome = findViewById(R.id.btn_home);
        btn_inventory = findViewById(R.id.btn_inventory);
        btn_orders = findViewById(R.id.btn_orders);
        btn_stokbarang = findViewById(R.id.btn_stokbarang);
        btnLogout = findViewById(R.id.btn_settings);
        spinnerProductName = findViewById(R.id.spinner_product_name);

        db = FirebaseFirestore.getInstance();


        getProducts();


        // Aksi Tombol Home
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke DashboardActivity
                Intent intent = new Intent(BarangKeluar.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        NavigationHelper.setNavigationActions(this, btnhome, btn_inventory, btn_orders, btn_stokbarang, btnLogout);
        inputQty = findViewById(R.id.input_qty);
        btnConfirm = findViewById(R.id.btn_confirm);

        spinnerProductName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProduct = (String) parent.getItemAtPosition(position);
                if (selectedProduct != null) {
                    fetchProductDetails(selectedProduct);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                clearForm();
            }
        });
//        spinnerProductName.setAdapter(adapter);


        // Listener untuk tombol konfirmasi
        btnConfirm.setOnClickListener(v -> {
            String productId = spinnerProductName.getSelectedItem().toString();
            int stockToOut = Integer.parseInt(inputQty.getText().toString());
            int currentStock = currentStockSelected;
            stockOutJumlah(currentStock, stockToOut, productId);
        });

    }

    private void fetchProductDetails(String productId) {
        db.collection("Manajemen Stok Gudang").document(productId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot document = task.getResult();
                        currentStockSelected = Integer.parseInt(String.valueOf(document.get("Jumlah")));
                    } else {
                        Log.e("Firestore", "Error getting product details: ", task.getException());
                    }
                });
    }
}
