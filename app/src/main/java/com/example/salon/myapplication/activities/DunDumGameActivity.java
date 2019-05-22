package com.example.salon.myapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
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
import com.example.salon.myapplication.models.InvitesModel;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.Sound;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DunDumGameActivity extends AppCompatActivity {

    private TextView TVreloadCounter, status;
    private Vibrator vibrator;
    private String roomId;
    private int reloadCounter = 0;
    private EPlayer player;
    private TextView playerName;
    private ImageButton shoot, reload, defence;
    private String myCard;
    private ValueEventListener changeCardListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        roomId = (String) this.getIntent().getExtras().get(EIntant.ID.name());
        player = (EPlayer) this.getIntent().getExtras().get(EIntant.WHO_AM_I.name());
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        playerName = (TextView) findViewById(R.id.playerName);
        status = (TextView) findViewById(R.id.status);
        playerName.setText(player.name());
        shoot = (ImageButton) findViewById(R.id.BTNgun);
        reload = (ImageButton) findViewById(R.id.BTNrelood);
        defence = (ImageButton) findViewById(R.id.BTNshild);
        TVreloadCounter = (TextView) findViewById(R.id.reloodCounter);
        enableShoot();
        TVreloadCounter.setText("0");
        enableClikes(true);

        changeCardListener = new ValueEventListener() {
            Intent intent = new Intent(DunDumGameActivity.this, DumDumEnemyChoseActivity.class);
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null ){
                    startActivity(intent);
                }
                myCard = (String) dataSnapshot.child((player).name()).child(ERoom.CARD.name()).getValue();
                String otherPlayerCard = (String) dataSnapshot.child(EPlayer.getOtherPlayer(player).name()).child(ERoom.CARD.name()).getValue();

                if (myCard == null || otherPlayerCard == null) {
                    if(myCard == null){
                        status.setText("whiting for your choice");
                    }
                    if(myCard != null && otherPlayerCard == null){
                        status.setText("whiting for other player choice");
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

    @Override
    protected void onStart() {
        super.onStart();
        Sound.createSoundPool(this);
        InvitesModel.removeInvitation(UsersModel.getId());

    }

    public void handleWinner(String myCard, String myName, String otherPlayerCard, String otherPlayerName){
        if (myCard.equals(EDumGame.SHOOT.name())&& reloadCounter > 0) {
            if (myCard.equals(EDumGame.SHOOT.name()) && otherPlayerCard.equals(EDumGame.RELOAD.name())) { //player 1 win
                DumDumRoomsModel.getRoom(roomId).removeEventListener(changeCardListener);
                Dialogs.handelGame(this,myCard,otherPlayerCard, myName);
            }
            reloadCounter--;
            enableShoot();
            TVreloadCounter.setText(String.valueOf(reloadCounter));
        }
        else if (otherPlayerCard.equals(EDumGame.SHOOT.name()) && myCard.equals(EDumGame.RELOAD.name())) { // other plyer win
            DumDumRoomsModel.getRoom(roomId).removeEventListener(changeCardListener);
            Dialogs.handelGame(this ,myCard,otherPlayerCard, otherPlayerName);
        }else {
            Dialogs.handelGame(this, myCard, otherPlayerCard, null);
        }
        if (myCard.equals(EDumGame.RELOAD.name())) {
            reloadCounter++;
            TVreloadCounter.setText(String.valueOf(reloadCounter));
            enableShoot();
        }

    }

    public void enableClikes(boolean isNeedToEnabled){
        shoot.setEnabled(isNeedToEnabled);
        reload.setEnabled(isNeedToEnabled);
        defence.setEnabled(isNeedToEnabled);
    }
    public void enableShoot() {
        if (reloadCounter <= 0) {
            shoot.setEnabled(false);
            shoot.setVisibility(View.INVISIBLE);
        } else {
            shoot.setEnabled(true);
            shoot.setVisibility(View.VISIBLE);
        }
    }
    public void shoot(View view) {
        DumDumGameModel.setPlayerValueInGame(EDumGame.SHOOT.name(),roomId, player);
        vibrator.vibrate(300);
        Sound.playGunShoot();
        enableClikes(false);

    }
    public void defance(View view) {
        DumDumGameModel.setPlayerValueInGame(EDumGame.DEFENCE.name(),roomId, player);
        vibrator.vibrate(300);
        enableClikes(false);
        Sound.playShield();

    }
    public void relood(View view) {
        DumDumGameModel.setPlayerValueInGame(EDumGame.RELOAD.name(),roomId, player);
        vibrator.vibrate(300);
        Sound.playReload();
        enableClikes(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DumDumRoomsModel.removeRoom(roomId);
        SharedPreferencesModel.setIsInGame(false, this);
        DumDumRoomsModel.getRoom(roomId).removeEventListener(changeCardListener);
        Sound.release();
    }

    public void homePage(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}
