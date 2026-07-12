package com.AbnerLuz.ecommerce_project.core;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");
    String role;
    UserRole(String role){
        this.role = role;
    }
    public String getRole(){return this.role;}
}
