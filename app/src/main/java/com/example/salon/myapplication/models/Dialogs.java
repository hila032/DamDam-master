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
import android.widget.TextView;

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
    private static ImageView myCardDilog;
    private static ImageView otherCardDilog;
    private static Button okDilog;
    public static void sendDumDumPlayerGameMassag(final Activity correntActivity, final DataSnapshot otherIdSnapshot){
        AlertDialog.Builder builder = new AlertDialog.Builder(correntActivity);
        builder.setMessage("you receive an invitation to a dum dum chicken duel")
                .setTitle("invitation")
                .setNegativeButton("NO, thank you", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InvitesModel.removeInvitation(UsersModel.getId());
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK, Challenge accepted", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        InvitesModel.removeInvitation(UsersModel.getId());

                        OnCompleteListener<Void> onCompleteGoToGameActivity = new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                                Intent intent = new Intent (correntActivity, DunDumGameActivity.class);
                                intent.putExtra(EIntant.ID.name(), otherIdSnapshot.getValue().toString());
                                intent.putExtra(EIntant.WHO_AM_I.name(), EPlayer.PLAYER2);
                                correntActivity.startActivity(intent);
                            }
                        };
                        DumDumRoomsModel.addRoom((String) otherIdSnapshot.getValue(), UsersModel.getId(), onCompleteGoToGameActivity);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public static void endTicTacGame(final Activity correntActivity, String playerValue){
        AlertDialog.Builder builder = new AlertDialog.Builder(correntActivity);
        builder.setMessage("Game over, the Winner is: " + playerValue);
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

    public static void DumDumEndGame(final Activity correntActivity, String player, String myCard, String otherCard){
        final Dialog dialog = new Dialog(correntActivity);
        dialog.setContentView(R.layout.end_game_dialog);
        myCardDilog = (ImageView) dialog.findViewById(R.id.myCardIV);
        otherCardDilog = (ImageView)dialog.findViewById(R.id.otherCardIV);
        TextView winner = (TextView) dialog.findViewById(R.id.winner);
        winner.setText("the winner is " + player);
        showImage(myCard,otherCard);
        okDilog = (Button) dialog.findViewById(R.id.ok);
        okDilog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                correntActivity.finish();
            }
        });
        if (correntActivity.hasWindowFocus()) {
            dialog.show();
        }
    }

    public static void tie(final Activity correntActivity, String myCard, String otherCard){
        final Dialog dialog = new Dialog(correntActivity);
        dialog.setContentView(R.layout.tie_dialog);
        myCardDilog = (ImageView) dialog.findViewById(R.id.myCardIV);
        otherCardDilog = (ImageView)dialog.findViewById(R.id.otherCardIV);
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
        if (myCard.equals(EDumGame.SHOOT.name())){
            myCardDilog.setImageResource(R.drawable.shoot);
        }
        if (myCard.equals(EDumGame.DEFENCE.name())){
            myCardDilog.setImageResource(R.drawable.defance);
        }
        if (myCard.equals(EDumGame.RELOAD.name())){
            myCardDilog.setImageResource(R.drawable.relood);
        }
        if (otherCard.equals(EDumGame.SHOOT.name())){
            otherCardDilog.setImageResource(R.drawable.shoot);
        }
        if (otherCard.equals(EDumGame.RELOAD.name())){
            otherCardDilog.setImageResource(R.drawable.relood);
        }
        if (otherCard.equals(EDumGame.DEFENCE.name())){
            otherCardDilog.setImageResource(R.drawable.defance);
        }
    }

    // tic tac
    public static void sendPlayerTicTacGameMassag(final Activity correntActivity, final DataSnapshot otherIdSnapshot){
        AlertDialog.Builder builder = new AlertDialog.Builder(correntActivity);
        builder.setMessage("you receive an invitation to a tic tac toe duel")
                .setTitle("invitetion")
                .setNegativeButton("NO, thank you", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InvitesModel.removeInvitation(UsersModel.getId());
                        dialog.cancel();
                    }
                })
                .setPositiveButton("OK, Challenge accepted", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InvitesModel.removeInvitation(UsersModel.getId());
                        dialog.cancel();
                        OnCompleteListener<Void> onCompleteGoToTicTacGameActivity = new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent (correntActivity, TicTacGameActivity.class);
                                intent.putExtra(EIntant.ID.name(), otherIdSnapshot.getValue().toString());
                                intent.putExtra(EIntant.WHO_AM_I.name(), EPlayer.PLAYER2);
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

