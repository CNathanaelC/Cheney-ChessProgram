package chess;

public class Board implements ChessBoard {
    ChessPiece[][] board = new Piece[8][8];

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = new Piece();
        board[position.getRow()][position.getColumn()] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()][position.getColumn()];
    }

    @Override
    public void resetBoard() {

        for(int r = 0; r < 8; r++) {
            for(int c = 0; c < 8; c++) {
                if(r == 0) {
                    if(c == 0 || c == 7) { //ROOK
                        Position coord = new Position();
                        coord.setRow(r);
                        coord.setColumn(c);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.ROOK);
                        piece.setTeamColor(ChessGame.TeamColor.BLACK);
                        addPiece(coord, piece);
                    } else if(c == 1 || c == 6) { //KNIGHT
                        Position coord = new Position();
                        coord.setRow(r);
                        coord.setColumn(c);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.KNIGHT);
                        piece.setTeamColor(ChessGame.TeamColor.BLACK);
                        addPiece(coord, piece);
                    } else if(c == 2 || c == 5) { //BISHOP
                        Position coord = new Position();
                        coord.setRow(r);
                        coord.setColumn(c);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.BISHOP);
                        piece.setTeamColor(ChessGame.TeamColor.BLACK);
                        addPiece(coord, piece);
                    } else if(c == 3) { //QUEEN
                        Position coord = new Position();
                        coord.setRow(r);
                        coord.setColumn(c);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.QUEEN);
                        piece.setTeamColor(ChessGame.TeamColor.BLACK);
                        addPiece(coord, piece);
                    } else { //c == 4 KING
                        Position coord = new Position();
                        coord.setRow(r);
                        coord.setColumn(c);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.KING);
                        piece.setTeamColor(ChessGame.TeamColor.BLACK);
                        addPiece(coord, piece);
                    }
                }
                else if(r == 1) {
                    Position coord = new Position();
                    coord.setRow(r);
                    coord.setColumn(c);
                    Piece piece = new Piece();
                    piece.setPieceType(ChessPiece.PieceType.PAWN);
                    piece.setTeamColor(ChessGame.TeamColor.BLACK);
                    addPiece(coord, piece);
                }
                else if(r == 6) {
                    Position coord = new Position();
                    coord.setRow(r);
                    coord.setColumn(c);
                    Piece piece = new Piece();
                    piece.setPieceType(ChessPiece.PieceType.PAWN);
                    piece.setTeamColor(ChessGame.TeamColor.WHITE);
                    addPiece(coord, piece);
                }
                else if(r == 7) {
                    if(c == 0 || c == 7) { //ROOK
                        Position coord = new Position();
                        coord.setRow(r);
                        coord.setColumn(c);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.ROOK);
                        piece.setTeamColor(ChessGame.TeamColor.WHITE);
                        addPiece(coord, piece);
                    } else if(c == 1 || c == 6) { //KNIGHT
                        Position coord = new Position();
                        coord.setRow(r);
                        coord.setColumn(c);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.KNIGHT);
                        piece.setTeamColor(ChessGame.TeamColor.WHITE);
                        addPiece(coord, piece);
                    } else if(c == 2 || c == 5) { //BISHOP
                        Position coord = new Position();
                        coord.setRow(r);
                        coord.setColumn(c);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.BISHOP);
                        piece.setTeamColor(ChessGame.TeamColor.WHITE);
                        addPiece(coord, piece);
                    } else if(c == 3) { //QUEEN
                        Position coord = new Position();
                        coord.setRow(r);
                        coord.setColumn(c);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.QUEEN);
                        piece.setTeamColor(ChessGame.TeamColor.WHITE);
                        addPiece(coord, piece);
                    } else { //c == 4 KING
                        Position coord = new Position();
                        coord.setRow(r);
                        coord.setColumn(c);
                        Piece piece = new Piece();
                        piece.setPieceType(ChessPiece.PieceType.KING);
                        piece.setTeamColor(ChessGame.TeamColor.WHITE);
                        addPiece(coord, piece);
                    }
                }
                else {
                    Position coord = new Position();
                    coord.setRow(r);
                    coord.setColumn(c);
                    Piece piece = new Piece();
                    piece.setPieceType(null);
                    piece.setTeamColor(null);
                    addPiece(coord, piece);
                }
            }
        }
    }
}
