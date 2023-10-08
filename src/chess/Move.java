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
        if(getEndPosition().getRow() == 1 || getEndPosition().getRow() == 8) {
            return QUEEN;
        } else {
            return PAWN;
        }
    }
    public void setPromotionPiece(ChessPiece.PieceType pt) {
        pp = pt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return pp == move.pp;
    }

    @Override
    public int hashCode() {
        return 0;
    }
    //    @Override
//    public boolean equals(Object o) {
//        return (this.getStartPosition().getRow() == ((Move)o).getStartPosition().getRow() && this.getStartPosition().getColumn() == ((Move)o).getStartPosition().getColumn() && this.getEndPosition().getRow() == ((Move)o).getEndPosition().getRow() && this.getEndPosition().getColumn() == ((Move)o).getEndPosition().getColumn());
//    }
//
//    @Override
//    public int hashCode() {
//        return System.identityHashCode(this);
//    }
}
