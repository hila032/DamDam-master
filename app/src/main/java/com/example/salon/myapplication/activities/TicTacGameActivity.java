package com.example.salon.myapplication.activities;

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
import com.example.salon.myapplication.models.RecordPlayer;
import com.example.salon.myapplication.models.SharedPreferencesModel;
import com.example.salon.myapplication.models.TicTacBoard;
import com.example.salon.myapplication.models.TicTacModle;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class TicTacGameActivity extends AppCompatActivity {
    private TextView textView;
    private TextView ViewScore;
    private String playerValue;
    private EPlayer player;
    private String roomId;
    private TicTacBoard board;
    private ChildEventListener changeTextListener;
    private boolean isMyTurn;
    private DBRecords playerDataBase;
    private final String x = "x";
    private final String o = "o";
    private final String tie = "Tie";
    private final String OTHER_PLAYER_TURN = "other player turn";
    private final String YOUR_TURN = "your turn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_game);
        textView = (TextView)findViewById(R.id.whosTurnIsTV);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferencesModel.setIsInGame(true, this);
        board = new TicTacBoard(this);
        roomId = (String) this.getIntent().getExtras().get(EIntant.id.name());
        playerDataBase = new DBRecords(this);
        player = (EPlayer) this.getIntent().getExtras().get(EIntant.whoAmI.name());
        if (player == EPlayer.PLAYER1){
            playerValue=x;
            Toast.makeText(this, "you are x, you start", Toast.LENGTH_SHORT).show();
            isMyTurn = true;
            board.setAllButtonsInGame(isMyTurn);
        }else {
            playerValue = o;
            Toast.makeText(this, "you are o, other player start", Toast.LENGTH_SHORT).show();
            isMyTurn = false;
            board.setAllButtonsInGame(isMyTurn);
        }
        board.setAlfaInGame(isMyTurn);

        changeTurnTextView();


        ViewScore = (TextView)findViewById(R.id.winX);
        changeTextListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (TicTacModle.isTicTacRoomExist(roomId)) {
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
                    String result = board.checkGameOver();
                    handleGameFinish(result);
                }
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
        TicTacModle.getTicTactRoom(roomId).child(ETicTacGame.board.name()).addChildEventListener(changeTextListener);
    }

    private void changeTurnTextView() {
        if (isMyTurn) {
            textView.setText(YOUR_TURN);
        }else {
            textView.setText(OTHER_PLAYER_TURN);
        }
    }

    public void changeText (View view) {
        Button btn = (Button) view;
        String rowTag = (String) ((LinearLayout) view.getParent()).getTag();
        String colTag = (String) btn.getTag();
        board.setValueInGame(Integer.valueOf(rowTag),Integer.valueOf(colTag),playerValue);
        TicTacModle.setPlayerValueInTicTacGame(playerValue, rowTag,colTag, roomId);
        btn.setText(playerValue);
        btn.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferencesModel.setIsInGame(true,this);
    }

    private void handleGameFinish(String result) {
        switch (result) {
            case x:
                Dialogs.endGame(TicTacGameActivity.this, x);
                if (player.equals(EPlayer.PLAYER1.name())) {
                    playerDataBase.createDumPlayer(new RecordPlayer(UsersModel.getNickname(TicTacGameActivity.this), 1, 0, 0));
                } else {
                    playerDataBase.createDumPlayer(new RecordPlayer(UsersModel.getNickname(TicTacGameActivity.this), 0, 1, 0));
                }
                break;
            case o:
                if (player.equals(EPlayer.PLAYER2.name())) {
                    playerDataBase.createDumPlayer(new RecordPlayer(UsersModel.getNickname(TicTacGameActivity.this), 1, 0, 0));
                } else {
                    playerDataBase.createDumPlayer(new RecordPlayer(UsersModel.getNickname(TicTacGameActivity.this), 0, 1, 0));
                }
                Dialogs.endGame(TicTacGameActivity.this, o);
                break;
            case tie:
                Dialogs.endGame(TicTacGameActivity.this, tie);
                playerDataBase.createDumPlayer(new RecordPlayer(UsersModel.getNickname(TicTacGameActivity.this), 0, 0, 1));
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
}
