package main.java;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Scanner;


public class Program {
    private Game game;
    private static final Logger LOGGER = LogManager.getLogger(Program.class);

    /**
     * Allow the user to choose the game he wants to play
     */
    private void init() {
        float input = 0.f;
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Welcome to my project. Please select a game :");
        System.out.println("1. Research" + '\n' +
                "2. Mastermind");
        while (input >= 3 || input <= 0) {
            input = 0.f;
            System.out.println("Choose between 1 or 2.");
            try {
                input = Float.parseFloat(sc.nextLine());
            } catch (Exception e) {
                LOGGER.error("Please enter a Integer");
            }
        }
        switch ((int) input) {
            case 1:
                System.out.println("You choosed the Research game.");
                game = new Research(GamePlayed.Reasearch);
                break;
            case 2:
                game = new Mastermind(GamePlayed.Mastermind);
                break;
        }
    }

    public Program() {
        try {
            init();
            game.init();
        } catch (NullPointerException e) {
            System.err.println();
            LOGGER.error(e.getClass().getSimpleName() + " " + e.getMessage());
        }
    }

    public void run() {
        while (game.isRunning()) {
            game.update();
            game.draw();
        }
    }
}
