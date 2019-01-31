package main.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public abstract class Game implements IGameLogic{
    protected GameMode gamemode;
    protected String password ="";
    protected String passwordHidden="";
    protected String passwordGuesser="";
    protected List<String> config;
    protected Map<String, String> properties;
    protected boolean run;
    protected int user


    public Game()
    {
        properties = new HashMap<>();
        run = true;
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
                    //System.out.println("prefix:" + getPrefix(content, content.indexOf("=")));
                    //System.out.println("Suffix:" + getSuffix(content, content.indexOf("=")));
                    properties.put(prefix, suffix);
                }
            }
        }catch (IOException e){
            System.err.println(e.getClass().getSimpleName() + " " + e.getMessage());
        }
    }


    /**
     * Allow the user to choose the mode on which he wants to play at
     */
    @Override
    public void init() {
        float input = 0.f;
        Scanner sc = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Please select a mode :");
        System.out.println("1. Challenger\n" +
                "2. Defense\n"+
                "3. Duel");
        while (input >= 4 || input <= 0)
        {
            System.out.println("Choose between 1, 2 or 3.");
            input = sc.nextFloat();
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
