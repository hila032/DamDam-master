package com.example.salon.myapplication.models;

public class GameController {
    private TicTacGameModle gameModel;
    private int counter;
    private String winName = "tie";
    private int addX = 0;
    private int add0 = 0;
    private int addTie = 0;


    public GameController(){
        gameModel = new TicTacGameModle();
    }
    public  void  newGame(){
        counter =0;
        gameModel.newGame();
    }
    public String getCurrentPlayer(){
        if (counter % 2 == 0) {
            return "x";
        }
        return "O";
    }
    public String playerStep (int location){
        String player;
        if (counter % 2 == 0) {
            player ="x";

        } else {
            player ="O";
        }
        counter++;
        gameModel.setStep(location,player);
        return player;
    }
    public String winner(){
        return winName;
    }
    public boolean isGameOver(){
        if (counter < 5)
            return false;
        String res = gameModel.checkGameOver();
        if (!res.equals("E")){
            //winner = game over
            winName = res;
            addWinner();
            return true;
        }
        if (counter<9) {
            return false;
        }
        if (counter >= 9){
            winName ="Tie";
            addTie++;
            return true;
        }

        return counter >= 9;
    }
    public void addWinner(){
        if (winName.equals("x")){
            addX++;
        }
        if (winName.equals("O")){
            add0++;
        }


    }
    public int getX() {
        return addX;
    }
    public int get0() {
        return add0;
    }
    public int getTie() {
        return addTie;
    }

}
