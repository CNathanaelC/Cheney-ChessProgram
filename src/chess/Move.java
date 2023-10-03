package chess;

import static chess.ChessPiece.PieceType.PAWN;
import static chess.ChessPiece.PieceType.QUEEN;

public class Move implements ChessMove{
    Position sp = new Position();
    Position ep = new Position();
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
}
