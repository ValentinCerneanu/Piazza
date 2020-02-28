package com.innovation.piazza.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.innovation.piazza.Adapters.ItemAdapter;
import com.innovation.piazza.Domain.Item;
import com.innovation.piazza.Domain.Order;
import com.innovation.piazza.R;
import com.innovation.piazza.Repository.CartRepository;
import com.innovation.piazza.Repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

public class CartActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRefToDatabase;

    private ArrayList<Item> itemsInCart = new ArrayList<>();
    private ItemAdapter itemAdapter;

    private ListView itemsList;
    private TextView cartTextView;
    private Button sentOrderButton;
    private TextView totalPrice;

    private CartRepository cartRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRepository = CartRepository.getInstance();
        Collection<Item> itemInCartsFromRepo = cartRepository.getItemsInCart().values();
        itemsInCart = new ArrayList<>(itemInCartsFromRepo);

        cartTextView = findViewById(R.id.toolbar_text);
        cartTextView.setText("Shopping Cart");

        totalPrice = findViewById(R.id.total_price);
        totalPrice.setText("Total: " + cartRepository.getTotalPriceString());
        cartRepository.setTotalTextView(totalPrice);

        itemAdapter = new ItemAdapter(itemsInCart, CartActivity.this, cartRepository.getStoreKey());
        itemsList = findViewById(R.id.items_list);
        itemsList.setAdapter(itemAdapter);

        sentOrderButton = findViewById(R.id.sent_order_btn);
        cartRepository.setSendOrderButton(sentOrderButton);

        if(itemsInCart.isEmpty()) {
            sentOrderButton.setEnabled(false);
        }

        sentOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order(UserRepository.getInstance().getFirebareUserID(), cartRepository.getStoreKey(), itemsInCart, Double.valueOf(cartRepository.getTotalPriceString()));
                database = FirebaseDatabase.getInstance();
                myRefToDatabase = database.getReference("Orders");
                myRefToDatabase.push().setValue(order).addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        AlertDialog.Builder builder = new AlertDialog.Builder((Context)CartActivity.this);
                        builder.setTitle("Comanda");
                        builder.setMessage("Comanda va fi preluata in curand!");
                        builder.setPositiveButton("OK", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        cartRepository.clearCart();
                        itemsInCart.clear();
                        itemAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
            }
        });
    }

    public void refreshUI(Item a) {
        itemsInCart.remove(a);
        itemAdapter.notifyDataSetChanged();
    }
}