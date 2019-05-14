package com.example.salon.myapplication.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.salon.myapplication.ESharedPreferences;

public class SharedPreferencesModel {
    private static SharedPreferences sharedPreferencesPic;
    private static SharedPreferences.Editor editor;
    
    public static void setIsInGame(boolean isInGame, Context context) {
        SharedPreferences preferencesIsInGame = context.getSharedPreferences(ESharedPreferences.STATE.name(), Context.MODE_PRIVATE);
        editor = preferencesIsInGame.edit();
        editor.putBoolean(ESharedPreferences.IS_IN_GAME.name(), isInGame);
        editor.commit();
    }

    public static boolean getIsInGame(Context context) {
        return context.getSharedPreferences(ESharedPreferences.STATE.name(), Context.MODE_PRIVATE).getBoolean(ESharedPreferences.IS_IN_GAME.name(), false);

    }
    public static void setDefault (Context context){
        sharedPreferencesPic = context.getSharedPreferences(ESharedPreferences.PROFILE.name(),Context.MODE_PRIVATE);
        editor = sharedPreferencesPic.edit();
    }
    public static String getPicName (){
        return sharedPreferencesPic.getString(ESharedPreferences.PIC_NAME.name(),"");
    }

    public static void setPicName(String picName){
        editor.putString(ESharedPreferences.PIC_NAME.name(),picName);
        editor.commit();
    }

}
