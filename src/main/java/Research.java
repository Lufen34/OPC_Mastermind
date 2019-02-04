package main.java;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Research extends Game {
    private String input ="";

    /**
     * Call the Game Initializer and load the properties file
     */
    @Override
    public void init() {
        super.init();
        getConfig("src/main/resources/config.properties");
        switch(gamemode)
        {
            case Challenger:
                passwordGenerator();
                hidePassword();
                break;
            case Defense:
                Scanner sc = new Scanner(System.in);
                System.out.print("Please select the password :");
                String input = sc.nextLine();
                password = input;
                hidePassword();
                bot = new ArtificialIntelligence(properties.get("numberOfAttempts"), properties.get("combinations"));
                break;
            case Duel:
                break;
        }

    }

    /**
     * Update the state of the game
     */
    @Override
    public void update() {

        switch (gamemode)
        {
            case Challenger:
                turn++;
                if (input.equals(password))
                {
                    System.out.println("You won the game !");
                    AskRetry();
                }
                if (turn >= Integer.parseInt(properties.get("numberOfAttempts")))
                {
                    System.out.println("You lost the game !");
                    AskRetry();
                }
                break;
            case Defense:
                turn++;
                if (turn >= Integer.parseInt(properties.get("numberOfAttempts")))
                {
                    System.out.println("You lost the game !");
                    AskRetry();
                }
                break;
            case Duel:
                break;
        }
    }

    /**
     * Show the changes to the screen for the user
     */
    @Override
    public void draw() {
        if(run == true) {
            switch (gamemode)
            {
                case Challenger:
                    if(properties.get("DevMode").contains("true"))
                        System.out.println("(Secret combination: " + password + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    System.out.print("Proposal : " );
                    Scanner sc = new Scanner(System.in);
                    input = sc.nextLine();  // Need un string pas un int
                    guessPasswordInfo(input);
                    System.out.println("Answer : " + passwordGuesser);
                    break;
                case Defense:
                    if(properties.get("DevMode").contains("true"))
                        System.out.println("(Secret combination: " + password + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    System.out.println("Proposal : " + bot.passwordGenerator());
                    guessPasswordInfo(bot.passwordGenerator());
                    bot.getInformation(passwordGuesser);
                    break;
                case Duel:
                    break;
            }
        }
    }

    /**
     * Generate a password depending the amount of of combinations allowed from the config file.
     */
    private void passwordGenerator()
    {
        char[] psw = new char[Integer.parseInt(properties.get("combinations"))];

        for (int i = 0; i < Integer.parseInt(properties.get("combinations")); i++) {
            int random = ThreadLocalRandom.current().nextInt(0, 9 + 1);
            psw[i] = String.valueOf(random).toCharArray()[0];
            //System.out.println(psw[i]);
        }
        password = new String(psw);
        passwordHidden = new String(psw);
        //System.out.println("psw :"+ password);
    }

    /**
     * Hide the password from the user
     */
    private void hidePassword()
    {
        char[] psw = password.toCharArray();
        for (int i = 0; i < password.length(); i++) {
            psw[i] = '*';
        }
        passwordHidden = new String(psw);
    }

    /**
     * Tell the user if his input was lower, upper or equals to the answer
     * @param inputUser The user input
     */
    private void guessPasswordInfo(String inputUser)
    {
        char[] guess = new char[password.length()];
        for (int i = 0; i < password.length(); i++) {
            if(inputUser.toCharArray()[i] == password.toCharArray()[i])
                guess[i] = '=';
            else if (inputUser.toCharArray()[i] < password.toCharArray()[i]) //Work with ascii table
                guess[i] = '+';
            else if (inputUser.toCharArray()[i] > password.toCharArray()[i])
                guess[i] = '-';
        }
        passwordGuesser = new String(guess);
    }

    /**
     * Ask the user if he want's to continue playing or not
     */
    private void AskRetry()
    {
        Scanner sc = new Scanner(System.in);
        float in;
        System.out.println("Do you want to play again ?\n" +
                "1. Yes\n"+
                "2. No");
        in = sc.nextFloat();
        while (in >= 3 || in <= 0)
        {
            System.out.println("Choose between 1 or 2.");
            in = sc.nextFloat();
        }
        switch ((int)in)
        {
            case 1:
                init();
                break;
            case 2:
                run = false;
                break;
        }
    }
}
