package com.innovation.piazza.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.innovation.piazza.Adapters.CategoryAdapater;
import com.innovation.piazza.Domain.Category;
import com.innovation.piazza.Domain.CategoryModel;
import com.innovation.piazza.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CategoriesActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRefToDatabase;

    private JSONObject categoriesJson = null;
    private CategoryAdapater categoryAdapater;
    private ArrayList<Category> categories = new ArrayList<>();

    private GridView categoriesList;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categories);

        categoryAdapater = new CategoryAdapater(categories, CategoriesActivity.this);
        categoriesList = findViewById(R.id.categories_list);
        categoriesList.setAdapter(categoryAdapater);

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent nextActivity;
                nextActivity = new Intent(getBaseContext(), SplashActivity.class);
                Category selectedCategory = (Category) arg0.getItemAtPosition(position);
                startActivity(nextActivity);
            }
        });

        getCategories();

    }

    private void getCategories() {
        database = FirebaseDatabase.getInstance();
        myRefToDatabase = database.getReference("Stores/AuchanAfi/Categories");
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
                                        categoriesJson.getString(CategoryModel.NAME),
                                        categoriesJson.getString(CategoryModel.PICTURE),
                                        categoryAdapater);
                                /*TODO de luat imaginea din firebase (PS: nu le-am pus inca
                                FirebaseCommunication firebaseCommunication = new FirebaseCommunication();
                                firebaseCommunication.getImageCategory(categoriesJson.getString("picture", category));
                                 */
                                categories.add(category);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        categoryAdapater.notifyDataSetChanged();
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
