package com.innovation.piazza.Domain;

import com.innovation.piazza.Services.LocationService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Order {

    private String storeKey;
    private ArrayList<OrderItem> items = new ArrayList<>();
    private Double totalPrice;
    private Date time;
    private String userID;
    private String address;
    private ArrayList<Double> geoLocation = new ArrayList<>();
    private String status;

    public Order(String userID, String storeKey, ArrayList<Item> items, Double totalPrice) {
        this.userID = userID;
        this.storeKey = storeKey;
        this.totalPrice = totalPrice;
        this.time = Calendar.getInstance().getTime();
        for(Item item: items){
            this.items.add(new OrderItem(item.getKey(), item.getQuantity(), item.getCategoryKey()));
        }
        LocationService locationService = LocationService.getInstance();
        this.address = locationService.getAddressLine();
        this.geoLocation.add(locationService.getLatitude());
        this.geoLocation.add(locationService.getLongitude());
        this.status = OrderModel.STATUS_NEPRELUATA;
    }

    public Order(String userID, String storeKey, Double totalPrice, String time, String address, String status) {
        this.userID = userID;
        this.storeKey = storeKey;
        this.totalPrice = totalPrice;
        this.address = address;
        this.status = status;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");
            this.time = dateFormat.parse(time);
            SimpleDateFormat humanReadableFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
            this.time = humanReadableFormat.parse(time);
        }catch (Exception e){
            e.printStackTrace();
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

    public String getTime() {
        return time.toString();
    }

    public String getUserID() {
        return userID;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Double> getGeoLocation() {
        return geoLocation;
    }

    public String getStatus() {
        return status;
    }
}
