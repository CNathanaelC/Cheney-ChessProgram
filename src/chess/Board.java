package chess;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessPiece.PieceType.*;

public class Board implements ChessBoard {
    public Piece[][] board = new Piece[8][8];
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()-1][position.getColumn()-1] = new Piece();
        board[position.getRow()-1][position.getColumn()-1] = (Piece) piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1];
    }
    @Override
    public void resetBoard() {
        for(int r = 0; r < 8; r++) {
            for(int c = 0; c < 8; c++) {
                if(r == 7) {
                    if(c == 0 || c == 7) { //ROOK
                        Position coord = new Position();
                        coord.setRow(r+1);
                        coord.setColumn(c+1);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.ROOK);
                        piece.setTeamColor(BLACK);
                        addPiece(coord, piece);
                    } else if(c == 1 || c == 6) { //KNIGHT
                        Position coord = new Position();
                        coord.setRow(r+1);
                        coord.setColumn(c+1);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.KNIGHT);
                        piece.setTeamColor(BLACK);
                        addPiece(coord, piece);
                    } else if(c == 2 || c == 5) { //BISHOP
                        Position coord = new Position();
                        coord.setRow(r+1);
                        coord.setColumn(c+1);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.BISHOP);
                        piece.setTeamColor(BLACK);
                        addPiece(coord, piece);
                    } else if(c == 3) { //QUEEN
                        Position coord = new Position();
                        coord.setRow(r+1);
                        coord.setColumn(c+1);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.QUEEN);
                        piece.setTeamColor(BLACK);
                        addPiece(coord, piece);
                    } else { //c == 4 KING
                        Position coord = new Position();
                        coord.setRow(r+1);
                        coord.setColumn(c+1);
                        Piece piece = new Piece();
                        piece.setPieceType(KING);
                        piece.setTeamColor(BLACK);
                        addPiece(coord, piece);
                    }
                }
                else if(r == 6) {
                    Position coord = new Position();
                    coord.setRow(r+1);
                    coord.setColumn(c+1);
                    Piece piece = new Piece();
                    piece.setPieceType(ChessPiece.PieceType.PAWN);
                    piece.setTeamColor(BLACK);
                    addPiece(coord, piece);
                }
                //FIXME::c != 4 is just for testing purposes to make sure the king can move
                else if(r == 1) {
                    Position coord = new Position();
                    coord.setRow(r+1);
                    coord.setColumn(c+1);
                    Piece piece = new Piece();
                    piece.setPieceType(ChessPiece.PieceType.PAWN);
                    piece.setTeamColor(ChessGame.TeamColor.WHITE);
                    addPiece(coord, piece);
                }
                else if(r == 0) {
                    if(c == 0 || c == 7) { //ROOK
                        Position coord = new Position();
                        coord.setRow(r+1);
                        coord.setColumn(c+1);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.ROOK);
                        piece.setTeamColor(ChessGame.TeamColor.WHITE);
                        addPiece(coord, piece);
                    } else if(c == 1 || c == 6) { //KNIGHT
                        Position coord = new Position();
                        coord.setRow(r+1);
                        coord.setColumn(c+1);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.KNIGHT);
                        piece.setTeamColor(ChessGame.TeamColor.WHITE);
                        addPiece(coord, piece);
                    } else if(c == 2 || c == 5) { //BISHOP
                        Position coord = new Position();
                        coord.setRow(r+1);
                        coord.setColumn(c+1);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.BISHOP);
                        piece.setTeamColor(ChessGame.TeamColor.WHITE);
                        addPiece(coord, piece);
                    } else if(c == 3) { //QUEEN
                        Position coord = new Position();
                        coord.setRow(r+1);
                        coord.setColumn(c+1);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.QUEEN);
                        piece.setTeamColor(ChessGame.TeamColor.WHITE);
                        addPiece(coord, piece);
                    } else { //c == 4 KING
                        Position coord = new Position();
                        coord.setRow(r+1);
                        coord.setColumn(c+1);
                        Piece piece = new Piece();
                        piece.setPieceType(KING);
                        piece.setTeamColor(ChessGame.TeamColor.WHITE);
                        addPiece(coord, piece);
                    }
                }
                else {
                    Position coord = new Position();
                    coord.setRow(r+1);
                    coord.setColumn(c+1);
                    Piece piece = new Piece();
                    piece.setPieceType(null);
                    piece.setTeamColor(null);
                    addPiece(coord, piece);
                }
            }
        }
    }

    public String toString() {
        StringBuilder picture = new StringBuilder();
        for(int r = 7; r > -1; r--) {
            for(int c = 0; c < 8; c++) {
                picture.append('|');

                Piece piece = new Piece();
                piece = board[r][c];
                if(piece != null) {
                    if(piece.getPieceType() == KING) {
                        if(piece.getTeamColor() != BLACK) {
                            picture.append('K');
                        } else {
                            picture.append('k');
                        }
                    } else if(piece.getPieceType() == QUEEN) {
                        if(piece.getTeamColor() != BLACK) {
                            picture.append('Q');
                        } else {
                            picture.append('q');
                        }
                    } else if(piece.getPieceType() == BISHOP) {
                        if(piece.getTeamColor() != BLACK) {
                            picture.append('B');
                        } else {
                            picture.append('b');
                        }
                    } else if(piece.getPieceType() == KNIGHT) {
                        if(piece.getTeamColor() != BLACK) {
                            picture.append('N');
                        } else {
                            picture.append('n');
                        }
                    } else if(piece.getPieceType() == ROOK) {
                        if(piece.getTeamColor() != BLACK) {
                            picture.append('R');
                        } else {
                            picture.append('r');
                        }
                    } else if (piece.getPieceType() == PAWN) {
                        if(piece.getTeamColor() != BLACK) {
                            picture.append('P');
                        } else {
                            picture.append('p');
                        }
                    } else {
                        picture.append(" ");
                    }
                    if (c == 7) {
                        picture.append("|\n");
                    }
                } else {
                    picture.append(" ");
                }
            }
        }

        return picture.toString();
    }
}
