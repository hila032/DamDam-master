package com.example.salon.myapplication.models;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.salon.myapplication.activities.DumDumEnemyChoseActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InvitesModel {
    private static Activity activity;
    public static void removeInvitation(String id) {
        FirebaseDatabase.getInstance().getReference("invites").child(id).removeValue();
    }
    public static void listenToInvitation(String id, Activity currentActivity) {
        activity = currentActivity;
        FirebaseDatabase.getInstance().getReference("invites").child(id).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot otherIdSnapshot) {
                if (otherIdSnapshot.getValue() != null) {
                    if (activity.getClass().getSimpleName().equals("TicTacEnemyChoseActivity")){
                        Dialogs.sendPlayerTicTacGameMassag(activity, otherIdSnapshot);
                    }
                    else {
                        Dialogs.sendPlayerGameMassag(activity, otherIdSnapshot);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static void addInvits(String id, String otherId) {
        FirebaseDatabase.getInstance().getReference("invites").child(otherId).setValue(id);
    }



}
