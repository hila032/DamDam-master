package com.example.salon.myapplication.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TicTacAvailableUsersModle {
    private static final DatabaseReference ticTacAvailableUsers = FirebaseDatabase.getInstance().getReference("tic tac availableUsers");

    public static void addTicTacUserToAvailableUsers(String id, String email) {
        ticTacAvailableUsers.child(id).setValue(email);
    }

    public static void removeTicTacUser(String id){
        ticTacAvailableUsers.child(id).removeValue();
    }


    public static DatabaseReference getTicTacAvailableUsers() {
        return ticTacAvailableUsers;
    }
}
