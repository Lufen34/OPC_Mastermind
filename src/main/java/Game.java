package main.java;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Game implements IGameLogic{
    protected GameMode gamemode;
    protected String password ="";
    protected String passwordHidden="";
    protected String passwordGuesser="";
    protected List<String> config;
    protected Map<String, String> properties;
    protected boolean run;
    protected int turn;
    protected ArtificialIntelligence bot;
    protected String input = "";
    protected String passwordAI = "";
    protected String inputAI = "";
    protected String passwordGuesserAI = "";
    protected int minRange = 0;
    protected int maxRange = 9;
    protected GamePlayed current;


    public Game(GamePlayed game)
    {
        properties = new HashMap<>();
        run = true;
        this.current = game;
    }

    /**
     * Return the whole text before the char at position marker
     * @param text the content that you want to extract from
     * @param marker the position of the char
     * @return
     */
    private String getPrefix(String text, int marker)
    {
        char[] key = new char[marker];
        for (int i = 0; i < marker; i++) {
            key[i] = text.charAt(i);
        }
        return new String(key);
    }

    /**
     * Return the whole text after the char at position marker
     * @param text the content that you want to extract from
     * @param marker the position of the char
     * @return
     */
    private String getSuffix(String text, int marker)
    {
        char[] key = new char[text.length() - marker -1];

        for (int i = 1; i < text.length() - marker; i++) {
            key[i-1] = text.charAt(marker+i);
        }
        return new String(key);
    }

    /**
     * Get the game configuration from the file specified
     * @param file the file you need to read
     */
    protected void getConfig(String file)
    {
        Path path = Paths.get(file);

        try {
            config = Files.readAllLines(path);

            for (String content:config)
            {
                if(content.contains("="))
                {
                    String prefix=getPrefix(content, content.indexOf("="));
                    String suffix=getSuffix(content, content.indexOf("="));
                    properties.put(prefix, suffix);
                }
            }
        }catch (IOException e){
            Logger logger = Logger.getLogger(Program.class);
            BasicConfigurator.configure();
            logger.info("Test", e);
        }
    }


    /**
     * Allow the user to choose the mode on which he wants to play at
     */
    @Override
    public void init() {
        // Exception Char / string
        float input = 0.f;
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Please select a mode :");
        System.out.println("1. Challenger\n" +
                "2. Defense\n"+
                "3. Duel");
        while (input >= 4 || input <= 0)
        {
            System.out.println("Choose between 1, 2 or 3.");
            try{
                input = Float.parseFloat(sc.nextLine());
            }catch (Exception e)
            {
                System.err.println("Please enter a Integer.");
            }
        }
        switch ((int)input) {
            case 1:
                System.out.println("You choosed the Challenger game.");
                gamemode = GameMode.Challenger;
                break;
            case 2:
                System.out.println("You choosed the Defense game.");
                gamemode = GameMode.Defense;
                break;
            case 3:
                System.out.println("You choosed the Duel game.");
                gamemode = GameMode.Duel;
                break;
        }
        password = "";
        passwordHidden = "";
        passwordGuesser = "";
        passwordAI = "";
        passwordGuesserAI = "";
        turn = 1;
    }

    /**
     * Generate a password depending the amount of of combinations allowed from the config file.
     */
    protected void passwordGenerator()
    {
        char[] psw = new char[Integer.parseInt(properties.get("combinations"))];

        for (int i = 0; i < Integer.parseInt(properties.get("combinations")); i++) {
            int random = ThreadLocalRandom.current().nextInt(minRange, maxRange + 1);
            psw[i] = String.valueOf(random).toCharArray()[0];
            //System.out.println(psw[i]);
        }
        passwordAI = new String(psw);
        passwordHidden = new String(psw);
    }

    /**
     * Hide the password from the user
     */
    protected void hidePassword()
    {
        char[] psw = passwordAI.toCharArray();
        for (int i = 0; i < passwordAI.length(); i++) {
            psw[i] = '*';
        }
        passwordHidden = new String(psw);
    }

    /**
     * Ask the user if he want's to continue playing or not
     */
    protected void AskRetry()
    {
        Scanner sc = new Scanner(System.in);
        float in = 0.f;
        System.out.println("Do you want to play again ?\n" +
                "1. Yes\n"+
                "2. No");
        while (in >= 3 || in <= 0)
        {
            System.out.println("Choose between 1 or 2.");
            try{
                in = Float.parseFloat(sc.nextLine());
            }catch (Exception e)
            {
                System.err.println("Please enter a Integer.");
            }
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

    /**
     * Test the input of the user in order to detect if he use no letter and the right amount of digits as configured
     * in the properties file.
     * @return
     */

    private boolean isTheRightAmount(String input)
    {
        if (input.length() < Integer.parseInt(properties.get("combinations")) || input.length() > Integer.parseInt(properties.get("combinations")))
        {
            System.err.println("Please enter a number with " + Integer.parseInt(properties.get("combinations")) + " digits");
            return false;
        }
        else
            return true;
    }

    private boolean isOnlyNumbers(String input)
    {
        for (int i = 0; i < input.length(); i++) {
            if (input.toCharArray()[i] < '0' || input.toCharArray()[i] > '9') {
                System.err.println("Please enter only integers.");
                return false;
            }
        }
        return true;
    }

    protected String inputProtection() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (isOnlyNumbers(input) == false || isTheRightAmount(input) == false) {
            input = sc.nextLine();
        }
        return input;
    }

    public GameMode getGamemode() {
        return gamemode;
    }

    public void setGamemode(GameMode gamemode) {
        this.gamemode = gamemode;
    }

    public boolean isRunning() {
        return run;
    }
}
