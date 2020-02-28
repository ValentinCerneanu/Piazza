package com.innovation.piazza.Activities;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.innovation.piazza.Adapters.OrderAdapter;
import com.innovation.piazza.Domain.Order;
import com.innovation.piazza.Domain.OrderModel;
import com.innovation.piazza.R;
import com.innovation.piazza.Repository.UserRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class OrderHistoryActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRefToDatabase;

    private ArrayList<Order> orders = new ArrayList<>();
    private OrderAdapter orderAdapter;

    private ListView orderList;
    private TextView OrderHistoryTextView;

    private JSONObject ordersJson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        OrderHistoryTextView = findViewById(R.id.toolbar_text);
        OrderHistoryTextView.setText("Istoric Comenzi");

        orderAdapter = new OrderAdapter(orders, OrderHistoryActivity.this);
        orderList = findViewById(R.id.order_list);
        orderList.setAdapter(orderAdapter);

        getOrders();
    }

    private void getOrders() {
        database = FirebaseDatabase.getInstance();
        myRefToDatabase = database.getReference("Orders");
        myRefToDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Gson gson = new Gson();
                    String gsonString = gson.toJson(dataSnapshot.getValue());
                    try {
                        ordersJson = new JSONObject(gsonString);
                        orders.clear();
                        Iterator<String> iterator = ordersJson.keys();
                        String userId = UserRepository.getInstance().getFirebareUserID();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            try {
                                JSONObject orderJson = new JSONObject(ordersJson.get(key).toString());
                                if(orderJson.getString(OrderModel.USER_ID).equals(userId)){
                                    Order order = new Order(userId,
                                                            orderJson.getString(OrderModel.STORE_KEY),
                                                            orderJson.getDouble(OrderModel.TOTAL_PRICE),
                                                            orderJson.getString(OrderModel.TIME),
                                                            orderJson.getString(OrderModel.ADDRESS),
                                                            orderJson.getString(OrderModel.STATUS));
                                    orders.add(order);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        orderAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}