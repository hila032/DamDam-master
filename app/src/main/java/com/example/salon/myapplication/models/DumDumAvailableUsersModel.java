package com.example.salon.myapplication.models;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * db structure:
 * id : email
 */
public class DumDumAvailableUsersModel {

    private static final DatabaseReference dumDumavailableUsers = FirebaseDatabase.getInstance().getReference("dum dum availableUsers");

    public static void DumDumaddUserToAvailableUsers(String id, String email) {
        dumDumavailableUsers.child(id).setValue(email);
    }
    public static void removeDumDumUser(String id){
        dumDumavailableUsers.child(id).removeValue();
    }


    public static DatabaseReference getDumDumAvailableUsers() {
        return dumDumavailableUsers;
    }
}
