package com.example.salon.myapplication.models;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoomsModel {
    public static void addRoom(String id, String otherPlayerId, OnCompleteListener<Void> onCompleteGoToGameActivity){
        DatabaseReference roomsReference = FirebaseDatabase.getInstance().getReference("Rooms");
        roomsReference.child(id).child(id).setValue("");
        roomsReference.child(id).child(otherPlayerId).setValue("").addOnCompleteListener(onCompleteGoToGameActivity);
    }




}
