package main.java;

import java.util.Scanner;

public class main {
    /**
     * Ask the user which game he want to play from the input.
     * @param game the game the user will choose to play.
     * @return
     */
    public static Game gameSetup(Game game) {

        int input = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to my project. Please select a game :");
        System.out.println("1. Research" + '\n' +
                "2. Mastermind");
        while (input >= 3 || input <= 0)
        {
            System.out.println("Choose between 1 or 2.");
            input = sc.nextInt();
        }
        switch (input) {
            case 1:
                game = new Research();
                game.setGameID(1);
                break;
            case 2:
                game = new Mastermind();
                game.setGameID(2);
                break;
            }
        return game;
    }

    public static void main(String[] args) {
        Game game = gameSetup(new Research());

        game.init();
        switch (game.getGameMode())
        {
            case Challenger:
                game.runChallenger();
                break;
            case Defense:
                game.runDefense();
                break;
            case Duel:
                game.runDuel();
                break;
        }
    }
}
