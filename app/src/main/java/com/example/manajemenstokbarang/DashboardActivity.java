package com.example.manajemenstokbarang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manajemenstokbarang.adapters.ProductAdapter;
import com.example.manajemenstokbarang.models.Product;
import com.example.manajemenstokbarang.utils.NavigationHelper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    // Navigation buttons
    private ImageView btnHome;
    private ImageView btnInventory;
    private ImageView btnOrders;
    private ImageView btnLogout;
    private ImageView btnStokBarang;

    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize navigation buttons
        btnHome = findViewById(R.id.btn_home);
        btnInventory = findViewById(R.id.btn_inventory);
        btnOrders = findViewById(R.id.btn_orders);
        btnStokBarang = findViewById(R.id.btn_stokbarang);
        btnLogout = findViewById(R.id.btn_settings);


        // Set navigation actions
        NavigationHelper.setNavigationActions(this, btnHome, btnInventory, btnOrders, btnStokBarang, btnLogout);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load data from Firestore
        fetchProductsFromFirestore();
    }


    private void fetchProductsFromFirestore() {
        productList = new ArrayList<>();
        firestore.collection("Manajemen Stok Gudang") // Firestore collection name
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        for (var document : querySnapshot.getDocuments()) {
                            String name = document.getString("Nama Barang");
                            long quantity = Integer.parseInt(String.valueOf(document.get("Jumlah")));
                            String imageUrl = document.getString("Image");

                            // Create a Product object
                            Product product = new Product(name, (int) quantity, imageUrl);


                            productList.add(product);
                        }

                        for (Product product : productList) {
                            Log.d("Firestores", "Product: " + product.getName() + " - " + product.getQuantity());
                        }

                        // Set up the adapter
                        productAdapter = new ProductAdapter(productList, this);
                        recyclerView.setAdapter(productAdapter);
                    } else {
                        Log.e("Firestore", "Error fetching products: ", task.getException());
                    }
                });
    }
}
