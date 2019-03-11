package com.example.salon.myapplication.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.myapplication.EDumGame;
import com.example.salon.myapplication.EIntant;
import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.ERoom;
import com.example.salon.myapplication.Emassege;
import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.GameModel;
import com.example.salon.myapplication.models.RoomsModel;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.Sound;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {

    private Vibrator vibrator;
    private String roomId;
    private int reloodCounter = 0;
    private TextView timmer;
    private static int timerSec = 3;
    private EPlayer player;
    private TextView playerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        roomId = (String) this.getIntent().getExtras().get(EIntant.id.name());
        player = (EPlayer) this.getIntent().getExtras().get(EIntant.whoAmI.name());
        timmer = (TextView) findViewById(R.id.timer);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        playerName = (TextView) findViewById(R.id.playerName);
        playerName.setText(UsersModel.getNickname(this));
        Sound.setSound(this);

//         Handler gameOverHandler = new Handler(){
//
//            @Override
//            public void handleMessage(Message msg) {
//
//                String name = msg.getData().getString(Emassege.name.name());
//                if (!name.equals(Emassege.tie.name())) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
//                    builder.setMessage("Game over, the winner is: " + name);
//                    builder.setTitle("Game Over");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(GameActivity.this, EnemychoseActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                }
//                else {
//                    countDownTimer.start();
//
//                }
//            }
//        };

        RoomsModel.getRoom(roomId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
// herre has a problem
                if (RoomsModel.isRoomExist(roomId)) {

                    String myCard = (String) dataSnapshot.child((player).name()).child(ERoom.card.name()).getValue();
                    String otherPlayerCard = (String) dataSnapshot.child(EPlayer.getOtherPlayer(player).name()).child(ERoom.card.name()).getValue();
                    String winner = GameModel.getWinner(myCard, player.name(), otherPlayerCard, EPlayer.getOtherPlayer(player).name());
                    Toast.makeText(GameActivity.this, winner, Toast.LENGTH_SHORT).show();

                }
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
        RoomsModel.setPlayerValueInGame(EDumGame.shoot.name(), roomId, player);
        vibrator.vibrate(300);
        Sound.playGunhoot();
    }
    public void defance(View view) {
        RoomsModel.setPlayerValueInGame(EDumGame.defance.name(), roomId, player);
        vibrator.vibrate(300);

    }
    public void relood(View view) {
        RoomsModel.setPlayerValueInGame(EDumGame.relood.name(), roomId, player);
        vibrator.vibrate(300);
        Sound.playRelood();
    }
// herre has a problem
    CountDownTimer  countDownTimer = new CountDownTimer(5000,1000) {

        @Override
        public void onTick(long millisUntilFinished) {

            timmer.setText(String.valueOf(millisUntilFinished/1000));

        }

        @Override
        public void onFinish() {
            RoomsModel.setPlayerValueInGame("", roomId, player);


        }
    };


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
