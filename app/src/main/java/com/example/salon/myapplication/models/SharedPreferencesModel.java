package com.example.salon.myapplication.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.salon.myapplication.ESharedPreferences;

public class SharedPreferencesModel {

    public static void setIsInGame(boolean isInGame, Context context) {
        SharedPreferences preferencesIsInGame = context.getSharedPreferences(ESharedPreferences.state.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesIsInGame.edit();
        editor.putBoolean(ESharedPreferences.isInGame.name(), isInGame);
        editor.commit();
    }

    public static boolean getIsInGame(Context context) {
        return context.getSharedPreferences(ESharedPreferences.state.name(), Context.MODE_PRIVATE).getBoolean(ESharedPreferences.isInGame.name(), false);

    }
}
