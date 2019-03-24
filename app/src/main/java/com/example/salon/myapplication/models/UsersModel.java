package com.example.salon.myapplication.models;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.salon.myapplication.activities.LoginActivity;
import com.example.salon.myapplication.activities.RegisterActivity;
import com.example.salon.myapplication.activities.WelcomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class UsersModel {
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static String otherid;
    private static String otherEmail;


    public static void register(Context context, String username, String password, String nickname, OnSuccessListener<AuthResult> onSuccessListener, OnFailureListener onFailureListener){
        auth.createUserWithEmailAndPassword(username, password)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
        SharedPreferences preferencesNickname = context.getSharedPreferences("nickName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesNickname.edit();
        editor.putString(username, nickname);
        editor.commit();



    }

    public static String getId(){
        return auth.getCurrentUser().getUid();
    }
    public static String getEmail(){
        return auth.getCurrentUser().getEmail();
    }
    public static String getNickname(Context context){
        SharedPreferences preferencesNickname = context.getSharedPreferences("nickName", Context.MODE_PRIVATE);
        String name = preferencesNickname.getString(UsersModel.getEmail(), "Annonimus");
        return name;
    }

    public static void login(String username, String password, final Activity LoginActivity) {
        final ProgressDialog mLoding = new ProgressDialog(LoginActivity);
        mLoding.setTitle("Processing...");
        mLoding.setMessage("Please wait...");
        mLoding.setCancelable(false);
        mLoding.show();

        final OnSuccessListener<AuthResult> ON_SUCCESS_LISTENER = new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Toast.makeText(LoginActivity, "login success" + authResult.toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity, WelcomeActivity.class);
                mLoding.dismiss();
                LoginActivity.startActivity(intent);


            }
        };
        final OnFailureListener ON_FAILURE_LISTENER = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mLoding.setMessage("wrong password or email");
                Toast.makeText(LoginActivity, "login F" + e.getMessage(), Toast.LENGTH_LONG).show();
                mLoding.dismiss();
            }
        };

        auth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(ON_SUCCESS_LISTENER)
                .addOnFailureListener(ON_FAILURE_LISTENER);

    }
    public static void signOut(){
        auth.signOut();
    }


    public String toString() {
        return getEmail();
    }


}
