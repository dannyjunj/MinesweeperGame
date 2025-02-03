import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The MinesweeperAlpha class represents a game of Minesweeper with interactive gameplay.
 * It allows the user to play through a grid of cells, where they can reveal, mark, or guess cells.
 * The game generates a grid based on a seed file and tracks progress through rounds. Mines are placed
 * randomly, and the user must avoid stepping on them while uncovering safe cells. The game ends when either all safe
 * cells are uncovered (win) or a mine is revealed (loss).
 *
 * <p>The class includes mechanisms for handling invalid user commands, bounds checking, and error reporting.
 * It also contains methods for managing game state, printing the grid, and tracking the user's progress through rounds.</p>
 *
 * <p>This class depends on a seed file to generate a custom grid. The file should contain integer values specifying
 * the number of rows, columns, the number of mines, and mine locations. If the file is improperly formatted or the
 * values are out of bounds, the program will terminate with an error message.</p>
 */
public class MinesweeperGame {

    private Scanner stdIn;
    private int numMines;
    private int rows;
    private int cols;
    private String[][] gameGrid;
    private String[][] mineGrid;
    private int round;
    private double score;
    private boolean showNoFog;

    /**
     * This is the constructor method for MinesweeperGame. Creates an instance of MinesweeperGame using {@code stdIn}
     * as the input scanner and passes through {@code seedPath} to the readSeed method for variable initializations.
     *
     * @param stdIn - The scanner used for standard input.
     * @param seedPath - The path to the seed used for instance variable initializations.
     */
    MinesweeperGame(Scanner stdIn, String seedPath) {
        this.stdIn = stdIn;
        readSeed(seedPath);
    } // MinesweeperGame

    /**
     * Prints welcome banner.
     */
    public void printWelcome() {
        System.out.println("        _");
        System.out.println("  /\\/\\ (F)_ __   ___  _____      _____  ___ _ __   ___ _ __");
        System.out.println(" /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|");
        System.out.println("/ /\\/\\ \\ | | | |  __/\\__ \\ V  V /  __/  __/ |_) |  __/ |");
        System.out.println("\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|");
        System.out.println("                             ALPHA EDITION |_| v2025.sp");
    } // printWelcome

    /**
     * Prints current minefield if the no-fog command is not used prior.
     */
    public void printMineField() {
        if (!showNoFog) {
            System.out.println();
            System.out.println("Rounds Completed: " + round);
            System.out.println();
            for (int i = 0; i < rows; i++) {
                System.out.print(" " + i + " |");
                for (int j = 0; j < cols; j++) {
                    System.out.print(gameGrid[i][j] + "|");
                }
                System.out.println();
            }
            System.out.print("     ");
            for (int i = 0; i < cols; i++) {
                System.out.print(i + "   ");
            }
            System.out.println();
        }
        System.out.println();
    } // printMineField

