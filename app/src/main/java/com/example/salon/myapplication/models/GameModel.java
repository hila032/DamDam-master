package com.example.salon.myapplication.models;

import android.view.View;

public class GameModel {
    private final String shoot = "shoot";
    private final String defance = "defance";
    private final String relood = "relood";
    private int counterReloods = 0;

    public int getAmountOfReloods(){
        return counterReloods;
    }
    public String winner (String player1, String player2){
        if (player1.equals(shoot) && !player2.equals(shoot)){
            return "player1";
        }
        if (player2.equals(shoot) && !player1.equals(shoot)){
            return "player2";
        }
        return null;
    }


    //offLine actions



}
