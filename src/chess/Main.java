package chess;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
//        Position p = new Position();
//        p.setRow(3);
//        p.setColumn(5);
//        Piece pi = new Piece();
//        pi.setPieceType(ChessPiece.PieceType.QUEEN);
//        pi.setTeamColor(ChessGame.TeamColor.WHITE);
//        game.official_board.addPiece(p,pi);
        game.official_board.resetBoard();
        for(int i = 0; i < 10; i++) {
            System.out.println(game.official_board.toString());
            Move move = new Move();
            Position start = new Position();
            Position end = new Position();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the start coordinates for your move in the following format:\n" +
                    "\"*row number**space**column number*\" e.g. 1 5\n");
            int row = scanner.nextInt();
            start.setRow(row);
            int column = scanner.nextInt();
            start.setColumn(column);
            System.out.print("Enter the end coordinates for your move in the following format:\n" +
                    "\"*row number**space**column number*\" e.g. 1 5\n");
            row = scanner.nextInt();
            end.setRow(row);
            column = scanner.nextInt();
            end.setColumn(column);
            if(game.official_board.getPiece(start) != null) {
                move.setStartPosition(start);
                move.setEndPosition(end);
                try {
                    game.makeMove(move);
                } catch (InvalidMoveException e) {
                    System.out.print("Try a different move\n\n");
                }
            } else {
                System.out.print("Try a different move\n\n");
            }
        }

        System.out.println(game.official_board.toString());
    }
}