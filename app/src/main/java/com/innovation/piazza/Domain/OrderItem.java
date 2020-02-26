package com.innovation.piazza.Domain;

public class OrderItem {
    private String key;
    private int quantity;

    public OrderItem(String key, int quantity) {
        this.key = key;
        this.quantity = quantity;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
