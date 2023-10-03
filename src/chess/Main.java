package chess;
public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.official_board.resetBoard();
        System.out.println(game.official_board.toString());

    }
}