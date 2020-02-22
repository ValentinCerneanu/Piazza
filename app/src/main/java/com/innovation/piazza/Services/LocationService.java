package com.innovation.piazza.Services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.List;
import java.util.Locale;

public class LocationService {

    private LocationTracker locationTracker;
    private Geocoder geocoder;
    private Address addresses;

    public LocationService(Context context) {
        geocoder = new Geocoder(context, Locale.getDefault());
        locationTracker = new LocationTracker(context);
    }

    public void getAddressByLocation() {
        locationTracker.getLocation();

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
        return addresses.getAddressLine(0);
    }
}
