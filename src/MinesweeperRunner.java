import java.util.Scanner;
/**
 * The MinesweeperRunner class is the entry point for running the Minesweeper game.
 * It initializes the game by reading a seed file provided as a command line argument,
 * and then starts the game by invoking the appropriate methods from the MinesweeperGame class.
 */
public class MinesweeperRunner {

    /**
     * The main method that initializes the Minesweeper game and starts the gameplay.
     * It takes the path to the seed file as a command line argument and uses it to
     * initialize the game. Then, it prints a welcome message and starts the game loop.
     *
     * @param args Command line arguments, where the first argument is the path to the seed file.
     */
    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);

        String seedPath = args[0];
        MinesweeperGame myGame = new MinesweeperGame(stdIn, seedPath);

        myGame.printWelcome();
        myGame.play();
    } // main
} // MinesweeperRunner

