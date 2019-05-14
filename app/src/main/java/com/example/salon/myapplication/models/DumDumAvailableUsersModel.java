package com.example.salon.myapplication.models;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * db structure:
 * id : email
 */
public class DumDumAvailableUsersModel {

    private static final DatabaseReference dumDumAvailableUsers = FirebaseDatabase.getInstance().getReference("dum dum availableUsers");

    public static void addDumDumUserToAvailableUsers(String id, String email) {
        dumDumAvailableUsers.child(id).setValue(email);
    }
    public static void removeDumDumUser(String id){
        dumDumAvailableUsers.child(id).removeValue();
    }


    public static DatabaseReference getDumDumAvailableUsers() {
        return dumDumAvailableUsers;
    }
}
