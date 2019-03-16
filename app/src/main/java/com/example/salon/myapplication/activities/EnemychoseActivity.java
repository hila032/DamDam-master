package com.example.salon.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.salon.myapplication.EIntant;
import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.ERoom;
import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.AvailableUsersModel;
import com.example.salon.myapplication.models.Dialogs;
import com.example.salon.myapplication.models.IDataSnapshotOnChange;
import com.example.salon.myapplication.models.InvitesModel;
import com.example.salon.myapplication.models.RoomsModel;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EnemychoseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enamychose);
        SharedPreferencesModel.setIsInGame(false, this);
        // TODO: big refactor.
        // make the two loops - one line.
        RoomsModel.getRoom(UsersModel.getId()).child(EPlayer.PLAYER1.name()).child(ERoom.id.name()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isInGame = SharedPreferencesModel.getIsInGame(EnemychoseActivity.this);

                if (dataSnapshot.getValue() != null && !isInGame) {
                    Intent intent = new Intent(EnemychoseActivity.this, GameActivity.class);
                    intent.putExtra(EIntant.id.name(), UsersModel.getId());
                    intent.putExtra(EIntant.whoAmI.name(), EPlayer.PLAYER1);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        InvitesModel.listenToInvitation(UsersModel.getId(), new IDataSnapshotOnChange() {
            @Override
            public void doAction(DataSnapshot otherIdSnapshot) {
                Dialogs.sendPlayerGameMassag(EnemychoseActivity.this, otherIdSnapshot);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        AvailableUsersModel.removeUser(UsersModel.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        AvailableUsersModel.addUserToAvailableUsers(UsersModel.getId(), UsersModel.getEmail());

    }
}