package main.java;

import java.util.Locale;
import java.util.Scanner;

public class Program {
    private Game game;
    private boolean run;

    /**
     * Allow the user to choose the game he wants to play
     */
    private void init()
    {
        float input = 0.f;
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Welcome to my project. Please select a game :");
        System.out.println("1. Research" + '\n' +
                "2. Mastermind");
        while (input >= 3 || input <= 0)
        {
            System.out.println("Choose between 1 or 2.");
            input = sc.nextFloat();
        }
        switch ((int)input) {
            case 1:
                System.out.println("You choosed the Research game.");
                game = new Research();
                break;
            case 2:
                game = new Mastermind();
                break;
        }
    }

    public Program()
    {
        try {
            init();
            game.init();
        } catch (NullPointerException e){
            System.err.println(e.getClass().getSimpleName() + " " + e.getMessage());
        }

        run = true;
    }

    public void run()
    {
        while (run != false)
        {
            game.update();
            game.draw();
        }
    }
}
