package main.java;

import java.util.Scanner;

public class Mastermind extends Game {

    public Mastermind(GamePlayed game) {
        super(game);
    }

    @Override
    public void init() {
        super.init();
        getConfig("src/main/resources/config.properties");
        minRange = Integer.parseInt(properties.get("numberMin"));
        maxRange = Integer.parseInt(properties.get("numberMax"));
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
                bot = new AIMastermind(properties.get("numberOfAttempts"), properties.get("combinations"), properties.get("numberMin"), properties.get("numberMax"));
                break;
            case Duel:
                passwordGenerator();
                hidePassword();
                sc = new Scanner(System.in);
                System.out.print("Please select the password :");
                input = sc.nextLine();
                password = input;
                hidePassword();
                bot = new AIMastermind(properties.get("numberOfAttempts"), properties.get("combinations"), properties.get("numberMin"), properties.get("numberMax"));
                break;
        }
    }

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

    @Override
    public void draw() {
        if(run == true) {
            switch (gamemode)
            {
                case Challenger:
                    if(properties.get("DevMode").contains("true"))
                        System.out.println("(Secret combination: " + passwordAI + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    System.out.print("Proposal : " );
                    input = inputProtection();
                    System.out.println("Answer : " + passwordGuessInfo(input));
                    break;
                case Defense:
                    if(properties.get("DevMode").contains("true"))
                        System.out.println("(Secret combination: " + password + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    inputAI = bot.passwordGenerator(turn, current);
                    System.out.println("AI Proposal : " + inputAI);
                    passwordGuesserAI = passwordGuessInfo(inputAI);
                    System.out.println("Answer : " + passwordGuesserAI);
                    break;
                case Duel:
                    break;
            }
            turn++;
        }
    }

    private String passwordGuessInfo(String input)
    {
        int correctPosition = 0;
        int wrongPosition = 0;
        int[] posMemory = new int[Integer.parseInt(properties.get("combinations"))];
        int j = 0;

        switch (gamemode)
        {
            case Challenger:
                // Detect if the number is correctly placed
                for (int i = 0; i < input.length(); i++) {
                    if (input.toCharArray()[i] == passwordAI.toCharArray()[i]) {
                        correctPosition++;
                    }
                    else{
                        posMemory[j] = i;
                        j++;
                    }
                }
                /* posMemory remember the position of the character which didn't matched
                 *  Here I try to detect if the character from the input X at position Y from posMemory is present inside
                 *  the array. At the end we just need to deduce the difference.
                 **/
                for (int i = 0; i < posMemory.length; i++) {
                    for (int k = 0; k < input.length(); k++) {
                        if(input.toCharArray()[posMemory[i]] == passwordAI.toCharArray()[k]){
                            wrongPosition++;
                            break;
                        }
                    }
                }
                break;
            case Defense:
                for (int i = 0; i < input.length(); i++) {
                    if (input.toCharArray()[i] == password.toCharArray()[i]) {
                        correctPosition++;
                    }
                    else{
                        posMemory[j] = i;
                        j++;
                    }
                }
                for (int i = 0; i < posMemory.length; i++) {
                    for (int k = 0; k < input.length(); k++) {
                        if(input.toCharArray()[posMemory[i]] == password.toCharArray()[k]){
                            wrongPosition++;
                            break;
                        }
                    }
                }
                break;
            case Duel:
                break;
        }
        return  ((wrongPosition - correctPosition) < 0) ? "0":wrongPosition - correctPosition + " are present(s), " + correctPosition + " are correctly placed.";
    }
}
