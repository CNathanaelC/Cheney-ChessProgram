package chess;

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
        return null;
    }
}
