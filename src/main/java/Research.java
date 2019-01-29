package main.java;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Research extends Game {

    /**
     * Call the Game Initializer and load the properties file
     */
    @Override
    public void init() {
        super.init();
        getConfig("src/main/resources/config.properties");
        if(gamemode == GameMode.Challenger)
        {
            passwordGenerator();
            hidePassword();
        }
        ChallengerLogicDraw();
    }

    /**
     * Update the state of the game
     */
    @Override
    public void update() {

        switch (gamemode)
        {
            case Challenger:

                break;
            case Defense:
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
        switch (gamemode)
        {
            case Challenger:
                ChallengerLogicDraw();
                break;
            case Defense:
                break;
            case Duel:
                break;
        }
    }

    public void ChallengerLogicDraw()
    {
        if(properties.get("DevMode").contains("true"))
            System.out.println("(Combinaison secrète : " + password + ')');
        else
            System.out.println("(Combinaison secrète : " + passwordHidden + ')');
        System.out.print("Proposition : " );
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        System.out.println();
    }

    /**ChallengerLogicDraw();
     * Generate a password depending the amount of of combinations allowed from the config file.
     */
    private void passwordGenerator()
    {
        char[] psw = new char[Integer.parseInt(properties.get("combinations"))];

        for (int i = 0; i < Integer.parseInt(properties.get("combinations")); i++) {
            int random = ThreadLocalRandom.current().nextInt(0, 9 + 1);
            psw[i] = String.valueOf(random).toCharArray()[0];
            System.out.println(psw[i]);
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
}
