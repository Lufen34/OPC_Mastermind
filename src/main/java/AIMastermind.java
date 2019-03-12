package main.java;

import java.util.concurrent.ThreadLocalRandom;

public class AIMastermind extends ArtificialIntelligence {
    public AIMastermind(String numberOfAttempts, String numberOfCombinations, String numberMin, String numberMax) {
        super(numberOfAttempts, numberOfCombinations, numberMin, numberMax);
    }

    public String passwordGenerator(int turn, GamePlayed game) {
        char[] psw = new char[this.numberOfCombinations];

        for (int j = 0; j < this.numberOfCombinations; j++) {
            int random = ThreadLocalRandom.current().nextInt(0, 9 + 1);
            psw[j] = String.valueOf(random).toCharArray()[0];
        }
        guess = new String(psw);
        return guess;
    }
}
