package com.example.salon.myapplication.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.AvailableUsersModel;
import com.example.salon.myapplication.models.UsersModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public final OnSuccessListener<AuthResult> ON_SUCCESS_LISTENER = new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {

//            Toast.makeText(LoginActivity.this, "login success" + authResult.toString(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
//                mLoding.dismiss();
                startActivity(intent);


        }
    };
    public final OnFailureListener ON_FAILURE_LISTENER = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            //mLoding.dismiss();
//            Toast.makeText(LoginActivity.this, "login F" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    TextView registerUser;
    EditText username, password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerUser = (TextView) findViewById(R.id.registerLogin);
        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        loginButton = (Button) findViewById(R.id.loginButton);

    }


    public void login(View view) {
        UsersModel.login(username.getText().toString(), password.getText().toString(), ON_SUCCESS_LISTENER, ON_FAILURE_LISTENER);
        final ProgressDialog mLoding = new ProgressDialog(LoginActivity.this);
        mLoding.setTitle("Processing...");
        mLoding.setMessage("Please wait...");
        mLoding.setCancelable(false);
        mLoding.setIndeterminate(true);
        mLoding.show();
    }


    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}