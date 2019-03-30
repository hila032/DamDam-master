package com.example.salon.myapplication.models;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.salon.myapplication.EDumGame;
import com.example.salon.myapplication.EIntant;
import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.R;
import com.example.salon.myapplication.activities.DunDumGameActivity;
import com.example.salon.myapplication.activities.TicTacGameActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class Dialogs {
    private static ImageView mycardDilog;
    private static ImageView othercardDilog;
    private static Button okDilog;
    public static void sendPlayerGameMassag(final Activity correntActivity, final DataSnapshot otherIdSnapshot){
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
                                Intent intent = new Intent (correntActivity, DunDumGameActivity.class);
                                intent.putExtra(EIntant.id.name(), otherIdSnapshot.getValue().toString());
                                intent.putExtra(EIntant.whoAmI.name(), EPlayer.PLAYER2);
                                correntActivity.startActivity(intent);
                            }
                        };
                        DumDumRoomsModel.addRoom((String) otherIdSnapshot.getValue(), UsersModel.getId(), onCompleteGoToGameActivity);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public static void endGame(final Activity correntActivity, String player){
        AlertDialog.Builder builder = new AlertDialog.Builder(correntActivity);
        builder.setMessage("Game over, the getWinner is: " + player);
        builder.setTitle("Game Over");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            correntActivity.finish();

            }
        });
        if (correntActivity.hasWindowFocus()) {
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    public static void tie(final Activity correntActivity, String myCard, String otherCard){
        final Dialog dialog = new Dialog(correntActivity);
        dialog.setContentView(R.layout.tie_dialog);
        mycardDilog = (ImageView) dialog.findViewById(R.id.myCardIV);
        othercardDilog = (ImageView)dialog.findViewById(R.id.otherCardIV);
        showImage(myCard,otherCard);
        okDilog = (Button) dialog.findViewById(R.id.ok);
        okDilog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (correntActivity.hasWindowFocus()) {
            dialog.show();
        }
    }
    private static void showImage(String myCard, String otherCard){
        if (myCard.equals(EDumGame.shoot.name())){
            mycardDilog.setImageResource(R.drawable.shoot);
        }
        if (myCard.equals(EDumGame.defance.name())){
            mycardDilog.setImageResource(R.drawable.defance);
        }
        if (myCard.equals(EDumGame.relood.name())){
            mycardDilog.setImageResource(R.drawable.relood);
        }
        if (otherCard.equals(EDumGame.shoot.name())){
            othercardDilog.setImageResource(R.drawable.shoot);
        }
        if (otherCard.equals(EDumGame.relood.name())){
            othercardDilog.setImageResource(R.drawable.relood);
        }
        if (otherCard.equals(EDumGame.defance.name())){
            othercardDilog.setImageResource(R.drawable.defance);
        }

    }

    // tic tac
    public static void sendPlayerTicTacGameMassag(final Activity correntActivity, final DataSnapshot otherIdSnapshot){
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

                        OnCompleteListener<Void> onCompleteGoToTicTacGameActivity = new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent (correntActivity, TicTacGameActivity.class);
                                intent.putExtra(EIntant.id.name(), otherIdSnapshot.getValue().toString());
                                intent.putExtra(EIntant.whoAmI.name(), EPlayer.PLAYER2);
                                correntActivity.startActivity(intent);
                            }
                        };
                        TicTacModle.addTicTacRoom(UsersModel.getId(),(String) otherIdSnapshot.getValue(),(String) otherIdSnapshot.getValue(), onCompleteGoToTicTacGameActivity);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

