package main.java;

public class Research extends Game {

    @Override
    public void init() {
        super.init();
        getConfig("src/main/resources/config.properties");
        System.out.println(properties);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }
}
