package src;

public class MineSweeperProgram {
    public static void main(String[] args) {

        Game game = new Game();

        boolean x = game.getCommands();

        game.startGame();

        game.initializeBoard();

        game.displayBoard();

        do {

            game.Commands();

        } while(x);
    }
}
