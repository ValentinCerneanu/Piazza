package com.innovation.piazza.Adapters;

public class StoresModel {
    String storeName;
    String storeDescription;
    String storeLogo;

    public StoresModel(String storeName, String storeDescription, String storeLogo) {
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.storeLogo = storeLogo;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public String getStoreLogo() {
        return storeLogo;
    }
}
