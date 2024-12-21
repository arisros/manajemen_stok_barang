package com.example.manajemenstokbarang.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

import com.example.manajemenstokbarang.BarangKeluar;
import com.example.manajemenstokbarang.BarangMasuk;
import com.example.manajemenstokbarang.DashboardActivity;
import com.example.manajemenstokbarang.LoginActivity;
import com.example.manajemenstokbarang.StokBarangActivity;

public class NavigationHelper {

    // Generic method to navigate to any activity
    public static void navigateTo(Context context, Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    public static String getUserRole(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPreferences", MODE_PRIVATE);
        return sharedPreferences.getString("userRole", ""); // Default to empty string if not found
    }

    // Method to set up navigation actions for the buttons
    public static void setNavigationActions(
            Context context,
            ImageView btnHome,
            ImageView btnInventory,
            ImageView btnOrders,
            ImageView btnStokBarang,
            ImageView btnLogout
            ) {

        // Handle role-specific visibility of buttons
        if (getUserRole(context).equals("Admin Gudang")) {
            btnOrders.setVisibility(View.GONE);
            btnInventory.setVisibility(View.GONE);
        }

        if (getUserRole(context).equals("Project Manager")) {
            btnInventory.setVisibility(View.GONE);
        }

        if (getUserRole(context).equals("Kepala Gudang")) {
            btnOrders.setVisibility(View.GONE);
        }

        // Set onClickListeners for buttons
        btnHome.setOnClickListener(v -> navigateTo(context, DashboardActivity.class));
        btnInventory.setOnClickListener(v -> navigateTo(context, BarangMasuk.class));
        btnStokBarang.setOnClickListener(v -> navigateTo(context, StokBarangActivity.class));
        btnOrders.setOnClickListener(v -> navigateTo(context, BarangKeluar.class));
        btnLogout.setOnClickListener(v -> {
            // Clear the user role from SharedPreferences and navigate to login
            SharedPreferences sharedPreferences = context.getSharedPreferences("AppPreferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("userRole");
            editor.apply();

            navigateTo(context, LoginActivity.class);
        });
    }
}
