import java.util.Scanner;

public class MinesweeperRunner {

    public static void main(String[] args) {

        Scanner stdIn = new Scanner(System.in);

        String seedPath = args[0];
        MinesweeperGame myGame = new MinesweeperGame(stdIn, seedPath);

        myGame.printWelcome();
        myGame.play();

    } // main

} // MinesweeperRunner
