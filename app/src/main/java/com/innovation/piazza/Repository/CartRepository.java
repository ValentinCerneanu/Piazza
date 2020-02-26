package com.innovation.piazza.Repository;

import com.innovation.piazza.Domain.Item;

import java.util.HashMap;

public class CartRepository {
    private static final CartRepository instance = new CartRepository();

    private String storeKey;
    private HashMap<String, Item> itemsInCart;

    private CartRepository() {
        itemsInCart = new HashMap<>();
    }

    public static CartRepository getInstance() {
        return instance;
    }

    public boolean addItemInCart(Item itemInCart, String storeKey) {
        if(storeKey.equals(this.storeKey) || this.storeKey == null) {
            this.storeKey = storeKey;
            itemsInCart.put(itemInCart.getKey(), itemInCart);
            return true;
        }
        return false;
    }

    public void clearCart() {
        itemsInCart.clear();
    }

    public int getQuantity(String key) {
        if(itemsInCart.get(key) == null)
            return 0;
        return itemsInCart.get(key).getQuantity();
    }

    public String getStoreKey() {
        return storeKey;
    }

    public HashMap<String, Item> getItemsInCart() {
        return itemsInCart;
    }
}
