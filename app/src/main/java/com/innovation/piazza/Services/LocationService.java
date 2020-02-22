package com.innovation.piazza.Services;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.util.List;
import java.util.Locale;

public class LocationService {

    private LocationTracker locationTracker;
    private Geocoder geocoder;
    private List<Address> addresses;

    public LocationService(Context context) {
        geocoder = new Geocoder(context, Locale.getDefault());
        locationTracker = new LocationTracker(context);
    }

    public void getAddressByLocation() {
        locationTracker.getLocation();
        try {
            addresses = geocoder.getFromLocation(locationTracker.getLatitude(), locationTracker.getLongitude(), 1);

            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            //TODO: addresa editabil si auto completat cu addresses.get(0).getAddressLine
            //  etaj, scara, ap

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public String getAddressLine(){
        return addresses.get(0).getAddressLine(0);
    }
}
