package com.AbnerLuz.ecommerce_project.core;

public enum OrderStatus {
    PENDING("PENDING"),
    PAID("PAID"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");
    private String status;
    OrderStatus(String status) {
        this.status = status;
    }
    public String getStatus() {return this.status;}
}
