package com.example.salon.myapplication.models;

import android.app.Activity;
import android.graphics.Color;
import android.widget.Button;

import com.example.salon.myapplication.ETicTacGame;
import com.example.salon.myapplication.R;

public class TicTacBoard {
    private ETicTacGame[][] board = new ETicTacGame[][] {{ETicTacGame.E,ETicTacGame.E,ETicTacGame.E},
            {ETicTacGame.E,ETicTacGame.E,ETicTacGame.E},
            {ETicTacGame.E,ETicTacGame.E,ETicTacGame.E}};
    private Button[][] buttonsArray= new Button[3][3];


    public TicTacBoard(Activity activity) {
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
    private boolean isSameNotEmptyCell(ETicTacGame cell1, ETicTacGame cell2, ETicTacGame cell3){
        if (cell1 == ETicTacGame.E || cell2 == ETicTacGame.E || cell3 == ETicTacGame.E){
            return false;
        }
        if (cell1.equals(cell2) && cell2.equals(cell3)){
            return true;
        }
        return false;
    }
    private boolean isNotEmpty(){
        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 3; col++){
                if (board[row][col] == ETicTacGame.E){
                    return false;
                }
            }
        }
        return true;
    }

    public ETicTacGame checkGameOver(){
        //checked rows
        for (int row = 0; row < board.length; row++){
              if (isSameNotEmptyCell(board[row][0],board[row][1], board[row][2])){
                setAllButtonsInGame(false);
                return board[row][0];
            }
        }
        // checked col
        for ( int col = 0; col < board.length; col++){
            if (isSameNotEmptyCell(board[0][col],board[1][col], board[2][col])){
                setAllButtonsInGame(false);
                return board[0][col];
            }
        }
        //checked אלכסונים
        if (isSameNotEmptyCell(board[0][0],board[1][1], board[2][2])){
            setAllButtonsInGame(false);
            return board[0][0];
        }
        if (isSameNotEmptyCell(board[0][2],board[1][1], board[2][0])){
            setAllButtonsInGame(false);
            return board[0][2];
        }
        if (isNotEmpty() ){
            setAllButtonsInGame(false);
            return ETicTacGame.TIE;
        }
        return ETicTacGame.E;
    }

    public void setValueInGame(int row, int col, String value){
        board[row][col] = ETicTacGame.valueOf(value);
        buttonsArray[row][col].setText(value);
        buttonsArray[row][col].setEnabled(false);

    }
    public void setAlfaInGame(boolean isMyTurn){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (!isMyTurn) {
                    buttonsArray[row][col].setTextColor(Color.GRAY);
                }
                else {
                    buttonsArray[row][col].setTextColor(Color.BLACK);
                }
                // לא פועל
                if (board[row][col] == (ETicTacGame.E)){
                    buttonsArray[row][col].setTextColor(Color.TRANSPARENT);
                }
            }
        }
    }
    public void setAllButtonsInGame(boolean isMyTurn){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttonsArray[row][col].setEnabled(isMyTurn);
                // לא פועל
                if (board[row][col] !=(ETicTacGame.E)){
                    buttonsArray[row][col].setEnabled(false);
                }
            }
        }
    }
}