package com.example.salon.myapplication.models;

import android.view.View;

import com.example.salon.myapplication.EDumGame;
import com.example.salon.myapplication.EPlayer;


public class GameModel {
    private static int counterReloods = 0;



    public static void shoot(String roomId, EPlayer player) {
        RoomsModel.setPlayerValueInGame(EDumGame.shoot.name(), roomId, player);
    }
    public static void defance(String roomId, EPlayer player) {
        RoomsModel.setPlayerValueInGame(EDumGame.defance.name(), roomId, player);

    }
    public static void relood(String roomId, EPlayer player) {
        RoomsModel.setPlayerValueInGame(EDumGame.relood.name(), roomId, player);
    }
}
