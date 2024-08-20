package com.example.myapplication.Models;

public class UnconfirmedProducts {
    private int id, quantity;
    private String name;
    private byte[] image;
    private double price;
    private int userid;
    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }
    public byte [] getImage() {
        return image;
    }
    public void setImage(byte [] image) {
        this.image = image;
    }
    public UnconfirmedProducts(int id,int quantity, String name, byte[] image, double price,int userid) {
        this.quantity = quantity;
        this.name = name;
        this.image = image;
        this.price = price;
        this.id=id;
        this.userid=userid;
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
