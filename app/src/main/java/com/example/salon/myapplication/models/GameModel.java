package com.example.salon.myapplication.models;

import android.os.Message;
import android.os.Handler;
import android.view.View;

import com.example.salon.myapplication.EDumGame;
import com.example.salon.myapplication.Emassege;


public class GameModel {
    private static int counterReloods = 0;


    public static void getWinner(String myCard, String myName, String otherPlayerCard, String otherPlayerName, Handler handler){
        if(myCard==null || otherPlayerCard==null)
            return ;
        if (myCard.equals(EDumGame.shoot.name()) && otherPlayerCard.equals(EDumGame.defance.name())) { // other player defend or shoot
                Message msg = handler.obtainMessage();
                msg.getData().putString(Emassege.name.name(), myName);
                handler.sendMessage(msg);
                return ;
        }
        if (otherPlayerCard.equals(EDumGame.shoot.name()) && myCard.equals(EDumGame.defance.name())) { // this player defend or shoot
            Message msg = handler.obtainMessage();
            msg.getData().putString(Emassege.name.name(), otherPlayerName);
            handler.sendMessage(msg);
            return ;
        }
//        if (myCard.equals(relood)) {
//            Message msg = handler.obtainMessage();
//            msg.getData().putInt(relood, counterReloods++);
//            handler.sendMessage(msg);
//
//        }

        if ((myCard.equals(EDumGame.relood.name()) && otherPlayerCard.equals(EDumGame.relood.name())) ||
                (myCard.equals(EDumGame.relood.name())&& otherPlayerCard.equals(EDumGame.defance.name())) ||
                (myCard.equals(EDumGame.defance.name())&& otherPlayerCard.equals(EDumGame.defance.name()) ||
                (myCard.equals(EDumGame.defance.name()) && myCard.equals(EDumGame.relood.name()))) ||
                (myCard.equals(EDumGame.relood.name()) && otherPlayerCard.equals(EDumGame.shoot.name())) ||
                (myCard.equals(EDumGame.shoot.name()) && otherPlayerCard.equals(EDumGame.relood.name())) ||
                (myCard.equals(EDumGame.shoot.name()) && otherPlayerCard.equals(EDumGame.shoot.name()))){

            Message msg = handler.obtainMessage();
            msg.getData().putString(Emassege.name.name(), Emassege.tie.name());
            handler.sendMessage(msg);
            return ;

        }

    }
}
