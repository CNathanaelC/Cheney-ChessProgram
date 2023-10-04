package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Piece implements ChessPiece{
    private PieceType type;
    private ChessGame.TeamColor color;

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }
    public void setTeamColor(ChessGame.TeamColor c) {
        color = c;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }
    public void setPieceType(PieceType t) {
        type = t;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        Move move = new Move();
        if(getPieceType() == PieceType.KING) {
            for(int r = -1; r <= 1; r++) {
                for(int c = -1; c <= 1; c++) {
                    Position prospect = new Position();
                    if(myPosition.getRow()+r > -1 && myPosition.getRow()+r < 8 && myPosition.getColumn()+c > -1 && myPosition.getColumn()+c < 8) {
                        prospect.setRow(myPosition.getRow()+r);
                        prospect.setColumn(myPosition.getColumn()+c);
                        if(board.getPiece(prospect) == null) {
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        } else if(board.getPiece(prospect).getTeamColor() != this.getTeamColor()) {
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    }
                }
            }
        } else if(getPieceType()== PieceType.QUEEN) {
            //while row and column are between -1 and 8

        } else if(getPieceType()== PieceType.BISHOP) {

        } else if(getPieceType()== PieceType.KNIGHT) {

        } else if(getPieceType()== PieceType.ROOK) {

        } else if(getPieceType()== PieceType.PAWN) {

        }
        return moves;
    }

}
