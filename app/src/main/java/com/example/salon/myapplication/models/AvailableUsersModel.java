package com.example.salon.myapplication.models;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * db structure:
 * id : email
 */
public class AvailableUsersModel {

    private static final DatabaseReference availableUsers = FirebaseDatabase.getInstance().getReference("availableUsers");

    public static void addUserToAvailableUsers(String id, String email) {
        availableUsers.child(id).setValue(email);
    }
    public static void removeUser(String id){
        availableUsers.child(id).removeValue();
    }

}
