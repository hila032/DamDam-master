package com.example.salon.myapplication.models;

import com.example.salon.myapplication.EPlayer;

public class TicTacGameController {
    private static String winName = "tie";
    private static int turnCounter = 0;
    private static int addX = 0;
    private static int add0 = 0;
    private static int addTie = 0;
    

    public static void newGame(String roomId){

        TicTacModle.resetTicTacRoom(roomId);
    }


    public static int getX() {
        return addX;
    }
    public static int get0() {
        return add0;
    }
    public static int getTie() {
        return addTie;
    }
}
