package com.example.salon.myapplication.models;

public class RecordPlayer {
    private String name;
    private int score;
    private long id;

    public RecordPlayer(String name, int score, long id) {
        this.name = name;
        this.score = score;
        this.id = id;
    }
    public RecordPlayer(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
