package com.example.salon.myapplication.models;

import android.app.Activity;
import android.widget.Button;

import com.example.salon.myapplication.ETicTacGame;
import com.example.salon.myapplication.R;

public class TicTacBoard {
    private String[][] board = new String[][]{{ETicTacGame.E.name(),ETicTacGame.E.name(),ETicTacGame.E.name()},
            {ETicTacGame.E.name(),ETicTacGame.E.name(),ETicTacGame.E.name()},
            {ETicTacGame.E.name(),ETicTacGame.E.name(),ETicTacGame.E.name()}};
    private Button[][] buttonsArray= new Button[3][3];
    private Activity activity;
    private int turnCounter =0;

    public TicTacBoard(Activity activity) {
        this.activity = activity;
        this.buttonsArray[0][0] = activity.findViewById(R.id.b00);
        this.buttonsArray[0][1] = activity.findViewById(R.id.b01);
        this.buttonsArray[0][2] = activity.findViewById(R.id.b02);
        this.buttonsArray[1][0] = activity.findViewById(R.id.b10);
        this.buttonsArray[1][1] = activity.findViewById(R.id.b11);
        this.buttonsArray[1][2] = activity.findViewById(R.id.b12);
        this.buttonsArray[2][0] = activity.findViewById(R.id.b20);
        this.buttonsArray[2][1] = activity.findViewById(R.id.b21);
        this.buttonsArray[2][2] = activity.findViewById(R.id.b22);
    }
private boolean isSamenotEmptyCell(String cell1, String cell2, String cell3){
    if (cell1.equals(ETicTacGame.E.name()) || cell2.equals(ETicTacGame.E.name()) || cell3.equals(ETicTacGame.E.name())){
        return false;
    }
    if (cell1.equals(cell2) && cell2.equals(cell3)){
        return true;
    }
    return false;
}

    public String checkGameOver(){
        //checked rows
        for ( int row = 0; row < board.length; row++){
              if (isSamenotEmptyCell(board[row][0],board[row][1], board[row][2])){
                setAllButtonsInGame(false);
                return board[row][0];

            }
        }
        // checked col
        for ( int col = 0; col < board.length; col++){
            if (isSamenotEmptyCell(board[0][col],board[1][col], board[2][col])){
                setAllButtonsInGame(false);
                return board[0][col];
            }
        }
        //checked אלכסונים
        if (isSamenotEmptyCell(board[0][0],board[1][1], board[2][2])){
            setAllButtonsInGame(false);
            return board[0][0];
        }
        if (isSamenotEmptyCell(board[0][2],board[1][1], board[2][0])){
            setAllButtonsInGame(false);
            return board[0][2];
        }
        if (turnCounter == 9){
            setAllButtonsInGame(false);
            return ETicTacGame.Tie.name();
        }
        return ETicTacGame.E.name();
    }
    public void setValueInGame(int row, int col, String value){
        board[row][col] = value;
        turnCounter++;
        buttonsArray[row][col].setText(value);
        buttonsArray[row][col].setEnabled(false);

    }
    public void setAlfaInGame(boolean isMyTurn){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (!isMyTurn) {
                    buttonsArray[row][col].setAlpha(0.99f);
                }
                else {
                    buttonsArray[row][col].setAlpha(1f);
                }
                // לא פועל
                if (buttonsArray[row][col].equals(ETicTacGame.E.name())){
                    buttonsArray[row][col].setAlpha(0f);
                }
            }
        }
    }
    public void setAllButtonsInGame(boolean isMyTurn){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (!isMyTurn){
                    buttonsArray[row][col].setEnabled(false);
                }
                else {
                    buttonsArray[row][col].setEnabled(true);
                }
                // לא פועל
                if (buttonsArray[row][col].equals(ETicTacGame.x.name()) || buttonsArray[row][col].equals(ETicTacGame.o.name())){
                    buttonsArray[row][col].setEnabled(false);
                }

            }
        }
    }
}
