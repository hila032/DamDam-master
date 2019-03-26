package com.example.salon.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.myapplication.EIntant;
import com.example.salon.myapplication.EPlayer;
import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.GameController;

public class TicTacGameActivity extends AppCompatActivity {
    private GameController gameController;
    private LinearLayout l;
    private TextView textView;
    private TextView ViewScore;

    private EPlayer player;
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_game);

        roomId = (String) this.getIntent().getExtras().get(EIntant.id.name());
        player = (EPlayer) this.getIntent().getExtras().get(EIntant.whoAmI.name());


        gameController = new GameController();

        l = (LinearLayout)findViewById(R.id.Llmain);
        textView = (TextView)findViewById(R.id.tV0);
        textView.setText("x");
        textView = (TextView)findViewById(R.id.tV0);
        textView.setText("0");

        ViewScore = (TextView)findViewById(R.id.winX);
    }
    public void changeText (View v) {
        Button btn = (Button) v;
        int loc = Integer.parseInt(btn.getTag().toString());
        String player = gameController.playerStep(loc);
        btn.setText(player);
        btn.setEnabled(false);
        String p = gameController.getCurrentPlayer();
        textView.setText(p);
        if (gameController.isGameOver()){
            String name = gameController.winner();
            Toast.makeText(this, "winner is: " + name, Toast.LENGTH_LONG).show();
            Button btn1;
            for (int i= 0; i < 9; i++){
                btn1 = (Button)l.findViewWithTag(i+"");
                btn1.setEnabled(false);
            }
        }
    }

    public void reset (View v) {
        String scoor = "x- " + gameController.getX() + " 0-" + gameController.get0() + " Tie-" + gameController.getTie();
        ViewScore.setText(scoor);
        newGame();


    }

    private void newGame(){

        gameController.newGame();
        String p = gameController.getCurrentPlayer();
        textView.setText(p);
        Button btn;
        for (int i= 0; i < 9; i++){
            btn = (Button)l.findViewWithTag(i+"");
            btn.setText("");
            btn.setEnabled(true);
        }
    }
}
