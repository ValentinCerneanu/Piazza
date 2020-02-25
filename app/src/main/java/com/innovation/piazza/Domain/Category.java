package com.innovation.piazza.Domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.innovation.piazza.Adapters.CategoryAdapter;

public class Category implements Parcelable {

    private String key;
    private String name;
    private String picture;
    private Bitmap bitmap;
    private CategoryAdapter categoryAdapter;

    public Category(String key, String name, String picture, CategoryAdapter categoryAdapter) {
        this.key = key;
        this.name = name;
        this.picture = picture;
        this.categoryAdapter = categoryAdapter;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(picture);
        dest.writeParcelable(bitmap, flags);
    }

    protected Category(Parcel in) {
        key = in.readString();
        name = in.readString();
        picture = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
