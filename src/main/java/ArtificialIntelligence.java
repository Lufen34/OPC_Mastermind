package main.java;

import com.sun.xml.internal.fastinfoset.util.CharArray;

import java.util.concurrent.ThreadLocalRandom;

public class ArtificialIntelligence {
    private int numberOfAttempts;
    private int numberOfCombinations;
    private int[] max;
    private int[] min;
    private char[] memoryInfo;
    private String guess;
    private boolean guessed;

    public ArtificialIntelligence(String numberOfAttempts, String numberOfCombinations)
    {
        this.numberOfCombinations = Integer.parseInt(numberOfCombinations);
        this.numberOfAttempts = Integer.parseInt(numberOfAttempts);
        min = new int[this.numberOfCombinations];
        max = new int[this.numberOfCombinations];
        memoryInfo = new char[this.numberOfCombinations];
        guessed = false;
    }

    public void getInformation(String passwordInfo)
    {
        for (int i = 0; i < passwordInfo.length(); i++) {
            if (passwordInfo.toCharArray()[i] == '=') {
                memoryInfo[i] = '=';
            }
            else {
                memoryInfo[i] = passwordInfo.toCharArray()[i];
            }
        }
    }

    /**
     * Generate a password for the AI turn
     * The first turn, the limit is set between 0 and 9
     * After the first turn, He will learn from his mistakes and improve or reduce depending the need
     * @param turn the actual turn of the game
     * @return the password generated
     */

    public String passwordGenerator(int turn)
    {
        int i = 0;

        char[] psw = new char[this.numberOfCombinations];
        if (turn == 1) {
            for (int j = 0; j < min.length; j++) {
                min[j] = '0';
                max[j] = '9';
            }
        }
        else {
            for (int k = 0; k < min.length; k++) {
                if (memoryInfo[k] == '=') {
                    min[k] = Character.getNumericValue(guess.toCharArray()[k]);
                    max[k] = Character.getNumericValue(guess.toCharArray()[k]);
                }
                else if(memoryInfo[k] == '-'){
                    max[k] = Character.getNumericValue(guess.toCharArray()[k]);
                    min[k] = 0;
                }
                else if(memoryInfo[k] == '+'){
                    min[k] = Character.getNumericValue(guess.toCharArray()[k]);
                    max[k] = 9;
                }
            }
        }
        for (int j = 0; j < this.numberOfCombinations; j++) {
            if(memoryInfo[j] == '=')
                psw[j] = guess.toCharArray()[j];
            else {
                int random = ThreadLocalRandom.current().nextInt(min[j]+1, max[j]+1);
                psw[j] = String.valueOf(random).toCharArray()[0];
            }
        }
        guess = new String(psw);
        guessed = true;
        return guess;
    }

}
