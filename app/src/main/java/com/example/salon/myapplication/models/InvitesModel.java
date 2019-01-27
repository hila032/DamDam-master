package com.example.salon.myapplication.models;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InvitesModel {
    public static void removeInvitation(String id) {
        FirebaseDatabase.getInstance().getReference("invites").child(id).removeValue();
    }
    public static void listenToInvitation(String id, ValueEventListener listener) {
        FirebaseDatabase.getInstance().getReference("invites").child(id).addValueEventListener(listener);
    }
    public static void addInvits(String id, String otherId) {
        FirebaseDatabase.getInstance().getReference("invites").child(otherId).setValue(id);
    }

}
