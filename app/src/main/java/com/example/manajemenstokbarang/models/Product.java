package com.example.manajemenstokbarang.models;


public class Product {
    private final String name;
    private final int quantity;
    private final String imageUrl;

    public Product(String name, int quantity, String imageUrl) {
        this.name = name;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
