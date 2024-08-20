package com.example.myapplication.Models;
import java.time.LocalDate;
public class Order {
    private int orderId,custId;
    private String orderAddress;
    private LocalDate orderDate;


    public Order(int custId, String orderAddress, LocalDate orderDate) {
        this.custId = custId;
        this.orderAddress = orderAddress;
        this.orderDate = orderDate;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
