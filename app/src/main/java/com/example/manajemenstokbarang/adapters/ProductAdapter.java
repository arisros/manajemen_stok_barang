package com.example.manajemenstokbarang.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.manajemenstokbarang.R;
import com.example.manajemenstokbarang.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvQuantity.setText(String.valueOf(product.getQuantity()));

        // Load image using Glide
        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_placeholder) // Placeholder image
                .error(R.drawable.ic_error) // Error image
                .into(holder.ivProductImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQuantity;
        ImageView ivProductImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvQuantity = itemView.findViewById(R.id.tv_product_quantity);
            ivProductImage = itemView.findViewById(R.id.iv_product_image);
        }
    }
}
