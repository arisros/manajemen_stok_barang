package com.example.manajemenstokbarang.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manajemenstokbarang.R;
import com.example.manajemenstokbarang.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;

    // Constructor untuk menerima data dan context
    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_product.xml untuk setiap item di RecyclerView
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = productList.get(position);

        // Mengatur data produk ke dalam komponen View
        holder.productName.setText(currentProduct.getName());
        holder.productStock.setText("Stock: " + currentProduct.getStock());
        holder.productImage.setImageResource(currentProduct.getImageResource()); // Menggunakan gambar sesuai dengan resource
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder untuk setiap item di RecyclerView
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productStock;
        ImageView productImage;

        public ProductViewHolder(View itemView) {
            super(itemView);
            // Menghubungkan View dengan ID dari layout item_product.xml
            productName = itemView.findViewById(R.id.product_name);
            productStock = itemView.findViewById(R.id.product_stock);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}
