package com.example.salon.myapplication.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TicTacAvailableUsersModle {
    private static final DatabaseReference ticTacavailableUsers = FirebaseDatabase.getInstance().getReference("tic tac availableUsers");

    public static void addTicTacUserToAvailableUsers(String id, String email) {
        ticTacavailableUsers.child(id).setValue(email);
    }

    public static void removeTicTacUser(String id){
        ticTacavailableUsers.child(id).removeValue();
    }


    public static DatabaseReference getTicTacAvailableUsers() {
        return ticTacavailableUsers;
    }
}
