package com.example.salon.myapplication.activities;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.salon.myapplication.EDumGame;
import com.example.salon.myapplication.EIntant;
import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.ERoom;
import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.Dialogs;
import com.example.salon.myapplication.models.DumDumGameModel;
import com.example.salon.myapplication.models.DumDumRoomsModel;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.Sound;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DunDumGameActivity extends AppCompatActivity {

    private TextView TVreloodCounter, stutos;
    private Vibrator vibrator;
    private String roomId;
    private int reloodCounter = 0;
    private EPlayer player;
    private TextView playerName;
    private ImageButton shoot, relood, defance;
    private String myCard;
    private ValueEventListener changeCardListener;
    private ChildEventListener exitPlayerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        roomId = (String) this.getIntent().getExtras().get(EIntant.id.name());
        player = (EPlayer) this.getIntent().getExtras().get(EIntant.whoAmI.name());
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        playerName = (TextView) findViewById(R.id.playerName);
        stutos = (TextView) findViewById(R.id.stutos);
        playerName.setText(player.name());
        shoot = (ImageButton) findViewById(R.id.BTNgun);
        relood = (ImageButton) findViewById(R.id.BTNrelood);
        defance = (ImageButton) findViewById(R.id.BTNshild);
        TVreloodCounter = (TextView) findViewById(R.id.reloodCounter);
        Sound.setSound(this);
        enableShoot();
        TVreloodCounter.setText("0");
        enableClikes(true);

// קוד חורני בדרך
        exitPlayerListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                DumDumRoomsModel.getRoom(roomId).removeEventListener(exitPlayerListener);
                finish();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        DumDumRoomsModel.getRoom(roomId).addChildEventListener(exitPlayerListener);
        changeCardListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myCard = (String) dataSnapshot.child((player).name()).child(ERoom.card.name()).getValue();
                String otherPlayerCard = (String) dataSnapshot.child(EPlayer.getOtherPlayer(player).name()).child(ERoom.card.name()).getValue();

                if (myCard == null || otherPlayerCard == null) {
                    if(myCard == null){
                        stutos.setText("whiting for your choice");
                    }
                    if(myCard != null && otherPlayerCard == null){
                        stutos.setText("whiting for other player choice");
                    }
                    return;
                }
                enableClikes(true);
                handleWinner(myCard, player.name(), otherPlayerCard, EPlayer.getOtherPlayer(player).name());
                if (myCard != null && otherPlayerCard != null) {
                    DumDumRoomsModel.removeCard(roomId,player);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        DumDumRoomsModel.getRoom(roomId).addValueEventListener(changeCardListener);

    }
    public void handleWinner(String myCard, String myName, String otherPlayerCard, String otherPlayerName){
        if (myCard.equals(EDumGame.shoot.name())&& reloodCounter > 0) {
            if (myCard.equals(EDumGame.shoot.name()) && otherPlayerCard.equals(EDumGame.relood.name())) { //player 1 win
                //Toast.makeText(this, "the getWinner is: " + myName, LENGTH_SHORT).show();
                DumDumRoomsModel.getRoom(roomId).removeEventListener(exitPlayerListener);
                Dialogs.DumDumEndGame(this, myName,myCard,otherPlayerCard);
            }
            reloodCounter--;
            enableShoot();
            TVreloodCounter.setText(String.valueOf(reloodCounter));
        }
        else if (otherPlayerCard.equals(EDumGame.shoot.name()) && myCard.equals(EDumGame.relood.name())) { // other plyer win
            //Toast.makeText(this, "the getWinner is: " + otherPlayerName, LENGTH_SHORT).show();
            DumDumRoomsModel.getRoom(roomId).removeEventListener(exitPlayerListener);
            Dialogs.DumDumEndGame(this, otherPlayerName,myCard,otherPlayerCard);

        }
        else {
            Dialogs.tie(this,myCard,otherPlayerCard);
        }

        if (myCard.equals(EDumGame.relood.name())) {
            reloodCounter++;
            TVreloodCounter.setText(String.valueOf(reloodCounter));
            enableShoot();
        }
    }

    public void enableClikes(boolean isNeedToEnabled){
        shoot.setEnabled(isNeedToEnabled);
        relood.setEnabled(isNeedToEnabled);
        defance.setEnabled(isNeedToEnabled);
    }
    public void enableShoot() {
        if (reloodCounter <= 0) {
            shoot.setEnabled(false);
            shoot.setVisibility(View.INVISIBLE);
        } else {
            shoot.setEnabled(true);
            shoot.setVisibility(View.VISIBLE);
        }
    }
    public void shoot(View view) {
        DumDumGameModel.shoot(roomId, player);
        vibrator.vibrate(300);
        Sound.playGunhoot();
        enableClikes(false);

    }
    public void defance(View view) {
        DumDumGameModel.defance(roomId,player);
        vibrator.vibrate(300);
        enableClikes(false);
        //todo: add sound

    }
    public void relood(View view) {
        DumDumGameModel.relood(roomId,player);
        vibrator.vibrate(300);
        Sound.playRelood();
        enableClikes(false);
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
        DumDumRoomsModel.getRoom(roomId).removeEventListener(changeCardListener);
        DumDumRoomsModel.getRoom(roomId).removeEventListener(exitPlayerListener);
        DumDumRoomsModel.removeRoom(roomId);
    }
}
