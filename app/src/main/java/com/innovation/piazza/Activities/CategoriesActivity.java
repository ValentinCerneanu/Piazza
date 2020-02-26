package com.innovation.piazza.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.innovation.piazza.Adapters.CategoryAdapter;
import com.innovation.piazza.Domain.Category;
import com.innovation.piazza.Domain.CategoryModel;
import com.innovation.piazza.Domain.Store;
import com.innovation.piazza.Domain.StoreModel;
import com.innovation.piazza.R;
import com.innovation.piazza.Services.FirebaseCommunication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class CategoriesActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRefToDatabase;

    private JSONObject categoriesJson = null;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categories = new ArrayList<>();
    private Store selectedStore;

    private GridView categoriesList;
    private TextView selectedStoreTextView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        selectedStore = getIntent().getParcelableExtra(StoreModel.SELECTED_STORE);
        selectedStoreTextView = findViewById(R.id.toolbar_text);
        selectedStoreTextView.setText(selectedStore.getName());

        categoryAdapter = new CategoryAdapter(categories, CategoriesActivity.this);
        categoriesList = findViewById(R.id.categories_list);
        categoriesList.setAdapter(categoryAdapter);

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent nextActivity;
                nextActivity = new Intent(getBaseContext(), ItemsActivity.class);
                Category selectedCategory = (Category) arg0.getItemAtPosition(position);
                nextActivity.putExtra(CategoryModel.SELECTED_CATEGORY, selectedCategory);
                nextActivity.putExtra(StoreModel.SELECTED_STORE, selectedStore.getKey());
                startActivity(nextActivity);
            }
        });

        getCategories();
    }

    private void getCategories() {
        database = FirebaseDatabase.getInstance();
        myRefToDatabase = database.getReference("Stores").child(selectedStore.getKey()).child("Categories");
        myRefToDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Gson gson = new Gson();
                    String gsonString = gson.toJson(dataSnapshot.getValue());
                    try {
                        categoriesJson = new JSONObject(gsonString);
                        categories.clear();
                        Iterator<String> iterator = categoriesJson.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            try {
                                JSONObject categoryJson = new JSONObject(categoriesJson.get(key).toString());
                                Category category = new Category(  key,
                                        categoryJson.getString(CategoryModel.NAME),
                                        categoryJson.getString(CategoryModel.PICTURE),
                                        categoryAdapter);

                                FirebaseCommunication firebaseCommunication = new FirebaseCommunication();
                                firebaseCommunication.getImageCategory(categoryJson.getString("picture"), category);

                                categories.add(category);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        categoryAdapter.notifyDataSetChanged();
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
