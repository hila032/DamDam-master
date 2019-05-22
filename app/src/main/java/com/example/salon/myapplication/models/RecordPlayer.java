package com.example.salon.myapplication.models;

public class RecordPlayer {
    private String name;
    private int win;
    private int loss;
    private int tie;
    private long id;

    public RecordPlayer(String name, int win, int loss, int tie, long id) {
        this.name = name;
        this.win = win;
        this.loss = loss;
        this.tie = tie;
        this.id = id;
    }

    public RecordPlayer(String name, int win, int loss, int tie) {
        this.name = name;
        this.win = win;
        this.loss = loss;
        this.tie = tie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWin() {
        return win;
    }


    public int getLoss() {
        return loss;
    }
    public int getTie() {
        return tie;
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }
}
