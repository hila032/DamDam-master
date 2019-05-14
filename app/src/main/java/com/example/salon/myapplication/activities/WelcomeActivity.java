package com.example.salon.myapplication.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.DumDumAvailableUsersModel;
import com.example.salon.myapplication.models.MyReceiver;
import com.example.salon.myapplication.models.NetworkUtil;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {
    private Intent intentService;
    private ComponentName service;
    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        MyReceiver = new MyReceiver();
        broadcastIntent();
        SharedPreferencesModel.setDefault(this);
        Toast.makeText(WelcomeActivity.this, "welcome back " + UsersModel.getNickname(this), Toast.LENGTH_LONG).show();



    }

    public void broadcastIntent() {
        intentService = new Intent(this, NetworkUtil.class);
        service = startService(intentService);
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void share(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "I challenging you to a duel");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void dumDumUserList(View view) {
        Intent intent = new Intent(WelcomeActivity.this, DumDumEnemyChoseActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        UsersModel.signOut();
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void profile(View view) {
        Intent intent = new Intent(WelcomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void ticTacUserList(View view) {
        Intent intent = new Intent(WelcomeActivity.this, TicTacEnemyChoseActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
        stopService(intentService);
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastIntent();
    }
}
