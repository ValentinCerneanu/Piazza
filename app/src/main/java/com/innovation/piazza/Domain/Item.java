package com.innovation.piazza.Domain;

import android.graphics.Bitmap;

import com.innovation.piazza.Adapters.ItemAdapter;

public class Item {
    private String key;
    private String name;
    private String description;
    private Double price;
    private Double gramaj;
    private String picture;
    private Bitmap bitmap;
    private ItemAdapter itemAdapter;
    private int quantity;

    protected Item(Item item) {
        this.key = item.key;
        this.name = item.name;
        this.description = item.description;
        this.price = item.price;
        this.gramaj = item.gramaj;
        this.picture = item.picture;
        this.itemAdapter = item.itemAdapter;
        this.quantity = 0;
    };

    public Item(String key, String name, String description, Double price, Double gramaj, String picture, ItemAdapter itemAdapter) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.price = price;
        this.gramaj = gramaj;
        this.picture = picture;
        this.itemAdapter = itemAdapter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getGramaj() {
        return gramaj;
    }

    public void setGramaj(Double gramaj) {
        this.gramaj = gramaj;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        itemAdapter.notifyDataSetChanged();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
