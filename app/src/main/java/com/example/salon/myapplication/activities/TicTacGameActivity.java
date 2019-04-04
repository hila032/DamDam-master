package com.example.salon.myapplication.activities;

import android.app.Dialog;
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
import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.Dialogs;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.TicTacBoard;
import com.example.salon.myapplication.models.TicTacModle;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class TicTacGameActivity extends AppCompatActivity {
    private LinearLayout l;
    private TextView textView;
    private TextView ViewScore;
    private String playerValue;
    private EPlayer player;
    private String roomId;
    private TicTacBoard board;
    private ChildEventListener changeTextListener;
    private boolean isMyTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_game);
        SharedPreferencesModel.setIsInGame(true, this);
        board = new TicTacBoard(this);
        roomId = (String) this.getIntent().getExtras().get(EIntant.id.name());
        player = (EPlayer) this.getIntent().getExtras().get(EIntant.whoAmI.name());
        if (player == EPlayer.PLAYER1){
            playerValue= "x";
            isMyTurn = true;
            board.setAllButtonsInGame(isMyTurn);
        }else {
            playerValue = "O";
            isMyTurn = false;
            board.setAllButtonsInGame(isMyTurn);
        }
        board.setAlfaInGame(isMyTurn);
        //l = (LinearLayout)findViewById(R.id.Llmain);
        textView = (TextView)findViewById(R.id.tV0);
        textView.setText("x");
        textView = (TextView)findViewById(R.id.tV0);
        textView.setText("0");

        ViewScore = (TextView)findViewById(R.id.winX);
        changeTextListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (TicTacModle.isTicTacRoomExist(roomId)) {
                    for (int i =0; i<3; i++) {
                        board.setValueInGame(Integer.parseInt(dataSnapshot.getKey()), i, (String) dataSnapshot.child(i + "").getValue());
                    }
                    isMyTurn =!isMyTurn;
                    board.setAlfaInGame(isMyTurn);
                    board.setAllButtonsInGame(isMyTurn);


                    String result = board.checkGameOver();
                    if (result.equals("x")) {
                        Dialogs.endGame(TicTacGameActivity.this, "x");
                        Toast.makeText(TicTacGameActivity.this, "x wins", Toast.LENGTH_SHORT).show();
                    } else if (result.equals("O")) {
                        Dialogs.endGame(TicTacGameActivity.this, "O");
                        Toast.makeText(TicTacGameActivity.this, "O wins", Toast.LENGTH_SHORT).show();
                    } else if (result.equals("Tie")) {
                        Dialogs.endGame(TicTacGameActivity.this, "Tie");
                        Toast.makeText(TicTacGameActivity.this, "no winners", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        TicTacModle.getTicTactRoom(roomId).child("board").addChildEventListener(changeTextListener);


    }
    public void changeText (View view) {
        Button btn = (Button) view;
        String rowTag = (String) ((LinearLayout) view.getParent()).getTag();
        String colTag = (String) btn.getTag();
        board.setValueInGame(Integer.valueOf(rowTag),Integer.valueOf(colTag),playerValue);
        TicTacModle.setPlayerValueInTicTacGame(playerValue, rowTag,colTag, roomId);
        btn.setText(playerValue);
        btn.setEnabled(false);

        textView.setText(playerValue);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferencesModel.setIsInGame(true,this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferencesModel.setIsInGame(false, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        TicTacModle.getTicTactRoom(roomId).removeEventListener(changeTextListener);
        TicTacModle.removeTicTacRoom(roomId);
    }
}
