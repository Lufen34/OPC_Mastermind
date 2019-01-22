package main.java;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.Scanner;

public class Research extends Game {

    /**
     * Initialise the game with the user input, Allowing the user to choose which mode he want to play.
     */
    @Override
    public void init() {
        super.init();
        Scanner sc = new Scanner(System.in);
        int input = 0;

        System.out.println("You choosed the Research game !");
        System.out.println("Select the mode you want to play :\n" +
                "1. Challenger mode\n" +
                "2. Defense mode\n" +
                "3. Duel mode");
        while (input >= 4 || input <= 0)
        {
            System.out.println("Choose between 1, 2, 3.");
            input = sc.nextInt();
        }
        switch(input)
        {
            case 1:
                gameMode = Mode.Challenger;
                break;
            case 2:
                gameMode = Mode.Defense;
                break;
            case 3:
                gameMode = Mode.Duel;
                break;
        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void runDuel() {

    }

    @Override
    public void runChallenger() {

    }

    @Override
    public void runDefense() {

    }

}
