package com.example.myapplication.Models;

public class ReportInfo {
    String customerName;
    String address;
    String productName;
    int productQuantity;
    String date;
    int rate;

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getDate() {
        return date;
    }

    public ReportInfo(String customerName, String address, String productName, int productQuantity, String date,int rate) {
        this.customerName = customerName;
        this.address = address;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.date = date;
        this.rate=rate;
    }

}
