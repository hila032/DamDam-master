package com.example.salon.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.salon.myapplication.EIntant;
import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.ERoom;
import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.DumDumAvailableUsersModel;
import com.example.salon.myapplication.models.InvitesModel;
import com.example.salon.myapplication.models.DumDumRoomsModel;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DumDumEnemyChoseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enamychose);
        SharedPreferencesModel.setIsInGame(false, this);

        DumDumRoomsModel.getRoom(UsersModel.getId()).child(EPlayer.PLAYER1.name()).child(ERoom.ID.name()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isInGame = SharedPreferencesModel.getIsInGame(DumDumEnemyChoseActivity.this);

                if (dataSnapshot.getValue() != null && !isInGame) {
                    Intent intent = new Intent(DumDumEnemyChoseActivity.this, DunDumGameActivity.class);
                    intent.putExtra(EIntant.ID.name(), UsersModel.getId());
                    intent.putExtra(EIntant.WHO_AM_I.name(), EPlayer.PLAYER1);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        InvitesModel.listenToInvitation(UsersModel.getId(), DumDumEnemyChoseActivity.this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        DumDumAvailableUsersModel.removeDumDumUser(UsersModel.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        DumDumAvailableUsersModel.addDumDumUserToAvailableUsers(UsersModel.getId(), UsersModel.getEmail());

    }
}