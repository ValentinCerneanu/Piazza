package com.innovation.piazza.Domain;

import java.util.ArrayList;

public class Order {

    private String storeKey;
    private ArrayList<OrderItem> items = new ArrayList<>();

    public Order(String storeKey, ArrayList<Item> items) {
        this.storeKey = storeKey;
        for(Item item: items){
            this.items.add(new OrderItem(item.getKey(), item.getQuantity()));
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
}