    /**
     * Prompts user for a command input in the Minesweeper game and executes the corresponding action.
     *
     * <p>This method reads a command from standard input, processes it, and modifies the game state accordingly.
     * It supports the following commands:
     * <ul>
     *     <li>Reveal: r/reveal row col - Reveals the cell at the given row and column.</li>
     *     <li>Mark: m/mark row col - Marks the cell at the given row and column with a flag.</li>
     *     <li>Guess: g/guess row col - Places a question mark on the specified cell.</li>
     *     <li>Help: h/help - Displays available commands.</li>
     *     <li>Quit: q/quit - Exits the game.</li>
     *     <li>No Fog: nofog - Temporarily reveals all mines.</li>
     * </ul>
     *
     * @throws IllegalArgumentException if user does not provide a valid command.
     * @throws ArrayIndexOutOfBoundsException if user's command is not within bounds of the game grid.
     */
    public void promptUser() throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        System.out.print("minesweeper-alpha: ");
        String fullCommand = stdIn.nextLine();
        Scanner commandScan = new Scanner(fullCommand);
        String userCommand = commandScan.next().toLowerCase();
        // Checks if the user commands that use int tokens have exactly two int tokens, throws exception if not.
        if (userCommand.equals("r") || userCommand.equals("reveal") || userCommand.equals("m") || userCommand.equals("mark") || userCommand.equals("g") || userCommand.equals("guess")) {
            hasTwoIntTokens(fullCommand);
        }
        switch (userCommand) {
            case "r":
            case "reveal":
                int revealRow = commandScan.nextInt();
                int revealCol = commandScan.nextInt();
                if (isInBounds(revealRow, revealCol)) {
                    if (!containsMine(revealRow, revealCol)) {
                        gameGrid[revealRow][revealCol] = " " + getNumAdjacentMines(revealRow, revealCol)+ " ";
                        round++;
                    } else {
                        printLoss();
                    }
                }
                break;

            case "m":
            case "mark":
                int markRow = commandScan.nextInt();
                int markCol = commandScan.nextInt();
                if (isInBounds(markRow, markCol)) {
                    gameGrid[markRow][markCol] = " F ";
                    round++;
                }
                break;

            case "g":
            case "guess":
                int guessRow = commandScan.nextInt();
                int guessCol = commandScan.nextInt();
                if (isInBounds(guessRow, guessCol)) {
                    gameGrid[guessRow][guessCol] = " ? ";
                    round++;
                }
                break;

            case "h":
            case "help":
                System.out.println();
                System.out.println("Commands Available...");
                System.out.println(" - Reveal: r/reveal row col");
                System.out.println(" -   Mark: m/mark   row col");
                System.out.println(" -  Guess: g/guess  row col");
                System.out.println(" -   Help: h/help");
                System.out.println(" -   Quit: q/quit");
                round++;
                break;

            case "q":
            case "quit":
                System.out.println();
                System.out.println("Quitting the game...");
                System.out.println("Bye!");
                System.exit(0);
                break;

            case "nofog":
                round++;
                showNoFog = true;
                printNoFogField();
                break;

            default:
                throw new IllegalArgumentException();
        }
    } // promptUser

    /**
     * Prints the minefield with all mines temporarily revealed.
     */
    public void printNoFogField() {
        System.out.println();
        System.out.println("Rounds Completed: " + round);
        System.out.println();
        for (int i = 0; i < rows; i++) {
            System.out.print(" " + i + " |");
            for (int j = 0; j < cols; j++) {
                if (gameGrid[i][j].equals(" ? ")) {
                    if (mineGrid[i][j].equals("<X>")) {
                        System.out.print("<?>|");
                    } else {
                        System.out.print(gameGrid[i][j] + "|");
                    }
                } else if (gameGrid[i][j].equals(" F ")) {
                    if (mineGrid[i][j].equals("<X>")) {
                        System.out.print("<F>|");
                    } else {
                        System.out.print(gameGrid[i][j] + "|");
                    }
                } else if (gameGrid[i][j].equals("   ")) {
                    if (mineGrid[i][j].equals("<X>")) {
                        System.out.print("< >|");
                    } else {
                        System.out.print(gameGrid[i][j] + "|");
                    }
                } else {
                    System.out.print(gameGrid[i][j] + "|");
                }
            }
            System.out.println();
        }
        System.out.print("     ");
        for (int i = 0; i < cols; i++) {
            System.out.print(i + "   ");
        }
        System.out.println();
    } // printNoFogField

    /**
     * Takes in the user's full command and parses it into a String array, which is then used to determine
     * whether the full command is valid and contains two int tokens. Returns {@code true} if the full command is valid
     * and contains the appropriate amount of tokens.
     *
     * @param command The user's full command.
     * @return {@code true} if user's full command is valid and contains two int tokens.
     * @throws IllegalArgumentException if user's full command is not valid or does not contain two int tokens.
     */
    public boolean hasTwoIntTokens(String command) throws IllegalArgumentException {
        String[] tokens = command.trim().split("\\s+");

        if (tokens.length == 3) {
            Integer.parseInt(tokens[1]);
            Integer.parseInt(tokens[2]);
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    } // hasTwoIntTokens

    /**
     * Returns how many mines are adjacent to the user's selected cell.
     *
     * @param selectedRow The row index of the cell to check.
     * @param selectedCol The column index of the cell to check.
     * @return The number of mines adjacent to the user's selected cell.
     */
    public int getNumAdjacentMines(int selectedRow, int selectedCol) {
        int adjMine = 0;
        if (isMineInBounds(selectedRow - 1, selectedCol - 1)) {
            if (containsMine(selectedRow - 1, selectedCol - 1)) {
                adjMine++;
            }
        }
        if (isMineInBounds(selectedRow - 1, selectedCol)) {
            if (containsMine(selectedRow - 1, selectedCol)) {
                adjMine++;
            }
        }
        if (isMineInBounds(selectedRow - 1, selectedCol + 1)) {
            if (containsMine(selectedRow - 1, selectedCol + 1)) {
                adjMine++;
            }
        }
        if (isMineInBounds(selectedRow, selectedCol - 1)) {
            if (containsMine(selectedRow, selectedCol - 1)) {
                adjMine++;
            }
        }
        if (isMineInBounds(selectedRow, selectedCol + 1)) {
            if (containsMine(selectedRow, selectedCol + 1)) {
                adjMine++;
            }
        }
        if (isMineInBounds(selectedRow + 1, selectedCol - 1)) {
            if (containsMine(selectedRow + 1, selectedCol - 1)) {
                adjMine++;
            }
        }
        if (isMineInBounds(selectedRow + 1, selectedCol)) {
            if (containsMine(selectedRow + 1, selectedCol)) {
                adjMine++;
            }
        }
        if (isMineInBounds(selectedRow + 1, selectedCol + 1)) {
            if (containsMine(selectedRow + 1, selectedCol + 1)) {
                adjMine++;
            }
        }
        return adjMine;
    } // getNumAdjacentMines

    /**
     * Determines whether a cell's indices are within the bounds of the game grid. Used exclusively by the
     * {@link #getNumAdjacentMines(int, int)} method.
     *
     * @param mineRow The row index of the cell to check.
     * @param mineCol The column index of the cell to check.
     * @return {@code true} if the specified coordinates are within the bounds of the grid,
     *         {@code false} otherwise.
     */
    public boolean isMineInBounds(int mineRow, int mineCol) {
        if (mineRow < rows && mineRow >= 0 && mineCol < cols && mineCol >= 0) {
            return true;
        } else {
            return false;
        }
    } // isMineInBounds

    /**
     * Evaluates whether the game has been won or not based off of two conditions:
     * <ul>
     *     <li>All cells containing mines are definitely marked (F).</li>
     *     <li>All cells not containing mines are revealed.</li>
     * </ul>
     *
     * @returns {@code} true if and only if all conditions are met to win,
     *          {@code} false otherwise.
     */
    public boolean isWon() {
        boolean winning = true;
        for (int i = 0; i < rows; i ++) {
            for (int j = 0; j < cols; j++) {
                if (mineGrid[i][j].equals("<X>")) {
                    if (!gameGrid[i][j].equals(" F ")) {
                        winning = false;
                    }
                }
                if (mineGrid[i][j].equals(" O ")) {
                    if (gameGrid[i][j].equals(" F ") || gameGrid[i][j].equals(" ? ") || gameGrid[i][j].equals("   ")) {
                        winning = false;
                    }
                }
            }
        }
        return winning;
    } // isWon

    /**
     * Displays a message indicating the player has lost the game by revealing a mine and then terminates the
     * program using {@code System.exit(0)}.
     */
    public void printLoss() {
        System.out.println();
        System.out.println(" Oh no... You revealed a mine!");
        System.out.println("  __ _  __ _ _ __ ___   ___    _____   _____ _ __");
        System.out.println(" / _` |/ _` | '_ ` _ \\ / _ \\  / _ \\ \\ / / _ \\ '__|");
        System.out.println("| (_| | (_| | | | | | |  __/ | (_) \\ V /  __/ |");
        System.out.println(" \\__, |\\__,_|_| |_| |_|\\___|  \\___/ \\_/ \\___|_|");
        System.out.println(" |___/");
        System.exit(0);
    } // printLoss

    /**
     * Displays a winning message when the player successfully wins the game. The player's score is also displayed which
     * is calculated based on the number of rounds taken to complete the game. The program then terminates using
     * {@code System.exit(0)}.
     */
    public void printWin() {
        System.out.println(" ░░░░░░░░░▄░░░░░░░░░░░░░░▄░░░░ \"So Doge\"");
        System.out.println(" ░░░░░░░░▌▒█░░░░░░░░░░░▄▀▒▌░░░");
        System.out.println(" ░░░░░░░░▌▒▒█░░░░░░░░▄▀▒▒▒▐░░░ \"Such Score\"");
        System.out.println(" ░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐░░░");
        System.out.println(" ░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐░░░ \"Much Minesweeping\"");
        System.out.println(" ░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌░░░");
        System.out.println(" ░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒▌░░ \"Wow\"");
        System.out.println(" ░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐░░");
        System.out.println(" ░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌░");
        System.out.println(" ░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌░");
        System.out.println(" ▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒▐░");
        System.out.println(" ▐▒▒▐▀▐▀▒░▄▄▒▄▒▒▒▒▒▒░▒░▒░▒▒▒▒▌");
        System.out.println(" ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▐░");
        System.out.println(" ░▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒░▒░▒░▒░▒▒▒▌░");
        System.out.println(" ░▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▄▒▒▐░░");
        System.out.println(" ░░▀▄▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▄▒▒▒▒▌░░");
        System.out.println(" ░░░░▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀░░░ CONGRATULATIONS!");
        System.out.println(" ░░░░░░▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀░░░░░ YOU HAVE WON!");
        System.out.println(" ░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▀▀░░░░░░░░ SCORE: ");
        score = 100.0 * rows * cols / round;
        System.out.print(score);
        System.exit(0);
    } // printWin

    /**
     * Controls the main game loop for Minesweeper.
     *
     * <p>The game continues running until the player wins by correctly marking all mines.
     * In each iteration of the loop, the minefield is printed, the player's input is
     * requested via {@code promptUser()}, and errors are handled gracefully. If the
     * player provides an invalid command, appropriate error messages are displayed.</p>
     *
     * <p>If the player wins the game, {@code printWin()} is called to display the
     * victory message and score before terminating the program.</p>
     */
    public void play() {
        while (!isWon()) {
            printMineField();
            showNoFog = false;
            try {
                promptUser();
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                System.err.println("Invalid Command: " + aioobe.getMessage());
                System.err.println();
                continue;
            } catch (IllegalArgumentException iae) {
                System.err.println("Invalid Command: Command not recognized!");
                System.err.println();
                continue;
            }
            if (isWon()) {
                printWin();
            }
        }
    } // play

    /**
     * Checks if the specified cell contains a mine.
     *
     * @param selectedRow The row index of the cell to check.
     * @param selectedCol The column index of the cell to check.
     * @return {@code true} if the specified cell contains a mine,
     *         {@code false} otherwise.
     */
    public boolean containsMine(int selectedRow, int selectedCol) {
        if (mineGrid[selectedRow][selectedCol].equals("<X>")) {
            return true;
        } else {
            return false;
        }
    } // containsMine

    /**
     * Checks if the specified cell is within the bounds of the game grid.
     *
     * @param selectedRow The row index of the cell to check.
     * @param selectedCol The column index of the cell to check.
     * @return {@code true} if the cell is within the bounds of the game grid.
     * @throws ArrayIndexOutOfBoundsException if the specified row or column is out of bounds of the game grid.
     */
    public boolean isInBounds(int selectedRow, int selectedCol) throws ArrayIndexOutOfBoundsException {
        String testForInBounds = gameGrid[selectedRow][selectedCol];
        return true;
    } // isInBounds

    /**
     * Reads a seed configuration file to initialize the game grid and mine grid.
     *
     * @param seedPath The path to the seed file containing the configuration.
     * @throws IllegalArgumentException if a non-integer value is encountered in the seed file.
     * @throws FileNotFoundException if the seed file is not found.
     * @throws ArrayIndexOutOfBoundsException if there is an unexpected number of tokens in the seed file.
     */
    public void readSeed(String seedPath) {

        List<Integer> seedNumbers = new ArrayList<>();
        try {
            File configFile = new File(seedPath);
            Scanner configScanner = new Scanner(configFile);

            while (configScanner.hasNext()) {
                if (configScanner.hasNextInt()) {
                    seedNumbers.add(configScanner.nextInt());
                } else {
                    throw new IllegalArgumentException();
                }
            }
            int[] config = seedNumbers.stream().mapToInt(Integer::intValue).toArray();

            if (config.length == 0 || config == null) {
                throw new ArrayIndexOutOfBoundsException();
            }

            rows = config[0];
            cols = config[1];
            if (rows < 5 || rows > 10 || cols < 5 || cols > 10) {
                System.err.println();
                System.err.println("Seed File Malformed Error: Cannot create a mine field with that many rows and/or columns!");
                System.exit(3);
            }

            numMines = config[2];
            if (numMines < 1 || numMines > (rows * cols - 1)) {
                System.err.println();
                System.err.println("Seed File Malformed Error: Please input a valid number of mines.");
                System.exit(3);
            }

            mineGrid = new String[rows][cols];
            gameGrid = new String[rows][cols];

            for (int i = 0; i < rows; i++) { // Initializes both gameGrid and mineGrid
                for (int j = 0; j < cols; j++) {
                    mineGrid[i][j] = " O ";
                    gameGrid[i][j] = "   ";
                }
            }

            boolean enoughMines = false;
            int mineCount = 0;
            for (int i = 3; i < config.length && !enoughMines; i++) {
                if (i % 2 != 0) {
                    if (config[i] > rows || config[i] < 0 || config[i + 1] > cols || config[i + 1] < 0) {
                        System.err.println();
                        System.err.println("Seed File Malformed Error: Mine location out of bounds.");
                        System.exit(3);
                    } else {
                        mineGrid[config[i]][config[i + 1]] = "<X>";
                        mineCount++;
                    }
                    if (mineCount == numMines) {
                        enoughMines = true;
                    }
                }
            }

        } catch (FileNotFoundException fnfe) {
            System.err.println();
            System.err.println("Seed File Not Found Error: Please input a valid seed file.");
            System.exit(2);
        } catch (IllegalArgumentException iae) {
            System.err.println();
            System.err.println("Seed File Malformed Error: Integer value expected.");
            System.exit(3);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            System.err.println();
            System.err.println("Seed File Malformed Error: Token expected but not found.");
            System.exit(3);
        }
    } // readSeed

} // MinesweeperGame
