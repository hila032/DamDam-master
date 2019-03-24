package com.example.salon.myapplication.models;

public class TicTacGameModle {
    private String[][] board;
    private final int SIZE =3;
    // e - empty location

    public TicTacGameModle(){
        board = new String[SIZE][SIZE];
        newGame();

    }
    public void newGame(){
        for (int row = 0; row < SIZE; row++){
            for (int col = 0; col < board[row].length; col++){
                board[row][col] = "E";
            }
        }
    }
    public void setStep (int location, String player){
        int row = location /SIZE;
        int col = location % SIZE;
        board[row][col] = player;
    }

    /**
     *  E = no winner
     * @return
     */
    public String checkGameOver(){
        String winner = "E";
        //checked rows
        for ( int row = 0; row < SIZE; row++){
            if (board[row][0].equals("E")){
                continue;
            }
            if (board[row][0].equals(board[row][1])&& board[row][1].equals(board[row][2])){
                return board[row][0];
            }
        }
        // checked col
        for ( int col = 0; col < SIZE; col++){
            if (board[0][col].equals("E")){
                continue;
            }
            if (board[0][col].equals(board[1][col])&& board[1][col].equals(board[2][col])){
                return board[0][col];
            }
        }
        if (board[0][0].equals(board[1][1])&&
                board[1][1].equals(board[2][2]) &&
                !board[0][0].equals("E")) {
            return board[0][0];
        }
        if (board[0][2].equals(board[1][1])&&
                board[1][1].equals(board[2][0]) &&
                !board[0][2].equals("E")) {
            return board[0][2];
        }
        return winner;
    }

}
