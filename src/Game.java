package src;

import java.util.Random;
import java.util.Scanner;

public class Game {

    long startTime;
    long endTime;
    long elapsedTime;

    void startGame() {

        System.out.println("===== MineSwepper Game =====\n");

        int escolha = 0;
        int segundaEscolha;

        Scanner scanner = new Scanner(System.in);
        Scanner scannerDois = new Scanner(System.in);
        Random random = new Random();

        System.out.println("1 - New Game\n" + "2 - Last 10 Wins\n" + "3 - Exit\n" + "Option>");
        escolha = scanner.nextInt();
        System.out.println();

        if(escolha == 1) {

            startTime = System.currentTimeMillis();

            do {

                System.out.print("1 - Register\n");
                System.out.print("2 - Play as a guest\n");
                segundaEscolha = scanner.nextInt();
                System.out.println();

                if(segundaEscolha == 1) {

                    System.out.print("Nickname: \n");
                    String nickname = scannerDois.nextLine();
                    System.out.println("Your nickname: " + nickname + "\n");

                } else if(segundaEscolha == 2){

                    int randomNickname;

                    do {

                        randomNickname = random.nextInt();

                    } while(randomNickname < 0);

                    System.out.print("Your nickname: Anonymous" + randomNickname + "\n\n");

                } else if(segundaEscolha > 2) {

                    System.out.println("You need to chose 1 or 2.\n");

                }

            } while(segundaEscolha > 2);

        } else if(escolha == 2) {


        } else if(escolha == 3) {

        }

    }

    char[][] tabuleiro = new char[9][9];
    String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    boolean minesPlacedFlag;

