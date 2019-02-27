package main.java;

import java.util.Locale;
import java.util.Scanner;


public class Program {
    private Game game;

    /**
     * Allow the user to choose the game he wants to play
     */
    private void init()
    {
        // Exception Char / string
        float input = 0.f;
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Welcome to my project. Please select a game :");
        System.out.println("1. Research" + '\n' +
                "2. Mastermind");
        while (input >= 3 || input <= 0)
        {
            input = 0.f;
            System.out.println("Choose between 1 or 2.");
            try{
                input = Float.parseFloat(sc.nextLine());
            }catch (Exception e)
            {
                System.err.println("Please enter a Integer.");
            }
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
    }

    public void run()
    {
        while (game.isRunning())
        {
            game.update();
            game.draw();
        }
    }
}
