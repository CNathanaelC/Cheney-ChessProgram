package chess;

import java.util.Collection;
import java.util.HashSet;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class Game implements ChessGame{
    private TeamColor teamTurn = WHITE;
    public Board official_board = new Board();
    private boolean gameOver = false;
    public boolean started = false;
    public void start() {
        started = true;
    }
    public boolean go() {
        return !gameOver;
    }
    public void end() {
        gameOver = true;
    }
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
        Collection<ChessMove> valid = new HashSet<>();
        //iterate through piece moves
        for(ChessMove move : official_board.getPiece(startPosition).pieceMoves(official_board, startPosition)){
            //pretend making move
            Game simulation = new Game();
            simulation.setBoard(official_board);
            Piece pretend = new Piece();
            pretend = (Piece) simulation.official_board.getPiece(move.getStartPosition());
            simulation.official_board.board[move.getStartPosition().getRow()-1][move.getStartPosition().getColumn()-1] = null;
            simulation.official_board.board[move.getEndPosition().getRow()-1][move.getEndPosition().getColumn()-1] = pretend;
            //check if board clone is in check
            if(!simulation.isInCheck(official_board.getPiece(startPosition).getTeamColor())) {
                valid.add(move);
            }
        }
        return valid;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Piece piece = (Piece) official_board.getPiece(move.getStartPosition());
        //trying to move on the wrong turn
        if(official_board.getPiece(move.getStartPosition()).getTeamColor() != teamTurn) {
            throw new InvalidMoveException("Invalid move: " + move);
        }
        //if move is in possible moves
        Collection<ChessMove> possibleMoves = validMoves(move.getStartPosition());
        boolean continueQ = false;
        for(ChessMove possibleMove : possibleMoves) {
           if(((Move)possibleMove).equals(move)) {
                continueQ = true;
            }
        }
        if(!continueQ) {
            throw new InvalidMoveException("Invalid move: " + move);
        }
        //if you take your own piece
        if(official_board.getPiece(move.getEndPosition()) != null) {
            if (official_board.getPiece(move.getStartPosition()).getTeamColor() == official_board.getPiece(move.getEndPosition()).getTeamColor()) {
                throw new InvalidMoveException("Invalid move: " + move);
            }
        }
        //make the move
        Piece justInCase = new Piece();
        justInCase = official_board.board[move.getStartPosition().getRow()-1][move.getStartPosition().getColumn()-1];
        official_board.board[move.getStartPosition().getRow()-1][move.getStartPosition().getColumn()-1] = null;
        official_board.board[move.getEndPosition().getRow()-1][move.getEndPosition().getColumn()-1] = piece;
        //if king is in check after
        if(isInCheck(teamTurn)) {
            official_board.board[move.getStartPosition().getRow()-1][move.getStartPosition().getColumn()-1] = piece;
            official_board.board[move.getEndPosition().getRow()-1][move.getEndPosition().getColumn()-1] = justInCase;
            throw new InvalidMoveException("Invalid move: " + move);
        }
        //promote piece if applicable
        if(move.getPromotionPiece() != null) {
            official_board.board[move.getEndPosition().getRow()-1][move.getEndPosition().getColumn()-1].setPieceType(move.getPromotionPiece());
        }
        //set it as the next teamturn
        if(teamTurn == WHITE) {
            setTeamTurn(BLACK);
        } else {
            setTeamTurn(WHITE);
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        Position kp = (Position) getKing(teamColor);
        if(kp != null) {
            for(int r = 0; r < 8; r++) {
                for(int c = 0; c < 8; c++) {
                    if(official_board.board[r][c] != null) {
                        if(official_board.board[r][c].getTeamColor() != teamColor) {
                            Position pp = new Position();
                            pp.setColumn(c+1);
                            pp.setRow(r+1);
                            Collection<ChessMove> possible_moves = official_board.getPiece(pp).pieceMoves(official_board,pp);
                            for(ChessMove m : possible_moves) {
                                if(m.getEndPosition().getRow() == kp.getRow() && m.getEndPosition().getColumn() == kp.getColumn()) {
                                    return true;
                                }
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
            if(validMoves(getKing(teamColor)).size() == 0) {
                return true;
            } else {
                Collection <ChessMove> vm = validMoves(getKing(teamColor));
                for(ChessMove cm : vm) {
                    if(cm != null) {
                        for(int r = 0; r < 8; r++) {
                            for(int c = 0; c < 8; c++) {
                                if(official_board.board[r][c] != null) {
                                    if(official_board.board[r][c].getTeamColor() != teamColor) {
                                        Position pp = new Position();
                                        pp.setColumn(c+1);
                                        pp.setRow(r+1);
                                        Collection<ChessMove> possible_moves = validMoves(pp);
                                        for(ChessMove m : possible_moves) {
                                            if(m.getEndPosition().getRow() == cm.getEndPosition().getRow() && m.getEndPosition().getColumn() == cm.getEndPosition().getColumn()) {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        if(validMoves(getKing(teamColor)).size() == 0) {
            return true;
        }
        for(int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Position p = new Position();
                p.setColumn(c+1);
                p.setRow(r+1);
                if(official_board.getPiece(p) != null) {
                    if(validMoves(p).size() != 0) {
                        return false;
                    }
                }
            }
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
