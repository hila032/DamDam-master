package com.example.salon.myapplication.models;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;

import com.example.salon.myapplication.R;

public class TicTacBoard {
    private String[][] board = new String[][]{{"E","E","E"},
                                                {"E","E","E"},
                                                {"E","E","E"}};
    private Button[][] buttonsArray= new Button[3][3];
    private Activity activity;

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

    public String checkGameOver(){
        //checked rows
        for ( int row = 0; row < board.length; row++){
            if (board[row][0].equals("E")){
                continue;
            }else
            if (board[row][0].equals(board[row][1])&& board[row][1].equals(board[row][2])){
                setAllButtonsInGame(false);
                return board[row][0];
            }
        }
        // checked col
        for ( int col = 0; col < board.length; col++){
            if (board[0][col].equals("E")){
                continue;
            }
            if (board[0][col].equals(board[1][col])&& board[1][col].equals(board[2][col])){
                setAllButtonsInGame(false);
                return board[0][col];
            }
        }
        //checked אלכסונים
        if (board[0][0].equals(board[1][1])&&
                board[1][1].equals(board[2][2]) &&
                !board[0][0].equals("E")) {
            setAllButtonsInGame(false);
            return board[0][0];
        }
        if (board[0][2].equals(board[1][1])&&
                board[1][1].equals(board[2][0]) &&
                !board[0][2].equals("E")) {
            setAllButtonsInGame(false);
            return board[0][2];
        }
        return "E";
    }
    public void setValueInGame(int row, int col, String value){
        board[row][col] = value;
        buttonsArray[row][col].setText(value);
        buttonsArray[row][col].setEnabled(false);

    }
    public void setAlfaInGame(boolean isMyTurn){
        if (isMyTurn == false) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    buttonsArray[row][col].setAlpha(0.99f);
                }
            }
        }
        else {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    buttonsArray[row][col].setAlpha(1);
                }
            }
        }
    }
    public void setAllButtonsInGame(boolean isMyTurn){
        if (isMyTurn == false) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    buttonsArray[row][col].setEnabled(false);
                }
            }
        }
        else {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    buttonsArray[row][col].setEnabled(true);
                }
            }
        }
    }
}
