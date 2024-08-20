package com.example.myapplication.Models;

public class Product {

    private int id, quantity, catId;
    private String name;
    private byte[] image;
    private double price;
    public byte [] getImage() {
        return image;
    }
    public void setImage(byte [] image) {
        this.image = image;
    }
    public Product(int quantity, int catId, String name, byte[] image, double price) {
        this.quantity = quantity;
        this.catId = catId;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
