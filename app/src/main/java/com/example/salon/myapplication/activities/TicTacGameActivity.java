package com.example.salon.myapplication.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.myapplication.EIntant;
import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.ETicTacGame;
import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.DBRecords;
import com.example.salon.myapplication.models.Dialogs;
import com.example.salon.myapplication.models.InvitesModel;
import com.example.salon.myapplication.models.RecordPlayer;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.TicTacBoard;
import com.example.salon.myapplication.models.TicTacModle;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.time.Instant;

public class TicTacGameActivity extends AppCompatActivity {
    private TextView whosTurnIsTV;
    private String playerValue;
    private EPlayer player;
    private String roomId;
    private TicTacBoard board;
    private ChildEventListener changeTextListener;
    private boolean isMyTurn;
    private ETicTacGame result;
    private DBRecords playerDataBase;
    private final String OTHER_PLAYER_TURN = "other player turn";
    private final String YOUR_TURN = "your turn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_game);
        whosTurnIsTV = (TextView)findViewById(R.id.whosTurnIsTV);
        SharedPreferencesModel.setIsInGame(true, this);
        result = null;
        board = new TicTacBoard(this);
        roomId = (String) this.getIntent().getExtras().get(EIntant.ID.name());
        playerDataBase = new DBRecords(this);
        player = (EPlayer) this.getIntent().getExtras().get(EIntant.WHO_AM_I.name());
        if (player == EPlayer.PLAYER1){
            playerValue=ETicTacGame.x.name();
            Toast.makeText(this, "you are x, you start", Toast.LENGTH_SHORT).show();
            isMyTurn = true;
            board.setAllButtonsInGame(isMyTurn);
        }else {
            playerValue = ETicTacGame.o.name();
            Toast.makeText(this, "you are o, other player start", Toast.LENGTH_SHORT).show();
            isMyTurn = false;
            board.setAllButtonsInGame(isMyTurn);
        }
        board.setAlfaInGame(isMyTurn);

        changeTurnTextView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        InvitesModel.removeInvitation(UsersModel.getId());
        changeTextListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    for (int i =0; i<3; i++) {
                        String value = (String) dataSnapshot.child(i + "").getValue();
                        if (value != null) {
                            board.setValueInGame(Integer.parseInt(dataSnapshot.getKey()), i, value);

                        }
                    }
                    isMyTurn =!isMyTurn;
                    board.setAlfaInGame(isMyTurn);
                    board.setAllButtonsInGame(isMyTurn);
                    changeTurnTextView();
                     result = board.checkGameOver();
                    handleGameFinish(result);
                }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                TicTacModle.getTicTactRoom(roomId).removeEventListener(changeTextListener);
                finish();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        TicTacModle.getTicTactRoom(roomId).child(ETicTacGame.BOARD.name()).addChildEventListener(changeTextListener);
    }

    private void changeTurnTextView() {
        if (isMyTurn) {
            whosTurnIsTV.setText(YOUR_TURN);
        }else {
            whosTurnIsTV.setText(OTHER_PLAYER_TURN);
        }
    }

    public void setCellValueOnClick(View view) {
        updateDBOnClick(view);
        Button btn = (Button) view;
        btn.setText(playerValue);
        btn.setEnabled(false);
    }

    private void updateDBOnClick(View view) {
        String rowTag = (String) ((LinearLayout) view.getParent()).getTag();
        String colTag = (String) view.getTag();
        TicTacModle.setPlayerValueInTicTacGame(playerValue, rowTag,colTag, roomId);

    }

    private void handleGameFinish(ETicTacGame result) {
        switch (result) {
            case x:
                Dialogs.endTicTacGame(TicTacGameActivity.this, ETicTacGame.x.name());
                if (player == EPlayer.PLAYER1) {
                    playerDataBase.createTicTacPlayer(new RecordPlayer(UsersModel.getNickname(TicTacGameActivity.this), 1, 0, 0));
                } else {
                    playerDataBase.createTicTacPlayer(new RecordPlayer(UsersModel.getNickname(TicTacGameActivity.this), 0, 1, 0));
                }
                break;
            case o:
                if (player.equals(EPlayer.PLAYER2.name())) {
                    playerDataBase.createTicTacPlayer(new RecordPlayer(UsersModel.getNickname(TicTacGameActivity.this), 1, 0, 0));
                } else {
                    playerDataBase.createTicTacPlayer(new RecordPlayer(UsersModel.getNickname(TicTacGameActivity.this), 0, 1, 0));
                }
                Dialogs.endTicTacGame(TicTacGameActivity.this, ETicTacGame.o.name());
                break;
            case TIE:
                Dialogs.endTicTacGame(TicTacGameActivity.this, ETicTacGame.TIE.name());
                playerDataBase.createTicTacPlayer(new RecordPlayer(UsersModel.getNickname(TicTacGameActivity.this), 0, 0, 1));
                break;
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferencesModel.setIsInGame(false, this);
        TicTacModle.removeTicTacRoom(roomId);
        TicTacModle.getTicTactRoom(roomId).removeEventListener(changeTextListener);
    }

    public void homePage(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}
