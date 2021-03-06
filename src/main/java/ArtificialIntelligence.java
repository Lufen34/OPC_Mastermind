package main.java;

import java.util.concurrent.ThreadLocalRandom;

public abstract class ArtificialIntelligence {
    protected int numberOfAttempts;
    protected int numberOfCombinations;
    protected int[] max;
    protected int[] min;
    protected char[] memoryInfo;
    protected String guess;
    protected char minRange;
    protected char maxRange;

    public ArtificialIntelligence(String numberOfAttempts, String numberOfCombinations, String numberMin, String numberMax) {
        this.numberOfCombinations = Integer.parseInt(numberOfCombinations);
        this.numberOfAttempts = Integer.parseInt(numberOfAttempts);
        min = new int[this.numberOfCombinations];
        max = new int[this.numberOfCombinations];
        memoryInfo = new char[this.numberOfCombinations];
        minRange = numberMin.toCharArray()[0];
        maxRange = numberMax.toCharArray()[0];
    }

    public void getInformation(String passwordInfo) {
        for (int i = 0; i < passwordInfo.length(); i++) {
            if (passwordInfo.toCharArray()[i] == '=') {
                memoryInfo[i] = '=';
            } else {
                memoryInfo[i] = passwordInfo.toCharArray()[i];
            }
        }
    }

    /**
     * Generate a password for the AI turn
     * The first turn, the limit is set between 0 and 9
     * After the first turn, He will learn from his mistakes and improve or reduce depending the need
     *
     * @param turn the actual turn of the game
     * @return the password generated
     */

    public String passwordGenerator(int turn, GamePlayed game) {
        int i = 0;

        char[] psw = new char[this.numberOfCombinations];
        if (turn == 1) {
            for (int j = 0; j < min.length; j++) {
                min[j] = '0';
                max[j] = '9';
            }
        } else {
            for (int k = 0; k < min.length; k++) {
                if (memoryInfo[k] == '=') {
                    min[k] = Character.getNumericValue(guess.toCharArray()[k]);
                    max[k] = Character.getNumericValue(guess.toCharArray()[k]);
                } else if (memoryInfo[k] == '-') {
                    max[k] = Character.getNumericValue(guess.toCharArray()[k]);
                    min[k] = 0;
                } else if (memoryInfo[k] == '+') {
                    min[k] = Character.getNumericValue(guess.toCharArray()[k]);
                    max[k] = 9;
                }
            }
        }
        for (int j = 0; j < this.numberOfCombinations; j++) {
            if (memoryInfo[j] == '=')
                psw[j] = guess.toCharArray()[j];
            else {
                int random = ThreadLocalRandom.current().nextInt(min[j] + 1, max[j] + 1);
                psw[j] = String.valueOf(random).toCharArray()[0];
            }
        }
        guess = new String(psw);
        return guess;
    }
}
