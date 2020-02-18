package com.innovation.piazza.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.innovation.piazza.R;

public class SignInMethodActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_method);

        Button emailSignInMethod = (Button) findViewById(R.id.loginEmail);
        emailSignInMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity;
                    nextActivity = new Intent(getBaseContext(), RegisterWithEmailActivity.class);
                    startActivity(nextActivity);
            }
        });

        Button alreadyHaveAccount = (Button) findViewById(R.id.alreadyHaveAccountBtn);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity;
                nextActivity = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(nextActivity);
            }
        });
    }
}
