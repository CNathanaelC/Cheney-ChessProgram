package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Piece implements ChessPiece{
    private PieceType type;
    private ChessGame.TeamColor color;

    private boolean first_move = true;

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

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new HashSet<>();
        if(getPieceType() == PieceType.KING) {
            for(int r = -1; r <= 1; r++) {
                for(int c = -1; c <= 1; c++) {
                    Position prospect = new Position();
                    if(myPosition.getRow()+r >= 1 && myPosition.getRow()+r <= 8 && myPosition.getColumn()+c >= 1 && myPosition.getColumn()+c <= 8) {
                        prospect.setRow(myPosition.getRow()+r);
                        prospect.setColumn(myPosition.getColumn()+c);
                        if(board.getPiece(prospect) == null) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add((ChessMove)move);
                        } else if(board.getPiece(prospect).getTeamColor() != this.getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition((Position) prospect);
                            moves.add((ChessMove) move);
                        }
                    }
                }
            }
            return moves;
        }
        else if(getPieceType()== PieceType.QUEEN) {
            boolean piecesInPath = true;
            //North
            piecesInPath = true;
            for(int r = myPosition.getRow()+1; r <= 8; r++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn();
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add((ChessMove) move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add((ChessMove) move);
                    }
                }
            }
            //South
            piecesInPath = true;
            for(int r = myPosition.getRow()-1; r >= 1; r--) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn();
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //West
            piecesInPath = true;
            for(int c = myPosition.getColumn()-1; c >= 1; c--) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int r = myPosition.getRow();
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //East
            piecesInPath = true;
            for(int c = myPosition.getColumn()+1; c <= 8; c++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int r = myPosition.getRow();
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //Northwest
            piecesInPath = true;
            for(int i = 1; myPosition.getColumn()-i >= 1 && myPosition.getRow()+i <= 8; i++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn() - i;
                    int r = myPosition.getRow() + i;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //Southeast
            piecesInPath = true;
            for(int i = 1; myPosition.getColumn()+i <= 8 && myPosition.getRow()-i >= 1; i++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn() + i;
                    int r = myPosition.getRow() - i;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //Northeast
            piecesInPath = true;
            for(int i = 1; myPosition.getColumn()+i <= 8 && myPosition.getRow()+i <= 8; i++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn() + i;
                    int r = myPosition.getRow() + i;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //Southwest
            piecesInPath = true;
            for(int i = 1; myPosition.getColumn()-i >= 1 && myPosition.getRow()-i >= 1; i++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn()-i;
                    int r = myPosition.getRow()-i;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            return moves;
        }
        else if(getPieceType()== PieceType.BISHOP) {
            boolean piecesInPath = true;
            //Northwest
            piecesInPath = true;
            for(int i = 1; myPosition.getColumn()-i >= 1 && myPosition.getRow()+i <= 8; i++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn() - i;
                    int r = myPosition.getRow() + i;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //Southeast
            piecesInPath = true;
            for(int i = 1; myPosition.getColumn()+i <= 8 && myPosition.getRow()-i >= 1; i++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn() + i;
                    int r = myPosition.getRow() - i;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //Northeast
            piecesInPath = true;
            for(int i = 1; myPosition.getColumn()+i <= 8 && myPosition.getRow()+i <= 8; i++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn() + i;
                    int r = myPosition.getRow() + i;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //Southwest
            piecesInPath = true;
            for(int i = 1; myPosition.getColumn()-i >= 1 && myPosition.getRow()-i >= 1; i++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn()-i;
                    int r = myPosition.getRow()-i;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            return moves;
        }
        else if(getPieceType()== PieceType.KNIGHT) {
            for(int r : new int[]{-2, -1, 1, 2, -2, -1, 1, 2}) {
                for(int c : new int[]{1, 2, -2, -1, -1, -2, 2,1}) {
                    if(Math.abs(r) != Math.abs(c)) {
                        Position prospect = new Position();
                        if(myPosition.getRow()+r >= 1 && myPosition.getRow()+r <= 8 && myPosition.getColumn()+c >= 1 && myPosition.getColumn()+c <= 8) {
                            prospect.setRow(myPosition.getRow()+r);
                            prospect.setColumn(myPosition.getColumn()+c);
                            if(board.getPiece(prospect) == null) {
                                Move move = new Move();
                                move.setStartPosition((Position) myPosition);
                                move.setEndPosition(prospect);
                                moves.add(move);
                            } else if(board.getPiece(prospect).getTeamColor() != this.getTeamColor()) {
                                Move move = new Move();
                                move.setStartPosition((Position) myPosition);
                                move.setEndPosition(prospect);
                                moves.add(move);
                            }
                        }
                    }
                }
            }
            return moves;
        }
        else if(getPieceType()== PieceType.ROOK) {
            boolean piecesInPath = true;
            //North
            piecesInPath = true;
            for(int r = myPosition.getRow()+1; r <= 8; r++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn();
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add((ChessMove) move);
                        }
                    } else {
                        Move move = new Move();
                       move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add((ChessMove) move);
                    }
                }
            }
            //South
            piecesInPath = true;
            for(int r = myPosition.getRow()-1; r >= 1; r--) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn();
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //West
            piecesInPath = true;
            for(int c = myPosition.getColumn()-1; c >= 1; c--) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int r = myPosition.getRow();
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            //East
            piecesInPath = true;
            for(int c = myPosition.getColumn()+1; c <= 8; c++) {
                if(piecesInPath) {
                    Position prospect = new Position();
                    int r = myPosition.getRow();
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        piecesInPath = false;
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    } else {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
            }
            return moves;
        }
        else if(getPieceType()== PieceType.PAWN) {
            if(getTeamColor() == ChessGame.TeamColor.BLACK) {
                //if this is the first move
                if(first_move) {
                    first_move = false;
                    for(int r = 1; r <= 2; r++) {
                        if(myPosition.getRow()-r <= 8 && myPosition.getRow()-r >= 1) {
                            Position prospect = new Position();
                            prospect.setRow(myPosition.getRow()-r);
                            prospect.setColumn(myPosition.getColumn());
                            if(board.getPiece(prospect) == null) {
                                Move move = new Move();
                                move.setStartPosition((Position) myPosition);
                                move.setEndPosition(prospect);
                                moves.add(move);
                            }
                        }
                    }
                }
                //if there are opposing pieces diagonal
                for(int r = 1; r <= 1; r++) {
                    for (int c = -1; c <= 1; c+=2) {
                        Position prospect = new Position();
                        if(myPosition.getRow()-r >= 1 && myPosition.getRow()-r <= 8 && myPosition.getColumn()-c >= 1 && myPosition.getColumn()-c <= 8) {
                            prospect.setRow(myPosition.getRow()-r);
                            prospect.setColumn(myPosition.getColumn()-c);
                            if( board.getPiece(prospect) != null && board.getPiece(prospect).getTeamColor() != this.getTeamColor()) {
                                Move move = new Move();
                                move.setStartPosition((Position) myPosition);
                                move.setEndPosition(prospect);
                                moves.add(move);
                            }
                        }
                    }
                }
                //all others (only can more forward one space
                Position prospect = new Position();
                if(myPosition.getRow()-1 <= 1) {
                    prospect.setRow(myPosition.getRow()+1);
                    prospect.setColumn(myPosition.getColumn());
                    if(board.getPiece(prospect) == null) {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
                return moves;
            }
            else {
                //if this is the first move
                if(first_move) {
                    first_move = false;
                    for(int r = 1; r <= 2; r++) {
                        if(myPosition.getRow()+r <= 8 && myPosition.getRow()+r >= 1) {
                            Position prospect = new Position();
                            prospect.setRow(myPosition.getRow()+r);
                            prospect.setColumn(myPosition.getColumn());
                            if(board.getPiece(prospect) == null) {
                                Move move = new Move();
                                move.setStartPosition((Position) myPosition);
                                move.setEndPosition(prospect);
                                moves.add(move);
                            }
                        }
                    }
                }
                //if there are opposing pieces diagonal
                for(int r = 1; r <= 1; r++) {
                    for (int c = -1; c <= 1; c+=2) {
                        Position prospect = new Position();
                        if(myPosition.getRow()+r >= 1 && myPosition.getRow()+r <= 8 && myPosition.getColumn()+c >= 1 && myPosition.getColumn()+c <= 8) {
                            prospect.setRow(myPosition.getRow()+r);
                            prospect.setColumn(myPosition.getColumn()+c);
                            if(board.getPiece(prospect) != null && board.getPiece(prospect).getTeamColor() != this.getTeamColor()) {
                                Move move = new Move();
                                move.setStartPosition((Position) myPosition);
                                move.setEndPosition(prospect);
                                moves.add(move);
                            }
                        }
                    }
                }
                //all others (only can more forward one space
                Position prospect = new Position();
                if(myPosition.getRow()+1 <= 8) {
                    prospect.setRow(myPosition.getRow()+1);
                    prospect.setColumn(myPosition.getColumn());
                    if(board.getPiece(prospect) == null) {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    }
                }
                return moves;
            }
        }
        return moves;
    }

}
