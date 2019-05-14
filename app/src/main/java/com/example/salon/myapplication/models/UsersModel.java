package com.example.salon.myapplication.models;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.salon.myapplication.ESharedPreferences;
import com.example.salon.myapplication.activities.WelcomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UsersModel {
    public static void register(final Activity registerActivity, String username, String password, String nickname) {
        final ProgressDialog mLoding = new ProgressDialog(registerActivity);
        mLoding.setTitle("Processing...");
        mLoding.setMessage("Please wait...");
        mLoding.setCancelable(false);
        mLoding.show();

        final OnSuccessListener<AuthResult> ON_SUCCESS_LISTENER = new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(registerActivity, "registration succeed", Toast.LENGTH_LONG).show();
                mLoding.dismiss();
                Intent intent = new Intent(registerActivity, WelcomeActivity.class);
                registerActivity.startActivity(intent);
            }
        };
        final OnFailureListener ON_FAILURE_LISTENER = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mLoding.setMessage("registration fail");
                Toast.makeText(registerActivity, "registration fail", Toast.LENGTH_LONG).show();
                mLoding.dismiss();
            }
        };
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(username, password)
                .addOnSuccessListener(ON_SUCCESS_LISTENER)
                .addOnFailureListener(ON_FAILURE_LISTENER);
        SharedPreferences preferencesNickname = registerActivity.getSharedPreferences(ESharedPreferences.NICKNAME.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesNickname.edit();
        editor.putString(username, nickname);
        editor.commit();


    }

    public static String getId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static String getEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    public static String getNickname(Context context) {
        SharedPreferences preferencesNickname = context.getSharedPreferences(ESharedPreferences.NICKNAME.name(), Context.MODE_PRIVATE);
        String name = preferencesNickname.getString(UsersModel.getEmail(), ESharedPreferences.ANONYMOUS.name());
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

                Toast.makeText(LoginActivity, "login success", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity, WelcomeActivity.class);
                mLoding.dismiss();
                LoginActivity.startActivity(intent);


            }
        };
        final OnFailureListener ON_FAILURE_LISTENER = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mLoding.setMessage("wrong password or email");
                Toast.makeText(LoginActivity, "login fail", Toast.LENGTH_LONG).show();
                mLoding.dismiss();
            }
        };

        FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(ON_SUCCESS_LISTENER)
                .addOnFailureListener(ON_FAILURE_LISTENER);

    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }


}
