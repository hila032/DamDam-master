package com.example.salon.myapplication.models;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.activities.GameActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.salon.myapplication.EPlayer.PLAYER1;
import static com.example.salon.myapplication.EPlayer.PLAYER2;

public class RoomsModel {
    public static void addRoom(String id, String otherPlayerId, OnCompleteListener<Void> onCompleteGoToGameActivity){
        DatabaseReference roomsReference = FirebaseDatabase.getInstance().getReference("Rooms");
        roomsReference.child(id).child(PLAYER1.name()).child("card").setValue("");
        roomsReference.child(id).child(PLAYER2.name()).child("card").setValue("");
        roomsReference.child(id).child(PLAYER1.name()).child("id").setValue(id);
        roomsReference.child(id).child(PLAYER2.name()).child("id").setValue(otherPlayerId)
                .addOnCompleteListener(onCompleteGoToGameActivity);


    }
    public static DatabaseReference getRoom(String roomId){
        return FirebaseDatabase.getInstance().getReference("Rooms").child(roomId);
    }
    public static void setPlayerValueInGame(String playerNewValueInGame , String roomId, EPlayer player){
        FirebaseDatabase.getInstance().getReference("Rooms").child(roomId).child(player.name()).child("card").setValue(playerNewValueInGame);
    }


    public static void removeRoom(String roomId) {
        getRoom(roomId).removeValue();
    }
}
