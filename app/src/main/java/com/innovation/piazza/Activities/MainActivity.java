package com.innovation.piazza.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.innovation.piazza.R;
import com.innovation.piazza.Services.LocationService;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton burgerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        saveLocalData();
        setupToolbarAndDrawer();

        getAddress();
    }

    private void getAddress(){
        LocationService locationService = new LocationService(getApplicationContext());
        locationService.getAddressByLocation();
        EditText editText = (EditText)findViewById(R.id.calculatedLocation);
        editText.setText( locationService.getAddressLine() , TextView.BufferType.EDITABLE);

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
}