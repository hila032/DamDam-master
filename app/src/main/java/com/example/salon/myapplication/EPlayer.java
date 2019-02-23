package com.example.salon.myapplication;

public enum EPlayer {
    PLAYER1, PLAYER2;
    public static EPlayer getOtherPlayer(EPlayer player){
        if (player == PLAYER1){
            return PLAYER2;
        }
        return PLAYER1;
    }
}
