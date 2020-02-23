package com.innovation.piazza.Domain;

public class StoreModel {

    private String key;
    private String name;
    private String address;
    private String logo;
    private String latitude;
    private String longitude;

    public StoreModel(String key, String name, String address, String logo, String latitude, String longitude) {
        this.key = key;
        this.name = name;
        this.address = address;
        this.logo = logo;
        this.latitude = latitude;
        this.longitude = longitude;
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
}
