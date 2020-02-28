package com.innovation.piazza.Repository;

import com.google.firebase.auth.FirebaseAuth;

public class UserRepository {
    private static final UserRepository instance = new UserRepository();

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        return instance;
    }

    public String getFirebareUserID(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        return mAuth.getCurrentUser().getUid();
    }
}
