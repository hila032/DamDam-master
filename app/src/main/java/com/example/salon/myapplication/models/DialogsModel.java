package com.example.salon.myapplication.models;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.example.salon.myapplication.activities.EnemychoseActivity;
import com.example.salon.myapplication.activities.GameActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class DialogsModel {
    public static void sendPlayerGameMassag(final Activity correntActivity, final DataSnapshot otherIdSnapshot, final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(correntActivity);
        builder.setMessage("im pop")
                .setTitle("invitetion")
                .setNegativeButton("nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InvitesModel.removeInvitation(UsersModel.getId());
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InvitesModel.removeInvitation(UsersModel.getId());

                        OnCompleteListener<Void> onCompleteGoToGameActivity = new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent (correntActivity, GameActivity.class);
                                intent.putExtra("id", otherIdSnapshot.getValue().toString());

                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                context.startActivity(intent);
                            }
                        };
                        RoomsModel.addRoom((String) otherIdSnapshot.getValue(), UsersModel.getId(), onCompleteGoToGameActivity);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

