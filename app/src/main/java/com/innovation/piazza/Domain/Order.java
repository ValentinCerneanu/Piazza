package com.innovation.piazza.Domain;

import java.util.ArrayList;

public class Order {

    private String storeKey;
    private ArrayList<OrderItem> items = new ArrayList<>();
    private Double totalPrice;

    public Order(String storeKey, ArrayList<Item> items, Double totalPrice) {
        this.storeKey = storeKey;
        this.totalPrice = totalPrice;
        for(Item item: items){
            this.items.add(new OrderItem(item.getKey(), item.getQuantity(), item.getCategoryKey()));
        }
    }

    public String getStoreKey() {
        return storeKey;
    }

    public void setStoreKey(String storeKey) {
        this.storeKey = storeKey;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderItem> items) {
        this.items = items;
    }

    public Double getTotalPrice() { return totalPrice; }

    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}
