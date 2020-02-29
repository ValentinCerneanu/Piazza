package com.innovation.piazza.Services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.Locale;

public class LocationService {
    public static final int LOCATION_PERMISSION_CODE = 1;
    private static final LocationService instance = new LocationService();

    private LocationTracker locationTracker;
    private Geocoder geocoder;
    private Address addresses;

    private LocationService() {
    }

    public static LocationService getInstance() {
        return instance;
    }

    public void getAddressByLocation(Context context) {
        geocoder = new Geocoder(context, Locale.getDefault());
        locationTracker = new LocationTracker(context);
        try {
            addresses = geocoder.getFromLocation(locationTracker.getLatitude(), locationTracker.getLongitude(), 1).get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Address getAddresses() {
        return addresses;
    }

    public String getAddressLine() {
        if(addresses != null)
            return addresses.getAddressLine(0);
        return new String();
    }

    public Geocoder getGeocoder() {
        return geocoder;
    }

    public Double getLatitude() {
        if(locationTracker != null)
            return locationTracker.getLatitude();
        else
            return new Double(0.0);
    }

    public Double getLongitude() {
        if(locationTracker != null)
            return locationTracker.getLongitude();
        else
            return new Double(0.0);
    }

}
