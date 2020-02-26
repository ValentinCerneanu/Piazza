package com.innovation.piazza.Activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.innovation.piazza.Adapters.ItemAdapter;
import com.innovation.piazza.Domain.Item;
import com.innovation.piazza.R;
import com.innovation.piazza.Repository.CartRepository;

import java.util.ArrayList;
import java.util.Collection;

public class CartActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRefToDatabase;

    private ArrayList<Item> itemsInCart = new ArrayList<>();
    private ItemAdapter itemAdapter;


    private ListView itemsList;
    private TextView cartTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartTextView = findViewById(R.id.toolbar_text);
        cartTextView.setText("Shopping Cart");

        CartRepository cartRepository = CartRepository.getInstance();
        Collection<Item> itemInCartsFromRepo = cartRepository.getItemsInCart().values();
        itemsInCart = new ArrayList<>(itemInCartsFromRepo);

        itemAdapter = new ItemAdapter(itemsInCart, CartActivity.this, cartRepository.getStoreKey());
        itemsList = findViewById(R.id.items_list);
        itemsList.setAdapter(itemAdapter);
    }
}