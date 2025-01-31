import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MinesweeperGame {

    private Scanner stdIn;
    private int numMines;
    private int rows;
    private int cols;
    private String[][] gameGrid;
    private String[][] mineGrid;
    private int round;
    private double score;

    /**
     * Constructor method for MinesweeperGame.
     *
     *
     *
     *
     *
     */
    MinesweeperGame(Scanner stdIn, String seedPath) {
        readSeed(seedPath);
    } // Constructor

    /**
     * Prints welcome banner.
     */
    public void printWelcome() {
//        System.out.println("        _");
//        System.out.println("  /\/\ (F)_ __   ___  _____      _____  ___ _ __   ___ _ __");
//        System.out.println(" /    \| | '_ \ / _ \/ __\ \ /\ / / _ \/ _ \ '_ \ / _ \ '__|");
//        System.out.println("/ /\/\ \ | | | |  __/\__ \\ V  V /  __/  __/ |_) |  __/ |");
//        System.out.println("\/    \/_|_| |_|\___||___/ \_/\_/ \___|\___| .__/ \___|_|");
//        System.out.println("                             ALPHA EDITION |_| v2025.sp");
    } // printWelcome

    /**
     * Prints current mine field.
     */
    public void printMineField() {
        System.out.println();
        System.out.println("Rounds Completed: " + round);
        System.out.println();
        for (int i = 0; i < rows; i++) {
            System.out.print(" " + i + " |");
            for (int j = 0; j < cols; j++) {
                System.out.print(gameGrid[i][i] + "|");
            }
            System.out.println();
        }
        System.out.print("     ");
        for (int i = 0; i < cols; i++) {
            System.out.print(i + "   ");
        }
        System.out.println();
    } // printMineField

    /**
     * Prompts user for command.
     */
    public void promptUser() throws NumberFormatException, IllegalArgumentException {
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

                    } else {
                        printLoss();
                    }
                } else {

                }
                break;
        }
    } // promptUser

    public boolean hasTwoIntTokens(String command) throws NumberFormatException, IllegalArgumentException {
        String[] tokens = command.trim().split("\\s+");

        if (tokens.length == 3) {
            Integer.parseInt(tokens[1]);
            Integer.parseInt(tokens[2]);
            return true;
        } else if (tokens.length > 3) {
            throw new IllegalArgumentException();
        } else {
            throw new NumberFormatException();
        }
    } // hasTwoIntTokens

    public int getNumAdjacentMines(int selectedRow, int selectedCol) {

    } // getNumAdjacentMines

    public boolean isMineInBounds

    /**
     * Evaluates whether the game has been won or not.
     *
     * @returns {@code} true if and only if all conditions are met to win; returns {@code} false otherwise.
     */
    public boolean isWon() {
        // TODO
    } // isWon

    /**
     * Prints loss banner.
     */
    public void printLoss() {
//        System.out.println(" Oh no... You revealed a mine!");
//        System.out.println("  __ _  __ _ _ __ ___   ___    _____   _____ _ __");
//        System.out.println(" / _` |/ _` | '_ ` _ \ / _ \  / _ \ \ / / _ \ '__|");
//        System.out.println("| (_| | (_| | | | | | |  __/ | (_) \ V /  __/ |");
//        System.out.println(" \__, |\__,_|_| |_| |_|\___|  \___/ \_/ \___|_|");
//        System.out.println(" |___/");
        System.exit(0);
    } // printLoss

    /**
     * Prints win banner with player's score.
     */
    public void printWin() {
//        System.out.println(" ░░░░░░░░░▄░░░░░░░░░░░░░░▄░░░░ "So Doge"");
//        System.out.println(" ░░░░░░░░▌▒█░░░░░░░░░░░▄▀▒▌░░░");
//        System.out.println(" ░░░░░░░░▌▒▒█░░░░░░░░▄▀▒▒▒▐░░░ "Such Score"");
//        System.out.println(" ░░░░░░░▐▄▀▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐░░░");
//        System.out.println(" ░░░░░▄▄▀▒░▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐░░░ "Much Minesweeping"");
//        System.out.println(" ░░░▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌░░░");
//        System.out.println(" ░░▐▒▒▒▄▄▒▒▒▒░░░▒▒▒▒▒▒▒▀▄▒▒▌░░ "Wow"");
//        System.out.println(" ░░▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐░░");
//        System.out.println(" ░▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌░");
//        System.out.println(" ░▌░▒▄██▄▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌░");
//        System.out.println(" ▀▒▀▐▄█▄█▌▄░▀▒▒░░░░░░░░░░▒▒▒▐░");
//        System.out.println(" ▐▒▒▐▀▐▀▒░▄▄▒▄▒▒▒▒▒▒░▒░▒░▒▒▒▒▌");
//        System.out.println(" ▐▒▒▒▀▀▄▄▒▒▒▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▐░");
//        System.out.println(" ░▌▒▒▒▒▒▒▀▀▀▒▒▒▒▒▒░▒░▒░▒░▒▒▒▌░");
//        System.out.println(" ░▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▄▒▒▐░░");
//        System.out.println(" ░░▀▄▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▄▒▒▒▒▌░░");
//        System.out.println(" ░░░░▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀░░░ CONGRATULATIONS!");
//        System.out.println(" ░░░░░░▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀░░░░░ YOU HAVE WON!");
//        System.out.println(" ░░░░░░░░░▒▒▒▒▒▒▒▒▒▒▀▀░░░░░░░░ SCORE:");
    } // printWin

    public void play() {
        while (!isWon()) {
            printMineField();
            try {
                promptUser();
            } catch (NumberFormatException nfe) {
                System.err.println();
                System.err.println("Invalid Command: null");
            } catch (IllegalArgumentException iae) {
                System.err.println();
                System.err.println("Invalid Command: Command not recognized!");
            }
        }
    } // play

    public boolean containsMine(int selectedRow, int selectedCol) {
        if (mineGrid[selectedRow][selectedCol].equals("<X>")) {
            return true;
        } else {
            return false;
        }
    } // containsMine
    public boolean isInBounds(int selectedRow, int selectedCol) {
        if (selectedRow < rows && selectedRow >= 0 && selectedCol < cols && selectedCol >= 0) {
            return true;
        }
        else {
            int invalidIndex, invalidDimension;
            if (selectedRow < 0 || selectedRow >= rows) {
                invalidIndex = selectedRow;
                invalidDimension = rows;
            } else {
                invalidIndex = selectedCol;
                invalidDimension = cols;
            }
            System.err.println();
            System.err.println("Invalid Command: Index " + invalidIndex + " out of bounds for length " + invalidDimension);
            return false;
        }
    } // isInBounds

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
            setNumMines(config[2]);
            if (numMines < 1 || numMines > (rows * cols - 1)); {
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
            for (int i = 3; i < config.length; i++) {
                if (i % 2 != 0) {
                    if (config[i] > rows || config[i] < 0 || config[i + 1] > cols || config[i + 1] < 0) {
                        System.err.println();
                        System.err.println("Seed File Malformed Error: Mine location out of bounds.");
                        System.exit(3);
                    } else {
                        mineGrid[config[i]][config[i + 1]] = "<X>";
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

    /**
     * Returns instance variable {@code rows}
     */
    public int getRows() {
        return this.rows;
    } // getRows

    public void setRows(int newRows) {
        this.rows = newRows;
    } // setRows

    public int getCols() {
        return this.cols;
    } // getCols

    public void setCols(int newCols) {
        this.rows = newCols;
    } // setCols

    public int getNumMines() {
        return this.numMines;
    } // getNumMines

    public void setNumMines(int newNumMines) {
        this.numMines = newNumMines;
    } // setNumMines

    public int getRound() {
        return this.round;
    } // getRounds

    public void setRound(int newRound) {
        this.round = newRound;
    } // setRound

} // MinesweeperGame
