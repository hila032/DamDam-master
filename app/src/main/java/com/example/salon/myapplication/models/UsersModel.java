package com.example.salon.myapplication.models;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class UsersModel {
    private static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static String getId(){
        return auth.getCurrentUser().getUid();
    }
    public static String getEmail(){
        return auth.getCurrentUser().getEmail();
    }
    public static void register(String username, String password, OnSuccessListener<AuthResult> onSuccessListener, OnFailureListener onFailureListener){
        auth.createUserWithEmailAndPassword(username, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }


    public static void login(String username, String password, OnSuccessListener<AuthResult> onSuccessListener, OnFailureListener onFailureListener) {
        auth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
    public static void signOut(){
        auth.signOut();
    }


    public String toString() {
        return getEmail();
    }

}
