package com.example.salon.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.DumDumAvailableUsersModel;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SharedPreferencesModel.setDefault(this);
        TextView email = (TextView) findViewById(R.id.emailWelcome);
        auth = FirebaseAuth.getInstance();
        email.setText(auth.getCurrentUser().getEmail()); //todo: might prodoce nullPointerException

        Toast.makeText(WelcomeActivity.this, "welcome back " + UsersModel.getNickname(this), Toast.LENGTH_LONG).show();

    }

    public void userList(View view) {
        Intent intent = new Intent(WelcomeActivity.this, DumDumEnemyChoseActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        DumDumAvailableUsersModel.removeDumDumUser(UsersModel.getId());
        UsersModel.signOut();
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void profile(View view) {
        Intent intent = new Intent(WelcomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void ticTac(View view) {
        Intent intent = new Intent(WelcomeActivity.this, TicTacEnemyChoseActivity.class);
        startActivity(intent);
    }
}
