package com.innovation.piazza.Activities;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.innovation.piazza.Adapters.ItemAdapter;
import com.innovation.piazza.Domain.Category;
import com.innovation.piazza.Domain.CategoryModel;
import com.innovation.piazza.Domain.Item;
import com.innovation.piazza.Domain.ItemModel;
import com.innovation.piazza.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemsActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRefToDatabase;

    private JSONObject itemsJson = null;
    private ArrayList<Item> items = new ArrayList<>();
    private ItemAdapter itemAdapter;

    private Category selectedCategory;

    private ListView itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        selectedCategory = getIntent().getParcelableExtra(CategoryModel.SELECTED_CATEGORY);

        getItems();

        itemAdapter = new ItemAdapter(items, ItemsActivity.this);
        itemsList = findViewById(R.id.items_list);
        itemsList.setAdapter(itemAdapter);
    }

    private void getItems() {
        database = FirebaseDatabase.getInstance();
        myRefToDatabase = database.getReference("Categories").child(selectedCategory.getKey());
        myRefToDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Gson gson = new Gson();
                    String gsonString = gson.toJson(dataSnapshot.getValue());
                    try {
                        itemsJson = new JSONObject(gsonString);
                        items.clear();
                        Iterator<String> iterator = itemsJson.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            try {
                                JSONObject itemJson = new JSONObject(itemsJson.get(key).toString());
                                Item item = new Item(  key,
                                        itemJson.getString(ItemModel.NAME),
                                        itemJson.getString(ItemModel.DESCRIPTION),
                                        itemJson.getDouble(ItemModel.PRICE),
                                        itemJson.getDouble(ItemModel.GRAMAJ),
                                        itemJson.getString(ItemModel.PICTURE),
                                        itemAdapter);
/*                                FirebaseCommunication firebaseCommunication = new FirebaseCommunication();
                                firebaseCommunication.getImageStore(storeJson.getString("logo_url"), store);*/
                                items.add(item);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        itemAdapter.notifyDataSetChanged();
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