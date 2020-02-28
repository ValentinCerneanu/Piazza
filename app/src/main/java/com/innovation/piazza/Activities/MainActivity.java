package com.innovation.piazza.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.innovation.piazza.Domain.Store;
import com.innovation.piazza.Domain.StoreModel;
import com.innovation.piazza.R;
import com.innovation.piazza.Services.FirebaseCommunication;
import com.innovation.piazza.Services.LocationService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton burgerBtn;
    private EditText addressEditText;

    private FirebaseDatabase database;
    private DatabaseReference myRefToDatabase;

    private JSONObject storesJson = null;
    private ArrayList<Store> stores = new ArrayList<>();
    private StoreAdapter storeAdapter;

    private ListView storesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveLocalData();
        setupToolbarAndDrawer();

        getAddress();

        storeAdapter = new StoreAdapter(stores, MainActivity.this);
        storesList = findViewById(R.id.stores_list);
        storesList.setAdapter(storeAdapter);

        storesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent nextActivity;
                nextActivity = new Intent(getBaseContext(), CategoriesActivity.class);
                Store selectedStore = (Store) arg0.getItemAtPosition(position);
                nextActivity.putExtra(StoreModel.SELECTED_STORE, selectedStore);
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
                        storesJson = new JSONObject(gsonString);
                        stores.clear();
                        Iterator<String> iterator = storesJson.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            try {
                                JSONObject storeJson = new JSONObject(storesJson.get(key).toString());
                                Store store = new Store(  key,
                                                                    storeJson.getString(StoreModel.NAME),
                                                                    storeJson.getString(StoreModel.ADDRESS),
                                                                    storeJson.getString(StoreModel.LOGO_URL),
                                                                    storeJson.getString(StoreModel.LATITUDE),
                                                                    storeJson.getString(StoreModel.LONGITUDE),
                                                                    storeAdapter);
                                FirebaseCommunication firebaseCommunication = new FirebaseCommunication();
                                firebaseCommunication.getImageStore(storeJson.getString("logo_url"), store);
                                stores.add(store);
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
        LocationService locationService = LocationService.getInstance();
        locationService.getAddressByLocation(this);
        addressEditText = (EditText) findViewById(R.id.calculated_location);
        addressEditText.setText(locationService.getAddressLine() , TextView.BufferType.EDITABLE);
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

        drawerLayout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closeKeyboard(MainActivity.this, v);
                return false;
            }
        });

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
                        Intent nextActivity;
                        nextActivity = new Intent(getBaseContext(), OrderHistoryActivity.class);
                        startActivity(nextActivity);
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
                closeKeyboard(MainActivity.this, v);
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

    private void closeKeyboard(Context context, View view) {
        addressEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}