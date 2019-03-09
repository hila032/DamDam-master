package com.example.salon.myapplication.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.salon.myapplication.R;

import java.util.Random;

public class OnePlayerActivity extends AppCompatActivity {

//    private String playerCard;
//    private int reloodCounter = 0;
//    AlertDialog.Builder ErorMasaage = new AlertDialog.Builder(this);
//    Random random = new Random();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_one_player);
//        ErorMasaage.setTitle("ERORE");
//        ErorMasaage.setMessage("you don't have infae bullets");
//        ErorMasaage.setPositiveButton("OK", null);
//
//    }
//    // computer
//    public String ComputerChoise(){
//        int choise = random.nextInt(3-1)+1;
//        if (choise == 1){
//            return "shoot";
//        }
//        if (choise == 2){
//            return "defence";
//        }
//        return "relood";
//    }
//
//    CountDownTimer countDownTimer = new CountDownTimer(5000,1000) {
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            timmer.setText(String.valueOf(timerSec));
//            timerSec--;
//        }
//
//        @Override
//        public void onFinish() {
//            timerSec = 3;
//            check();
//
//
//        }
//    };
//
//
//    public void reloodOffLine(View view) {
//        playerCard = "relood";
//        reloodCounter++;
//    }
//
//    public void defanceOffLine(View view) {
//        playerCard = "defence";
//    }
//
//    public void shootOffLine(View view) {
//        if (reloodCounter > 0) {
//            playerCard = "shoot";
//        }
//        AlertDialog dialog = ErorMasaage.create();
//        dialog.show();
//
//
//    }
//
}
