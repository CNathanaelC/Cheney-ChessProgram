package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Piece implements ChessPiece{
    private PieceType type;
    private ChessGame.TeamColor color;

    public boolean first_move = true;
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
//            if(first_move) {//Castling
//                if(getTeamColor() == ChessGame.TeamColor.WHITE) {
//                    Position rook_west = new Position();
//                    rook_west.setRow(1);
//                    rook_west.setColumn(1);
//                    Position rook_east = new Position();
//                    rook_east.setRow(1);
//                    rook_east.setColumn(8);
//                    Piece rw = (Piece) board.getPiece(rook_west);
//                    Piece re = (Piece) board.getPiece(rook_east);
//                    if(myPosition.getRow() == 1 && myPosition.getColumn() == 5) {
//                        if(rw != null) {
//                            if(rw.first_move) {
//
//                            }
//                        } else if (re != null) {
//                            if(re.first_move) {
//
//                            }
//                        }
//                    }
//                } else {
//
//                }
//            }
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
            moves.clear();
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
//            for(ChessMove m : moves) {
//                if(board.getPiece(m.getEndPosition()) != null) {
//                    System.out.println(board.getPiece(m.getStartPosition()).getTeamColor() + "(" + m.getStartPosition().getRow() + "," + m.getStartPosition().getColumn() + ") -" + board.getPiece(m.getEndPosition()).getTeamColor() + "(" + m.getEndPosition().getRow() + "," + m.getEndPosition().getColumn() + ")");
//                }
//            }
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
                //South
                int edit_int = 1;
                boolean blocked = false;
                if(myPosition.getRow()-edit_int >= 1) {
                    int r = myPosition.getRow()-edit_int;
                    Position prospect = new Position();
                    int c = myPosition.getColumn();
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) == null) {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    } else {
                        blocked = true;
                    }
                }
                if(!blocked && myPosition.getRow() == 7) {
                    edit_int = 2;
                    first_move = false;
                    if(myPosition.getRow()-edit_int >= 1) {
                        int r = myPosition.getRow()-edit_int;
                        Position prospect = new Position();
                        int c = myPosition.getColumn();
                        prospect.setRow(r);
                        prospect.setColumn(c);
                        if(board.getPiece(prospect) == null) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    }
                }


                //Southeast
                if (myPosition.getColumn()+1 <= 8 && myPosition.getRow()-1 >= 1) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn() + 1;
                    int r = myPosition.getRow() - 1;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    }
                }
                //Southwest
                if(myPosition.getColumn()-1 >= 1 && myPosition.getRow()-1 >= 1) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn()-1;
                    int r = myPosition.getRow()-1;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    }
                }
                return moves;
            }
            else {
                //North
                int edit_int = 1;
                boolean blocked = false;
                if(myPosition.getRow()+edit_int <= 8) {
                    int r = myPosition.getRow()+edit_int;
                    Position prospect = new Position();
                    int c = myPosition.getColumn();
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) == null) {
                        Move move = new Move();
                        move.setStartPosition((Position) myPosition);
                        move.setEndPosition(prospect);
                        moves.add(move);
                    } else {
                        blocked = true;
                    }
                }
                if(!blocked && myPosition.getRow() == 2) {
                    edit_int = 2;
                    first_move = false;
                    if(myPosition.getRow()+edit_int <= 8) {
                        int r = myPosition.getRow()+edit_int;
                        Position prospect = new Position();
                        int c = myPosition.getColumn();
                        prospect.setRow(r);
                        prospect.setColumn(c);
                        if(board.getPiece(prospect) == null) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    }
                }

                //Northwest
                if(myPosition.getColumn()-1 >= 1 && myPosition.getRow()+1 <= 8) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn() - 1;
                    int r = myPosition.getRow() + 1;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    }
                }
                //Northeast
                if(myPosition.getColumn()+1 <= 8 && myPosition.getRow()+1 <= 8) {
                    Position prospect = new Position();
                    int c = myPosition.getColumn() + 1;
                    int r = myPosition.getRow() + 1;
                    prospect.setRow(r);
                    prospect.setColumn(c);
                    if(board.getPiece(prospect) != null) {
                        if(board.getPiece(prospect).getTeamColor() != getTeamColor()) {
                            Move move = new Move();
                            move.setStartPosition((Position) myPosition);
                            move.setEndPosition(prospect);
                            moves.add(move);
                        }
                    }
                }

                return moves;
            }
        }
        return moves;
    }

}
