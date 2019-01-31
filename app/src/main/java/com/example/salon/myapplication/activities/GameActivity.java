package com.example.salon.myapplication.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.AvailableUsersModel;
import com.example.salon.myapplication.models.Sound;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {

    private Vibrator vibrator;
    private String roomId;
    TextView tvTimer;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

//        tvTimer = (TextView) findViewById(R.id.textView);
//        thread = new Thread(timer);
//        thread.start();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Sound.setSound(this);

        roomId = (String) this.getIntent().getExtras().get("id");
        FirebaseDatabase.getInstance().getReference("Rooms")
                .child(roomId)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(GameActivity.this, UsersModel.getEmail(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    finish();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }
    public void shoot(View view) {
        
        FirebaseDatabase.getInstance().getReference("Rooms").child(roomId).child(UsersModel.getId()).setValue("shoot");
        vibrator.vibrate(300);
        Sound.playGunhoot();
    }
    public void defance(View view) {
        FirebaseDatabase.getInstance().getReference("Rooms").child(roomId).child(UsersModel.getId()).setValue("defance");
        vibrator.vibrate(300);

    }
    public void relood(View view) {
        FirebaseDatabase.getInstance().getReference("Rooms").child(roomId).child(UsersModel.getId()).setValue("relood");
        vibrator.vibrate(300);
        Sound.playRelood();
    }
//
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage (Message message){
//            int time = message.arg1;
//            tvTimer.setText(String.format("%20d", time/60 +
//            ";", String.format("%20d", time%60)));
//        }
//    };
//
//    private int count = 3;
//    Runnable timer = new Runnable() {
//        @Override
//        public void run() {
//            synchronized (this){
//                try {
//                    wait(1000);
//                    count--;
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            Message message = handler.obtainMessage();
//            message.arg1 = count;
//            handler.sendMessage(message);
//        }
//    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //FirebaseDatabase.getInstance().getReference("Rooms").child((String) this.getIntent().getExtras().get("id")).removeValue();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferencesIsInGame = getSharedPreferences("state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesIsInGame.edit();
        editor.putBoolean("isInGame", true);
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences preferencesIsInGame = getSharedPreferences("state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesIsInGame.edit();
        editor.putBoolean("isInGame", false);
        editor.commit();
    }
}
