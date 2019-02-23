package com.example.salon.myapplication.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.ESharedPreferences;
import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.GameModel;
import com.example.salon.myapplication.models.RoomsModel;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.Sound;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {

    private Vibrator vibrator;
    private String roomId;
    private final String shoot = "shoot";
    private final String defance = "defance";
    private final String relood = "relood";
    private int reloodCounter = 0;
    private TextView timmer;
    private int timerSec = 3;
    private EPlayer player;
    GameModel gameModel = new GameModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        roomId = (String) this.getIntent().getExtras().get("id");
        player = (EPlayer) this.getIntent().getExtras().get("whoAmI");
        timmer = (TextView) findViewById(R.id.timer);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Sound.setSound(this);



        RoomsModel.getRoom(roomId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //Toast.makeText(GameActivity.this, "", Toast.LENGTH_SHORT).show();
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
        countDownTimer.start();

    }
    public void shoot(View view) {
        RoomsModel.setPlayerValueInGame(shoot, roomId, player);
        vibrator.vibrate(300);
        Sound.playGunhoot();
    }
    public void defance(View view) {
        RoomsModel.setPlayerValueInGame(defance, roomId, player);
        vibrator.vibrate(300);

    }
    public void relood(View view) {
        RoomsModel.setPlayerValueInGame(relood, roomId, player);
        vibrator.vibrate(300);
        Sound.playRelood();
    }

    CountDownTimer countDownTimer = new CountDownTimer(5000,1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            timmer.setText(String.valueOf(timerSec));
            timerSec--;
        }

        @Override
        public void onFinish() {
            timerSec = 3;
           check();


        }
    };

    private void check() {

        RoomsModel.getRoom(roomId).addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String otherPlayer = ((String) dataSnapshot.child(EPlayer.getOtherPlayer(player).name()).child("card").getValue());
                    Toast.makeText(GameActivity.this, otherPlayer, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferencesModel.setIsInGame(true,this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferencesModel.setIsInGame(false, this);
        RoomsModel.removeRoom(roomId);

    }
}
