package com.example.tccadoteumaarvore.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {
    private static FirebaseAuth auth;
    private static DatabaseReference reference;

    public static FirebaseAuth getFirebaseAuth(){
       if (auth == null){
           auth = FirebaseAuth.getInstance();
       }
       return auth;
    }

    public static DatabaseReference getFirebaseRef(){
        if (reference == null){
            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }

}
