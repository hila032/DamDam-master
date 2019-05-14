package com.example.salon.myapplication.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.UsersModel;

public class RegisterActivity extends AppCompatActivity {
    private EditText mail, password, nikename;
    private Button registerButton, loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mail = (EditText) findViewById(R.id.usernameReg);
        password = (EditText) findViewById(R.id.passwordReg);
        nikename = (EditText) findViewById(R.id.nicknameReg);
        registerButton = (Button) findViewById(R.id.registerButtonReg);
        loginButton = (Button) findViewById(R.id.loginReg);
    }

    public void register(View view) {
        String name = nikename.getText().toString();
        UsersModel.register(this, mail.getText().toString(), password.getText().toString(), name);
    }

    public void login(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
