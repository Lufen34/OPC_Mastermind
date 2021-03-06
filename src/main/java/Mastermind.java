package main.java;

public class Mastermind extends Game {

    public Mastermind(GamePlayed game, boolean devmode) {
        super(game, devmode);
    }

    @Override
    public void init() {
        super.init();
        getConfig("src/main/resources/config.properties");
        minRange = Integer.parseInt(properties.get("numberMin"));
        maxRange = Integer.parseInt(properties.get("numberMax"));
        if(devmode == false && properties.get("devmode").toLowerCase().equals("true"))
            devmode = true;
        switch (gamemode) {
            case Challenger:
                passwordGenerator();
                passwordHidden = hidePassword(passwordAI);
                break;
            case Defense:
                System.out.print("Please select the password :");
                String input = inputProtection();
                password = input;
                passwordHidden = hidePassword(password);
                bot = new AIMastermind(properties.get("numberOfAttempts"), properties.get("combinations"), properties.get("numberMin"), properties.get("numberMax"));
                break;
            case Duel:
                passwordGenerator();
                passwordHidden = hidePassword(passwordAI);
                System.out.print("Please select the password :");
                input = inputProtection();
                password = input;
                passwordHidden = hidePassword(password);
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
        if (run == true) {
            switch (gamemode) {
                case Challenger:
                    if (devmode == true)
                        System.out.println("(Secret combination: " + passwordAI + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    System.out.print("Proposal : ");
                    input = inputProtection();
                    System.out.println("Answer : " + passwordGuessInfo(input, true));
                    break;
                case Defense:
                    if (devmode == true)
                        System.out.println("(Secret combination: " + password + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    inputAI = bot.passwordGenerator(turn, current);
                    System.out.println("AI Proposal : " + inputAI);
                    passwordGuesserAI = passwordGuessInfo(inputAI, false);
                    System.out.println("Answer : " + passwordGuesserAI);
                    break;
                case Duel:
                    if (devmode == true)
                        System.out.println("(Secret combination: " + passwordAI + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    System.out.print("Proposal : ");
                    input = inputProtection();
                    System.out.println("Answer : " + passwordGuessInfo(input, true));
                    if (devmode == true)
                        System.out.println("(Secret combination: " + password + ')');
                    else
                        System.out.println("(Secret combination : " + passwordHidden + ')');
                    inputAI = bot.passwordGenerator(turn, current);
                    System.out.println("AI Proposal : " + inputAI);
                    passwordGuesserAI = passwordGuessInfo(inputAI, false);
                    System.out.println("Answer : " + passwordGuesserAI);
                    break;
            }
            turn++;
        }
    }

    private String passwordGuessInfo(String input, boolean player) {
        int correctPosition = 0;
        int wrongPosition = 0;
        int[] posMemory = new int[Integer.parseInt(properties.get("combinations"))];
        int j = 0;

        switch (gamemode) {
            case Challenger:
                // Detect if the number is correctly placed
                for (int i = 0; i < input.length(); i++) {
                    if (input.toCharArray()[i] == passwordAI.toCharArray()[i]) {
                        correctPosition++;
                    } else {
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
                        if (input.toCharArray()[posMemory[i]] == passwordAI.toCharArray()[k]) {
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
                    } else {
                        posMemory[j] = i;
                        j++;
                    }
                }
                for (int i = 0; i < posMemory.length; i++) {
                    for (int k = 0; k < input.length(); k++) {
                        if (input.toCharArray()[posMemory[i]] == password.toCharArray()[k]) {
                            wrongPosition++;
                            break;
                        }
                    }
                }
                break;
            case Duel:
                if (player == true) {
                    for (int i = 0; i < input.length(); i++) {
                        if (input.toCharArray()[i] == passwordAI.toCharArray()[i]) {
                            correctPosition++;
                        } else {
                            posMemory[j] = i;
                            j++;
                        }
                    }
                    for (int i = 0; i < posMemory.length; i++) {
                        for (int k = 0; k < input.length(); k++) {
                            if (input.toCharArray()[posMemory[i]] == passwordAI.toCharArray()[k]) {
                                wrongPosition++;
                                break;
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < input.length(); i++) {
                        if (input.toCharArray()[i] == password.toCharArray()[i]) {
                            correctPosition++;
                        } else {
                            posMemory[j] = i;
                            j++;
                        }
                    }
                    for (int i = 0; i < posMemory.length; i++) {
                        for (int k = 0; k < input.length(); k++) {
                            if (input.toCharArray()[posMemory[i]] == password.toCharArray()[k]) {
                                wrongPosition++;
                                break;
                            }
                        }
                    }
                }
                break;
        }
        return ((wrongPosition - correctPosition) < 0) ? "0" : wrongPosition - correctPosition + " are present(s), " + correctPosition + " are correctly placed.";
    }
}
