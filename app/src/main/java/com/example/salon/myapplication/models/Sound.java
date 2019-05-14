package com.example.salon.myapplication.models;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.salon.myapplication.R;

public class Sound {
    private static int shootId;
    private static int reloadId;
    private static int shieldId;
    private static final float VALUME = 1;
    private static SoundPool soundPool;


    public static void createSoundPool(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
        loadSound(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    private static void createOldSoundPool() {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }
    private static void loadSound(Context context) {
        shootId = soundPool.load(context, R.raw.gunshoot,1);
        reloadId = soundPool.load(context, R.raw.reload,1);
        shieldId = soundPool.load(context,R.raw.shield,1);
    }

    public static void playGunShoot(){
        soundPool.play(shootId, VALUME, VALUME, 0,0,1);
    }
    public static void playReload(){
        soundPool.play(reloadId, VALUME, VALUME, 0,0,1);
    }
    public static void playShield(){
        soundPool.play(shieldId, VALUME, VALUME, 0,0,1);
    }
    public static void release(){
        soundPool.release();
    }
}
