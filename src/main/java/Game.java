package main.java;

import java.util.Scanner;

public abstract class Game implements Ilogic {

    protected Mode gameMode;
    protected int gameID = 0;

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }

    public int getGameID() {
        return gameID;
    }
    public Mode getGameMode() {
        return gameMode;
    }
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
