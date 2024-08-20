package com.example.myapplication.Models;

public class OrderDetails {
    private int orderId,ProId,quantity;
   int confirmed;
   int rate;

    public void setRate(int rate) {
        this.rate = rate;
    }
    public int getRate() {
        return rate;
    }

    public OrderDetails(int proId, int quantity, int rate) {
        this.orderId = orderId;
        ProId = proId;
        this.quantity = quantity;
        this.rate=rate;
       // this.confirmed=confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProId() {
        return ProId;
    }

    public void setProId(int proId) {
        ProId = proId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
