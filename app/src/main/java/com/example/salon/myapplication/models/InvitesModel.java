package com.example.salon.myapplication.models;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.salon.myapplication.EInvites;
import com.example.salon.myapplication.activities.TicTacEnemyChoseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InvitesModel {
    private static Activity activity;

    public static void addInvite(String id, String otherId) {
        FirebaseDatabase.getInstance().getReference(EInvites.INVITES.name()).child(otherId).setValue(id);
    }
    public static void removeInvitation(String id) {
        FirebaseDatabase.getInstance().getReference(EInvites.INVITES.name()).child(id).removeValue();
    }
    public static void listenToInvitation(String id, Activity currentActivity) {
        activity = currentActivity;
        FirebaseDatabase.getInstance().getReference(EInvites.INVITES.name()).child(id).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot otherIdSnapshot) {
                if (otherIdSnapshot.getValue() != null ) {
                    if (activity instanceof TicTacEnemyChoseActivity){
                        Dialogs.sendPlayerTicTacGameMassag(activity, otherIdSnapshot);
                    }
                    else {
                        Dialogs.sendDumDumPlayerGameMassag(activity, otherIdSnapshot);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
