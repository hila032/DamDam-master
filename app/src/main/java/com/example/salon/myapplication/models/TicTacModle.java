package com.example.salon.myapplication.models;

import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.ERoom;
import com.example.salon.myapplication.ETicTacGame;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TicTacModle {


    public static void addTicTacRoom (String myId, String otherId, String roomId, OnCompleteListener<Void> onCompleteGoToTicTacGameActivity){
        DatabaseReference roomsReference = FirebaseDatabase.getInstance().getReference(ERoom.TICTACROOM.name()).child(roomId).child(ETicTacGame.BOARD.name());
        FirebaseDatabase.getInstance().getReference(ERoom.TICTACROOM.name()).child(roomId).child(EPlayer.PLAYER1.name()).setValue(myId);
         FirebaseDatabase.getInstance().getReference(ERoom.TICTACROOM.name()).child(roomId).child(EPlayer.PLAYER2.name()).setValue(otherId);
        for (int i = 0; i<3; i++){
            roomsReference.child(i + ETicTacGame.E.name());
            for (int k = 0; k <3; k++){
                roomsReference.child(i + "").child(k + "").setValue(ETicTacGame.E.name());
            }
        }
        roomsReference.child("2").child("2").setValue(ETicTacGame.E.name())
                .addOnCompleteListener(onCompleteGoToTicTacGameActivity);
    }

    public static DatabaseReference getTicTactRoom(String roomId){
        return FirebaseDatabase.getInstance().getReference(ERoom.TICTACROOM.name()).child(roomId);
    }
    public static DatabaseReference getTicTactBoard(String roomId){
        return FirebaseDatabase.getInstance().getReference(ERoom.TICTACROOM.name()).child(roomId).child(ETicTacGame.BOARD.name());
    }

    public static void setPlayerValueInTicTacGame(String playerNewValueInGame, String playerRowLocation, String playerColLocation, String roomId){
        getTicTactBoard(roomId).child(playerRowLocation).child(playerColLocation).setValue(playerNewValueInGame);
    }

    public static void removeTicTacRoom(String roomId) {
        FirebaseDatabase.getInstance().getReference(ERoom.TICTACROOM.name()).child(roomId).removeValue();
    }
}
