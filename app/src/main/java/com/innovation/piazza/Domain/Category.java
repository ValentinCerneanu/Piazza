package com.innovation.piazza.Domain;

import android.graphics.Bitmap;
import com.innovation.piazza.Adapters.CategoryAdapater;

public class Category {

    private String key;
    private String name;
    private String picture;
    private Bitmap bitmap;
    private CategoryAdapater categoryAdapater;


    public Category(String key, String name, String picture, CategoryAdapater categoryAdapater) {
        this.key = key;
        this.name = name;
        this.picture = picture;
        this.categoryAdapater = categoryAdapater;

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
        categoryAdapater.notifyDataSetChanged();
    }

}
