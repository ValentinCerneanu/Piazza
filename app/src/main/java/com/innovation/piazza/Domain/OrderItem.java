package com.innovation.piazza.Domain;

public class OrderItem {
    private String key;
    private String categoryKey;
    private int quantity;

    public OrderItem(String key, int quantity, String categoryKey) {
        this.key = key;
        this.categoryKey = categoryKey;
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

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }
}
