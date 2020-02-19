package com.innovation.piazza.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.innovation.piazza.R;

public class RegisterWithEmailActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mName;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button createAccount;

    private FirebaseDatabase database;
    private DatabaseReference myRefToDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mName = (EditText) findViewById(R.id.input_name_register);
        mEmail = (EditText) findViewById(R.id.input_email_register);
        mPhone = (EditText) findViewById(R.id.input_phone_register);
        mPhone.setTransformationMethod(null);
        mPassword = (EditText) findViewById(R.id.input_password1_register);
        mConfirmPassword = (EditText) findViewById(R.id.input_password2_register);
        mConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    createAccount();
                    return true;
                }
                return false;
            }
        });
        createAccount = findViewById(R.id.btn_signup);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    public void createAccount() {
        try {
            String name = mName.getText().toString();
            String email = mEmail.getText().toString();
            String phone = mPhone.getText().toString();
            String password = mPassword.getText().toString();
            String passwordConfirmed = mConfirmPassword.getText().toString();

            View focusView = null;
            boolean cancel = false;
            //name
            if (TextUtils.isEmpty(name)) {
                mName.setError(getString(R.string.error_field_required));
                focusView = mName;
                cancel = true;
            }
            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmail.setError(getString(R.string.error_field_required));
                focusView = mEmail;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmail.setError(getString(R.string.error_invalid_email));
                focusView = mEmail;
                cancel = true;
            }
            //phone
            if (TextUtils.isEmpty(phone)) {
                mPhone.setError(getString(R.string.error_field_required));
                focusView = mPhone;
                cancel = true;
            }
            // Check for a valid password, if the user entered one.
            if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                mPassword.setError(getString(R.string.error_invalid_password));
                focusView = mPassword;
                cancel = true;
            }
            if (!password.equals(passwordConfirmed)) {
                mConfirmPassword.setError(getString(R.string.error_password_not_match));
                focusView = mConfirmPassword;
                cancel = true;
            }
            if (cancel) {
                // There was an error; don't attempt login and focus the first
                // form field with an error.
                focusView.requestFocus();
            } else {
                createAccout(email, password);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void createAccout(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            Intent nextActivity;
                            nextActivity = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(nextActivity);
                            finish();
                            firebaseUser.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("", "Email sent.");
                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "createUserWithEmail:failure", task.getException());
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterWithEmailActivity.this);
                            builder1.setMessage("There was an error creating the account. Please retry");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Retry",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder1.create();
                            alert.show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterWithEmailActivity.this);
                        builder1.setMessage("There was an error creating the account. Please retry");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Retry",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder1.create();
                        alert.show();
                    }
                });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    public void goBackToLogin(View v){
        Intent nextActivity;
        nextActivity = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(nextActivity);
        finish();
    }

}


