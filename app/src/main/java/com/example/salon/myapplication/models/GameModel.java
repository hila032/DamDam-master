package com.example.salon.myapplication.models;

import android.os.Message;

import com.example.salon.myapplication.EDumGame;
import com.example.salon.myapplication.Emassege;


public class GameModel {
    private static int counterReloods = 0;


    public static String getWinner(String myCard, String myName, String otherPlayerCard, String otherPlayerName){
        if(myCard==null || otherPlayerCard==null) {
            return null;
        }
            if (myCard.equals(EDumGame.shoot.name()) && otherPlayerCard.equals(EDumGame.relood.name())) { // other player defend or shoot

                return myName;
            }
            if (otherPlayerCard.equals(EDumGame.shoot.name()) && myCard.equals(EDumGame.relood.name())) { // this player defend or shoot
                return otherPlayerName;
            }
            if (myCard.equals(EDumGame.relood.name())) {


            }
            return Emassege.tie.name();
    }
}
