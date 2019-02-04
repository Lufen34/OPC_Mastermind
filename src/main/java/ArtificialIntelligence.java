package main.java;

import java.util.concurrent.ThreadLocalRandom;

public class ArtificialIntelligence {
    private int numberOfAttempts;
    private int numberOfCombinations;
    private char[] memory;
    private char[] answer;
    private String guess;

    public ArtificialIntelligence(String numberOfAttempts, String numberOfCombinations)
    {
        this.numberOfCombinations = Integer.parseInt(numberOfCombinations);
        this.numberOfAttempts = Integer.parseInt(numberOfAttempts);
        memory = new char[this.numberOfCombinations];
        answer = new char[this.numberOfCombinations];
    }

    public void getInformation(String passwordInfo)
    {
        for (int i = 0; i < passwordInfo.length(); i++) {
            if (passwordInfo.toCharArray()[i] == '=') {
                memory[i] = '=';
                answer[i] = guess.toCharArray()[i];
            }
            else {
                memory[i] = passwordInfo.toCharArray()[i];
            }
        }
    }

    public String passwordGenerator()
    {
        boolean guessed = true;
        int i = 0;
        try {
            while (guessed == true || i >= memory.length)
            {
                guessed = (memory[i] == '=') ? true : false;
                i++;
            }
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.err.println(getClass().getSimpleName() + " " + e.getMessage());
        }finally {
            System.out.println(memory);
        }

        if (guessed == true)
            return new String(answer);
        else {
            char[] psw = new char[this.numberOfCombinations];

            for (int j = 0; j < this.numberOfCombinations; j++) {
                int random = ThreadLocalRandom.current().nextInt(0, 9 + 1);
                psw[j] = String.valueOf(random).toCharArray()[0];
            }
            guess = new String(psw);
            return guess;
        }

    }

}
