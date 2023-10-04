package chess;

import static chess.ChessPiece.PieceType.PAWN;
import static chess.ChessPiece.PieceType.QUEEN;

public class Move implements ChessMove{
    private Position sp = new Position();
    private Position ep = new Position();
    private ChessPiece.PieceType pp;
    @Override
    public ChessPosition getStartPosition() {
        return sp;
    }
    public void setStartPosition(Position s) {
        sp = s;
    }


    @Override
    public ChessPosition getEndPosition() {
        return ep;
    }
    public void setEndPosition(Position e) {
        ep = e;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        if(getEndPosition().getRow() == 0 || getEndPosition().getRow() == 7) {
            return QUEEN;
        } else {
            return PAWN;
        }
    }
    public void setPromotionPiece(ChessPiece.PieceType pt) {
        pp = pt;
    }
    @Override
    public boolean equals(ChessMove move) {
        return (this.getStartPosition().getRow() == move.getStartPosition().getRow() && this.getStartPosition().getColumn() == move.getStartPosition().getColumn() && this.getEndPosition().getRow() == move.getEndPosition().getRow() && this.getEndPosition().getColumn() == move.getEndPosition().getColumn());
    }
}
