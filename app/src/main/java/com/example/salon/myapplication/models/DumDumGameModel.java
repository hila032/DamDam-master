package com.example.salon.myapplication.models;

import com.example.salon.myapplication.EDumGame;
import com.example.salon.myapplication.EPlayer;


public class DumDumGameModel {



    public static void shoot(String roomId, EPlayer player) {
        DumDumRoomsModel.setPlayerValueInGame(EDumGame.shoot.name(), roomId, player);
    }
    public static void defance(String roomId, EPlayer player) {
        DumDumRoomsModel.setPlayerValueInGame(EDumGame.defance.name(), roomId, player);

    }
    public static void relood(String roomId, EPlayer player) {
        DumDumRoomsModel.setPlayerValueInGame(EDumGame.relood.name(), roomId, player);
    }
}
