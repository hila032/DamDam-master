package com.example.salon.myapplication.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.salon.myapplication.ESharedPreferences;

public class SharedPreferencesModel {
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private static String FILE_NAME = "profile";
    private static String PIC_NAME = "picName";
    public static void setIsInGame(boolean isInGame, Context context) {
        SharedPreferences preferencesIsInGame = context.getSharedPreferences(ESharedPreferences.state.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesIsInGame.edit();
        editor.putBoolean(ESharedPreferences.isInGame.name(), isInGame);
        editor.commit();
    }

    public static boolean getIsInGame(Context context) {
        return context.getSharedPreferences(ESharedPreferences.state.name(), Context.MODE_PRIVATE).getBoolean(ESharedPreferences.isInGame.name(), false);

    }
    public static void setDefault (Context context){
        sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    public static String getPicName (Context context){
        return sp.getString(PIC_NAME,"");
    }

    public static void seyPicName (String picName){
        editor.putString(PIC_NAME,picName);
        editor.commit();
    }

}
