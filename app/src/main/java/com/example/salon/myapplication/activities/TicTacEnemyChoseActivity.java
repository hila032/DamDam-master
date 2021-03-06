package com.example.salon.myapplication.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.salon.myapplication.EIntant;
import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.InvitesModel;
import com.example.salon.myapplication.models.TicTacModle;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.TicTacAvailableUsersModle;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class TicTacEnemyChoseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_enemy_chose);
        SharedPreferencesModel.setIsInGame(false, this);

        TicTacModle.getTicTactRoom(UsersModel.getId()).child(EPlayer.PLAYER1.name()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isInGame = SharedPreferencesModel.getIsInGame(TicTacEnemyChoseActivity.this);

                if (dataSnapshot.getValue() != null && !isInGame) {
                    Intent intent = new Intent(TicTacEnemyChoseActivity.this, TicTacGameActivity.class);
                    intent.putExtra(EIntant.ID.name(), UsersModel.getId());
                    intent.putExtra(EIntant.WHO_AM_I.name(), EPlayer.PLAYER1);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        InvitesModel.listenToInvitation(UsersModel.getId(), TicTacEnemyChoseActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        TicTacAvailableUsersModle.removeTicTacUser(UsersModel.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        TicTacAvailableUsersModle.addTicTacUserToAvailableUsers(UsersModel.getId(), UsersModel.getEmail());

    }
}