    public void initializeBoard() {

        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j++) {

                tabuleiro[i][j] = '#';

            }

        }

        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < 10) {

            int x = random.nextInt(9);
            int y = random.nextInt(9);

            if (tabuleiro[x][y] != 'X') {
                tabuleiro[x][y] = 'X';
                minesPlaced++;

            }

        }

        minesPlacedFlag = true;

    }

    int bandeirasDispniveis = 10;

    public void displayBoard() {

        System.out.print("    0    1    2    3    4    5    6    7    8\n");

        for (int i = 0; i < 9; i++) {

            System.out.print(letras[i] + "   ");

            for (int j = 0; j < 9; j++) {

                char displayChar = (tabuleiro[i][j] == 'X') ? '#' : tabuleiro[i][j];

                System.out.print(displayChar + "    ");

            }

            System.out.println();

        }

        System.out.println();

        if(bandeirasDispniveis > 0) {

            System.out.println("Available Flags: " + bandeirasDispniveis);

        } else {

            System.out.println("Available Flags: 0");

        }

        System.out.println("/help command to see all available commands.");

        showElapsedTime();

        System.out.println();

    }

    public void displayBoardWithMines() {

        System.out.print("    0    1    2    3    4    5    6    7    8\n");

        for (int i = 0; i < 9; i++) {

            System.out.print(letras[i] + "   ");

            for (int j = 0; j < 9; j++) {

                System.out.print(tabuleiro[i][j] + "    ");

            }

            System.out.println();

        }

        System.out.println();

        if(bandeirasDispniveis > 0) {

            System.out.println("Available Flags: " + bandeirasDispniveis);

        } else {

            System.out.println("Available Flags: 0");

        }

        System.out.println("/help command to see all available commands.");

        showElapsedTime();

        System.out.println();

    }

    public void calculateProximityValues() { // falta implementar essa método no jogo, ou seja, quando o jogador abrir as celulas, os numeros serem exibidos em vez do *.

        int[] dRow = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dCol = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {

                if (tabuleiro[row][col] == 'X') {

                    for (int i = 0; i < 8; i++) {

                        int newRow = row + dRow[i];
                        int newCol = col + dCol[i];

                        if (newRow >= 0 && newRow < 9 && newCol >= 0 && newCol < 9 && tabuleiro[newRow][newCol] != 'X') {

                            if (tabuleiro[newRow][newCol] == '#') {
                                tabuleiro[newRow][newCol] = '1';

                            } else {

                                tabuleiro[newRow][newCol]++;
                            }

                        }

                    }

                }

            }

        }

    }

    private char calculateDisplayValue(int row, int col) {

        char value = tabuleiro[row][col];

        return (value == '#') ? ' ' : value;

    }

    Scanner openScanner = new Scanner(System.in);

    public void Open() {

        System.out.print("<row><column>: ");
        String open = openScanner.nextLine().toUpperCase();
        System.out.println();

        if (open.length() < 2 || open.length() > 3) {

            System.out.println("Invalid input. Please use format <row><column> (e.g., A0).\n");
            return;

        }

        try {

            int row = open.charAt(0) - 'A';
            int col = Integer.parseInt(open.substring(1));

            if (row < 0 || row >= 9 || col < 0 || col >= 9) {

                System.out.println("Invalid coordinates. Out of bounds.");
                return;

            }

            if (tabuleiro[row][col] == 'X') {

                minesPlacedFlag = false;

                if(minesPlacedFlag == false) {

                    endTime = System.currentTimeMillis();
                    elapsedTime = endTime - startTime;
                    System.out.println("You hit a mine! Game over. Time elapsed: " + formatElapsedTime(elapsedTime) + "\n");

                }
                startGame();
                displayBoard();

            } else if (tabuleiro[row][col] == '#') {

                tabuleiro[row][col] = '*';
                System.out.println("Cell opened!\n");
                displayBoard();

            } else if(tabuleiro[row][col] == 'F') {

                System.out.println("This cell contains a flag.");

            } else {

                System.out.println("Cell already opened.");

            }

        } catch (Exception e) {

            System.out.println("Invalid input. Please use format <row><column> (e.g., A0).");
            return;

        }

    }

    public void Hint() {

        Random random = new Random();
        boolean hintFound = false;

        for (int attempt = 0; attempt < 100; attempt++) {

            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (tabuleiro[row][col] == '#') {

                System.out.println("Hint: Try opening cell " + letras[row] + col);
                hintFound = true;
                break;

            }

        }

        if (!hintFound) {

            System.out.println("No safe hints available.");

        }

    }

    int contabler = 1;

    public int Flag() {

        System.out.print("<row><column>: ");
        String input = openScanner.nextLine().toUpperCase();
        System.out.println();

        if (input.length() < 2 || input.length() > 3) {

            System.out.println("Invalid input. Please use format <row><column> (e.g., A0).\n");
            return contabler;

        }

        try {

            int row = input.charAt(0) - 'A';
            int col = Integer.parseInt(input.substring(1));

            if (row < 0 || row >= 9 || col < 0 || col >= 9) {

                System.out.println("Invalid coordinates. Out of bounds.\n");
                return contabler;

            }

            if (tabuleiro[row][col] == '#' || tabuleiro[row][col] == 'X') {

                bandeirasDispniveis = bandeirasDispniveis - 1;
                tabuleiro[row][col] = 'F';
                System.out.println("Flag placed at " + input + ".\n");
                displayBoard();
                contabler = contabler + 1;

            } else if (tabuleiro[row][col] == 'F') {

                bandeirasDispniveis = bandeirasDispniveis + 1;
                tabuleiro[row][col] = '#';
                System.out.println("Flag removed from " + input + ".\n");
                displayBoard();
                contabler = contabler - 1;

            } else if(tabuleiro[row][col] == '*') {

                System.out.println("Cell already opened. Cannot place or remove a flag here.\n");

            }

        } catch (Exception e) {

            System.out.println("Invalid input. Please use format <row><column> (e.g., A0).\n");
            return contabler;

        }

        return contabler;

    }

    boolean x = true;

    public void Commands() {

        System.out.print("Command: ");
        Scanner commandScanner = new Scanner(System.in);
        Scanner quitScanner = new Scanner(System.in);
        String command = commandScanner.nextLine();
        System.out.println();

        if(command.equals("/help") || command.equals("/open") || command.equals("/flag") || command.equals("/hint") || command.equals("/cheat") || command.equals("/quit")) {

            if(command.equals("/help")) {

                System.out.println("Commands:");
                System.out.println("/open <row><column>: Open the cell at the row/column board coordinates.");
                System.out.println("/flag <row><column>: Mark the cell in the row/column board coordinates with a flag. If a flag already exists in that cell, remove it.");
                System.out.println("/hint: Suggests to the player, at random, a cell that does not contain mines. The indication is made in the form of board coordinates.");
                System.out.println("/cheat: Switches the game to “cheat” mode, where the mines are revealed each time the board is shown.");
                System.out.println("/quit: End the game and return to the main menu. A game finished like this does not enter the list of victories.\n");

            } else if(command.equals("/open")) {

                Open();

            } else if(command.equals("/flag")) {

                if(contabler <= 10) {

                    Flag();

                } else if(contabler > 10) {

                    System.out.println("There are no more flags available.\n");

                }

            } else if(command.equals("/hint")) {

                Hint();

            } else if(command.equals("/cheat")) {

                displayBoardWithMines();

            } else if(command.equals("/quit")) {

                System.out.println("Quit Game?");
                System.out.println("Yes or No?");
                String quit = quitScanner.nextLine();

                if(quit.equals("Yes")) {

                    x = false;
                    minesPlacedFlag = false;
                    System.out.println();
                    startGame();
                    displayBoard();

                } else if(quit.equals("No")){

                }

            }

        } else {

            System.out.println("Invalid Command.\n");

        }

    }

    public boolean getCommands() {

        return x;

    }

    private String formatElapsedTime(long elapsedTime) {
        long seconds = elapsedTime / 1000; // Converte para segundos
        long minutes = seconds / 60; // Calcula os minutos
        seconds %= 60; // Resto são os segundos
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void showElapsedTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        System.out.println("Current game time: " + formatElapsedTime(elapsedTime));
    }

}
