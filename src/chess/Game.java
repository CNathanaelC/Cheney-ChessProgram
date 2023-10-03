package chess;

import javax.management.openmbean.CompositeDataSupport;
import java.util.Collection;

import static chess.ChessGame.TeamColor.WHITE;

public class Game implements ChessGame{
    private TeamColor teamTurn = WHITE;
    Board official_board = new Board();
    @Override
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        return official_board.getPiece(startPosition).pieceMoves(official_board, startPosition);
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor)) {
            if(official_board.getPiece(getKing(teamColor)).pieceMoves(official_board, getKing(teamColor)).size() == 0) {

            }
        }
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        if(!isInCheck(teamColor)) {
            for(int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    Position p = new Position();
                    p.setColumn(c);
                    p.setRow(r);
                    if(official_board.getPiece(p).pieceMoves(official_board, p).size() != 0) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void setBoard(ChessBoard board) {
        for(int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                official_board.board[r][c] = ((Board)board).board[r][c];
            }
        }
    }
    public ChessPosition getKing(TeamColor tc) {
        for(int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Position p = new Position();
                p.setColumn(c);
                p.setRow(r);
                if(official_board.getPiece(p).getPieceType() == ChessPiece.PieceType.KING && official_board.getPiece(p).getTeamColor() == tc) {
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public ChessBoard getBoard() {
        return official_board;
    }

}
