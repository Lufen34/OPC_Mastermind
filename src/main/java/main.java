package main.java;

public class main {

    public static void main(String[] args) {
        Program program = null;
        int count = args.length;

        if(count > 0)
        {
            if(args[0].toLowerCase().contains("devmode")) {
                program = new Program(true);
            }
        }
        else {
            program = new Program(false );
        }
        program.run();
    }
}
