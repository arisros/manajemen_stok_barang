package com.example.manajemenstokbarang.models;

public class Product {
    private String name;
    private int stock;
    private int imageResource;

    public Product(String name, int stock, int imageResource) {
        this.name = name;
        this.stock = stock;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getImageResource() {
        return imageResource;
    }
}
