package com.innovation.piazza.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.innovation.piazza.Adapters.StoreAdapter;
import com.innovation.piazza.Domain.StoreModel;
import com.innovation.piazza.R;
import com.innovation.piazza.Services.LocationService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton burgerBtn;

    private FirebaseDatabase database;
    private DatabaseReference myRefToDatabase;

    private JSONObject stores = null;
    private ArrayList<StoreModel> storeModels = new ArrayList<>();
    private StoreAdapter storeAdapter;

    private ListView storesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveLocalData();
        setupToolbarAndDrawer();

        getAddress();

        storeAdapter = new StoreAdapter(storeModels, MainActivity.this);
        storesList = findViewById(R.id.stores_list);
        storesList.setAdapter(storeAdapter);

        storesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent nextActivity;
                nextActivity = new Intent(getBaseContext(), SplashActivity.class);
                StoreModel selectedStore = (StoreModel) arg0.getItemAtPosition(position);
                startActivity(nextActivity);
            }
        });

        getStores();
    }

    private void getStores() {
        database = FirebaseDatabase.getInstance();
        myRefToDatabase = database.getReference("Stores");
        myRefToDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Gson gson = new Gson();
                    String gsonString = gson.toJson(dataSnapshot.getValue());
                    try {
                        stores = new JSONObject(gsonString);
                        storeModels.clear();
                        Iterator<String> iterator = stores.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            try {
                                JSONObject storeJson = new JSONObject(stores.get(key).toString());
                                StoreModel store = new StoreModel(  stores.get(key).toString(),
                                                                    storeJson.getString("name"),
                                                                    storeJson.getString("address"),
                                                                    storeJson.getString("logo_url"),
                                                                    storeJson.getString("latitude"),
                                                                    storeJson.getString("longitude"));
                                storeModels.add(store);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        storeAdapter.notifyDataSetChanged();
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

    private void getAddress(){
        LocationService locationService = new LocationService(this);
        locationService.getAddressByLocation();
        EditText editText = (EditText) findViewById(R.id.calculatedLocation);
        editText.setText(locationService.getAddressLine() , TextView.BufferType.EDITABLE);
    }

    private void saveLocalData() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        SharedPreferences sharedPreferences = getSharedPreferences("FirebaseUser", MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("id", firebaseUser.getUid());
        ed.putString("name", firebaseUser.getDisplayName());
        ed.putString("email", firebaseUser.getEmail());
        //ed.putString("phoneNumber",  user.getPhoneNumber());
        ed.commit();
    }

    private void setupToolbarAndDrawer(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();

        drawerLayout = findViewById(R.id.drawerlayout);

        View headerLayout = navigationView.getHeaderView(0);
        TextView userEditText = (TextView) headerLayout.findViewById(R.id.user);
        SharedPreferences sharedPreferences = getSharedPreferences("FirebaseUser", MODE_PRIVATE);
        String userName = sharedPreferences.getString("name", "");
        if (userName != null) {
            userEditText.setText("Hello, " + userName +"!");
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Closing drawer on item click
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_shop_list: {
                        break;
                    }

                    case R.id.nav_promos: {
/*                        Intent nextActivity;
                        nextActivity = new Intent(getBaseContext(), .class);
                        startActivity(nextActivity);*/
                        break;
                    }

                    case R.id.nav_orders_history: {
/*                        Intent nextActivity;
                        nextActivity = new Intent(getBaseContext(), .class);
                        startActivity(nextActivity);*/
                        break;
                    }

                    case R.id.nav_return_policy: {
/*                        Intent nextActivity;
                        nextActivity = new Intent(getBaseContext(), .class);
                        startActivity(nextActivity);*/
                        break;
                    }

                    case R.id.nav_cookie_policy: {
/*                        Intent nextActivity;
                        nextActivity = new Intent(getBaseContext(), .class);
                        startActivity(nextActivity);*/
                        break;
                    }

                    case R.id.nav_logout: {
                        FirebaseAuth.getInstance().signOut();
                        Intent nextActivity = new Intent(getBaseContext(), SignInMethodActivity.class);
                        nextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(nextActivity);
                        finishAffinity();
                    }
                    return true;
                }
                return false;
            }
        });

        burgerBtn = (ImageButton) findViewById(R.id.hamburger_btn);
        burgerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout .openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LocationService.LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,"Location Permission Granted", Toast.LENGTH_SHORT).show();
                getAddress();
            }
            else {
                //denied
            }
        }
    }
}