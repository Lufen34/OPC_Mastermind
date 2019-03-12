package main.java;

import java.util.Scanner;

public class Research extends Game {

    public Research(GamePlayed game) {
        super(game);
    }

    /**
     * Call the Game Initializer and load the properties file
     */
    @Override
    public void init() {
        super.init();
        getConfig("src/main/resources/config.properties");
        switch (gamemode) {
            case Challenger:
                passwordGenerator();
                hidePassword();
                break;
            case Defense:
                System.out.print("Please select the password :");
                input = inputProtection();
                password = input;
                hidePassword();
                bot = new AIResearch(properties.get("numberOfAttempts"), properties.get("combinations"), properties.get("numberMin"), properties.get("numberMax"));
                break;
            case Duel:
                passwordGenerator();
                hidePassword();
                System.out.print("Please select the password :");
                input = inputProtection();
                password = input;
                hidePassword();
                bot = new AIResearch(properties.get("numberOfAttempts"), properties.get("combinations"), properties.get("numberMin"), properties.get("numberMax"));
                break;
        }

    }

    /**
     * Update the state of the game
     */
    @Override
    public void update() {

        switch (gamemode) {
            case Challenger:
                if (input.equals(passwordAI)) {
                    System.out.println("You won the game !");
                    AskRetry();
                }
                if (turn >= Integer.parseInt(properties.get("numberOfAttempts")) + 1) {
                    System.out.println("You lost the game !");
                    AskRetry();
                }
                break;
            case Defense:
                if (inputAI.equals(password)) {
                    System.out.println("You lost the game !");
                    AskRetry();
                }
                if (turn >= Integer.parseInt(properties.get("numberOfAttempts")) + 1) {
                    System.out.println("You Win the game !");
                    AskRetry();
                }
                break;
            case Duel:
                if (input.equals(passwordAI) && inputAI.equals(password)) {
                    System.out.println("This is a Draw !");
                    AskRetry();
                } else if (input.equals(passwordAI)) {
                    System.out.println("You won the game !");
                    AskRetry();
                } else if (inputAI.equals(password)) {
                    System.out.println("You lost the game !");
                    AskRetry();
                } else if (turn >= Integer.parseInt(properties.get("numberOfAttempts")) + 1) {
                    System.out.println("No one win the game !");
                    AskRetry();
                }
                break;
        }
    }

    /**
     * Show the changes to the screen for the user
     */
    @Override
    public void draw() {
        if (run == true) {
            switch (gamemode) {
                case Challenger:
                    if (properties.get("DevMode").contains("true"))
                        System.out.println("(Secret combination: " + passwordAI + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    System.out.print("Proposal : ");
                    input = inputProtection();
                    passwordGuesser = guessPasswordInfo(input, false);
                    System.out.println("Answer : " + passwordGuesser);
                    break;
                case Defense:
                    if (properties.get("DevMode").contains("true"))
                        System.out.println("(Secret combination: " + password + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    inputAI = bot.passwordGenerator(turn, current);
                    System.out.println("AI Proposal : " + inputAI);
                    passwordGuesserAI = guessPasswordInfo(inputAI, false);
                    System.out.println("Answer : " + passwordGuesserAI);
                    bot.getInformation(passwordGuesserAI);
                    break;
                case Duel:
                    if (properties.get("DevMode").contains("true"))
                        System.out.println("(Secret combination: " + passwordAI + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    System.out.print("Player Proposal : ");
                    input = inputProtection();
                    passwordGuesser = guessPasswordInfo(input, true);
                    System.out.println("Answer : " + passwordGuesser);
                    if (properties.get("DevMode").contains("true"))
                        System.out.println("(Secret combination: " + password + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    inputAI = bot.passwordGenerator(turn, current);
                    System.out.println("AI Proposal : " + inputAI);
                    passwordGuesserAI = guessPasswordInfo(inputAI, false);
                    System.out.println("Answer : " + passwordGuesserAI);
                    bot.getInformation(passwordGuesserAI);
                    break;
            }
            turn++;
        }
    }

    /**
     * Tell the user if his input was lower, upper or equals to the answer
     *
     * @param inputUser The user input
     * @return the information of the input from the user or the AI
     */
    private String guessPasswordInfo(String inputUser, boolean IsHumanPlayer) {
        char[] guess = null;

        switch (gamemode) {
            case Challenger:
                guess = new char[passwordAI.length()];
                for (int i = 0; i < passwordAI.length(); i++) {
                    if (inputUser.toCharArray()[i] == passwordAI.toCharArray()[i])
                        guess[i] = '=';
                    else if (inputUser.toCharArray()[i] < passwordAI.toCharArray()[i]) //Work with ascii table
                        guess[i] = '+';
                    else if (inputUser.toCharArray()[i] > passwordAI.toCharArray()[i])
                        guess[i] = '-';
                }
                break;
            case Defense:
                guess = new char[password.length()];
                for (int i = 0; i < password.length(); i++) {
                    if (inputUser.toCharArray()[i] == password.toCharArray()[i])
                        guess[i] = '=';
                    else if (inputUser.toCharArray()[i] < password.toCharArray()[i]) //Work with ascii table
                        guess[i] = '+';
                    else if (inputUser.toCharArray()[i] > password.toCharArray()[i])
                        guess[i] = '-';
                }
                break;
            case Duel:
                if (IsHumanPlayer) {
                    guess = new char[passwordAI.length()];
                    for (int i = 0; i < passwordAI.length(); i++) {
                        if (inputUser.toCharArray()[i] == passwordAI.toCharArray()[i])
                            guess[i] = '=';
                        else if (inputUser.toCharArray()[i] < passwordAI.toCharArray()[i]) //Work with ascii table
                            guess[i] = '+';
                        else if (inputUser.toCharArray()[i] > passwordAI.toCharArray()[i])
                            guess[i] = '-';
                    }
                } else {
                    guess = new char[password.length()];
                    for (int i = 0; i < password.length(); i++) {
                        if (inputUser.toCharArray()[i] == password.toCharArray()[i])
                            guess[i] = '=';
                        else if (inputUser.toCharArray()[i] < password.toCharArray()[i]) //Work with ascii table
                            guess[i] = '+';
                        else if (inputUser.toCharArray()[i] > password.toCharArray()[i])
                            guess[i] = '-';
                    }
                }
                break;
        }
        return new String(guess);
    }


}
