package com.example.salon.myapplication.models;

import com.example.salon.myapplication.EDumGame;
import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.ERoom;
import com.google.firebase.database.FirebaseDatabase;


public class DumDumGameModel {

    public static void setPlayerValueInGame(String playerNewValueInGame , String roomId, EPlayer player){
        FirebaseDatabase.getInstance().getReference(ERoom.ROOMS.name()).child(roomId).child(player.name()).child(ERoom.CARD.name()).setValue(playerNewValueInGame);
    }
}
