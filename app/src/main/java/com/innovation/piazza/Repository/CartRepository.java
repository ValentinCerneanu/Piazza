package com.innovation.piazza.Repository;

import com.innovation.piazza.Domain.Item;

import java.util.ArrayList;
import java.util.Collection;
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
            if(itemInCart.getQuantity() == 0)
                itemsInCart.remove(itemInCart.getKey());
            else
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

    public double getTotalPrice() {
        Collection<Item> itemInCartsFromRepo = itemsInCart.values();
        ArrayList<Item> itemsInCartArrayList = new ArrayList<>(itemInCartsFromRepo);
        double totalPrice = 0;
        for(Item item : itemsInCartArrayList) {
            totalPrice = totalPrice + item.getQuantity() * item.getPrice();
        }
        return totalPrice;
    }
}
