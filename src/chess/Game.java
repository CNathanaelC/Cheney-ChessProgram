package chess;

import javax.management.openmbean.CompositeDataSupport;
import java.util.ArrayList;
import java.util.Collection;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class Game implements ChessGame{
    private TeamColor teamTurn = WHITE;
    public Board official_board = new Board();
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
        Piece piece = (Piece) official_board.getPiece(move.getStartPosition());
        //if move is in possible moves
        Collection<ChessMove> possibleMoves = validMoves(move.getStartPosition());
        boolean continueQ = false;
        for(ChessMove possibleMove : possibleMoves) {
            //FIXME::remove the testing output
            System.out.println("Move: (" + ((Move)possibleMove).getStartPosition().getRow() + "," + ((Move)possibleMove).getStartPosition().getColumn() + ") to (" +
                    ((Move)possibleMove).getEndPosition().getRow() + "," + ((Move)possibleMove).getEndPosition().getColumn() + ")\n");
            if(((Move)possibleMove).equals(move)) {
                continueQ = true;
            }
        }
        if(!continueQ) {
            throw new InvalidMoveException("Invalid move: " + move);
        }

        //if king is in check after
        if(isInCheck(teamTurn)) {
            throw new InvalidMoveException("Invalid move: " + move);
        }
        if(official_board.getPiece(move.getStartPosition()).getTeamColor() != teamTurn) {
            throw new InvalidMoveException("Invalid move: " + move);
        }
        //make the move
        official_board.board[move.getStartPosition().getRow()-1][move.getStartPosition().getColumn()-1] = null;
        official_board.board[move.getEndPosition().getRow()-1][move.getEndPosition().getColumn()-1] = piece;
        //set it as the next teamturn
        if(teamTurn == WHITE) {
            teamTurn = BLACK;
        } else {
            teamTurn = WHITE;
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        Position kp = (Position) getKing(teamColor);
        for(int r = 0; r < 8; r++) {
            for(int c = 0; c < 8; c++) {
                if(official_board.board[r][c] != null) {
                    if(official_board.board[r][c].getTeamColor() != teamColor) {
                        Position pp = new Position();
                        pp.setColumn(c+1);
                        pp.setRow(r+1);
                        Collection<ChessMove> possible_moves = validMoves(pp);
                        for(ChessMove m : possible_moves) {
                            if(m.getEndPosition().getRow() == kp.getRow() && m.getEndPosition().getColumn() == kp.getColumn()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor)) {
            if(official_board.getPiece(getKing(teamColor)).pieceMoves(official_board, getKing(teamColor)).size() == 0) {
                return true;
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
                    p.setColumn(c+1);
                    p.setRow(r+1);
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
                if(official_board.board[r][c] != null) {
                    Position p = new Position();
                    p.setColumn(c+1);
                    p.setRow(r+1);
                    if(official_board.getPiece(p).getPieceType() == ChessPiece.PieceType.KING && official_board.getPiece(p).getTeamColor() == tc) {
                        return p;
                    }
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
