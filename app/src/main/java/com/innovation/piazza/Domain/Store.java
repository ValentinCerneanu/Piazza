package com.innovation.piazza.Domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.innovation.piazza.Adapters.StoreAdapter;

public class Store implements Parcelable {

    private String key;
    private String name;
    private String address;
    private String logo;
    private String latitude;
    private String longitude;
    private Bitmap bitmap;
    private StoreAdapter storeAdapter;

    public Store(String key, String name, String address, String logo, String latitude, String longitude, StoreAdapter storeAdapter) {
        this.key = key;
        this.name = name;
        this.address = address;
        this.logo = logo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storeAdapter = storeAdapter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(logo);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeParcelable(bitmap, flags);
    }

    protected Store(Parcel in) {
        key = in.readString();
        name = in.readString();
        address = in.readString();
        logo = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    public String getKey() {
        return key;
    }

    public String getName() {
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
