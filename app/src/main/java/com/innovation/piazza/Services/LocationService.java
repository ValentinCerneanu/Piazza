package com.innovation.piazza.Services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.Locale;

public class LocationService {
    public static final int LOCATION_PERMISSION_CODE = 1;

    private LocationTracker locationTracker;
    private Geocoder geocoder;
    private Address addresses;

    public LocationService(Context context) {
        geocoder = new Geocoder(context, Locale.getDefault());
        locationTracker = new LocationTracker(context);
    }

    public void getAddressByLocation() {
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
}
