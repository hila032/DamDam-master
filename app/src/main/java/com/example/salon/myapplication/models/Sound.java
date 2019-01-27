package com.example.salon.myapplication.models;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.salon.myapplication.R;

public class Sound {
    private static Context context;
    private static int shootId;
    private static int reloodId;
    private static float valume;
    private static SoundPool soundPool;

    public static void setSound (Context contextRes){
        context = contextRes;
        valume = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool  = new SoundPool.Builder().setMaxStreams(3).build();
        }
        else {
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 1);
        }
        shootId = soundPool.load(context, R.raw.gunshoot,1);
        reloodId = soundPool.load(context, R.raw.reloode,1);
    }
    public static void playGunhoot(){
        soundPool.play(shootId, valume, valume, 1,0,1);
    }
    public static void playRelood(){
        soundPool.play(reloodId, valume, valume, 1,0,1);
    }
}
