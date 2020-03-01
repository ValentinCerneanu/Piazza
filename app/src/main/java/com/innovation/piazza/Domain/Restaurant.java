package com.innovation.piazza.Domain;

public class Restaurant {
    private String key;
    private String name;
    private String address;
    private String discountValue;
    private String oreDiscount;

    public Restaurant(String key, String name, String address, String discountValue, String oreDiscount) {
        this.key = key;
        this.name = name;
        this.address = address;
        this.discountValue = discountValue;
        this.oreDiscount = oreDiscount;
    }

    public Restaurant(String name, String address, String discountValue, String oreDiscount) {
        this.name = name;
        this.address = address;
        this.discountValue = discountValue;
        this.oreDiscount = oreDiscount;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public String getOreDiscount() {
        return oreDiscount;
    }

    public void setOreDiscount(String oreDiscount) {
        this.oreDiscount = oreDiscount;
    }
}
