package com.innovation.piazza.Domain;

import android.graphics.Bitmap;

import com.innovation.piazza.Adapters.StoreAdapter;

public class StoreModel {

    private String key;
    private String name;
    private String address;
    private String logo;
    private String latitude;
    private String longitude;
    private Bitmap bitmap;
    private StoreAdapter storeAdapter;

    public StoreModel(String key, String name, String address, String logo, String latitude, String longitude, StoreAdapter storeAdapter) {
        this.key = key;
        this.name = name;
        this.address = address;
        this.logo = logo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeAdapter = storeAdapter;
    }

    public String getKey() {
        return key;
    }

    public String getStoreName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getLogo() {
        return logo;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() { return longitude; }

    public Bitmap getBitmap() { return bitmap; }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        storeAdapter.notifyDataSetChanged();
    }
}
