package com.example.salon.myapplication.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.UsersModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText username, password;
    Button registerButton;
    TextView login;

    public final OnFailureListener ON_FAILURE_LISTENER = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(RegisterActivity.this, "registration F" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    public final OnSuccessListener<AuthResult> ON_SUCCESS_LISTENER = new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            Toast.makeText(RegisterActivity.this, "registration Sucsses" + authResult.getUser(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.usernameReg);
        password = (EditText) findViewById(R.id.passwordReg);
        registerButton = (Button) findViewById(R.id.registerButtonReg);
        login = (TextView) findViewById(R.id.loginReg);
    }

    public void register(View view) {
        UsersModel.register(username.getText().toString(), password.getText().toString(), ON_SUCCESS_LISTENER, ON_FAILURE_LISTENER);
    }

    public void login(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
