package com.example.salon.myapplication.models;

import com.example.salon.myapplication.EDumGame;
import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.ERoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.salon.myapplication.EPlayer.PLAYER1;
import static com.example.salon.myapplication.EPlayer.PLAYER2;

public class DumDumRoomsModel {

    public static void addRoom(String id, String otherPlayerId, OnCompleteListener<Void> onCompleteGoToGameActivity){
        DatabaseReference roomsReference = FirebaseDatabase.getInstance().getReference(ERoom.ROOMS.name());
        roomsReference.child(id).child(PLAYER1.name()).child(ERoom.ID.name()).setValue(id);
        roomsReference.child(id).child(PLAYER2.name()).child(ERoom.ID.name()).setValue(otherPlayerId)
                .addOnCompleteListener(onCompleteGoToGameActivity);


    }
    public static DatabaseReference getRoom(String roomId){
        return FirebaseDatabase.getInstance().getReference(ERoom.ROOMS.name()).child(roomId);
    }

    public static void removeCard(String roomId, EPlayer player){
        FirebaseDatabase.getInstance().getReference(ERoom.ROOMS.name()).child(roomId).child(player.name()).child(ERoom.CARD.name()).removeValue();
    }
    public static void removeRoom(String roomId) {
        getRoom(roomId).removeValue();
    }

}
