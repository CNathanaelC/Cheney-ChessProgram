package ui;

import chess.Board;
import chess.Game;
import chess.Piece;
import chess.Position;
import webSocketMessages.userCommands.JoinPlayer;

import javax.websocket.Endpoint;
import java.io.IOException;
import java.util.Arrays;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessPiece.PieceType.*;
import static chess.ChessPiece.PieceType.PAWN;
import static ui.EscapeSequences.*;

public class ChessClient {
    public boolean sessionLogin = false;
    public boolean joinedGame = false;
    public String playerColor;
    private static ServerFacade server = new ServerFacade();

    public static void main(String[] args) {
        new Repl().run();
    }
    public String execute(String input) {
        if(sessionLogin) {
            try {
                var tokens = input.toLowerCase().split(" ");
                var cmd = (tokens.length > 0) ? tokens[0] : "help";
                var params = Arrays.copyOfRange(tokens, 1, tokens.length);
                return switch (cmd) {
                    case "quit" -> quit();
                    case "create" -> createGame(params);
                    case "play" -> joinGame(params);
                    case "observe" -> joinObserver(params);
                    case "list" -> listGames();
                    case "logout" -> logout();
                    case "poista" -> c();
                    default -> help();
                };
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            if(joinedGame) {
                printChessBoard("WHITE");
                printChessBoard("BLACK");
                try {
                    var tokens = input.toLowerCase().split(" ");
                    var cmd = (tokens.length > 0) ? tokens[0] : "help";
                    var params = Arrays.copyOfRange(tokens, 1, tokens.length);
                    return switch (cmd) {
                        case "resign" -> resign();
                        case "move" -> makeMove(params);
                        case "highlight" -> highlight(params);
                        case "redraw" -> redraw(playerColor);
                        case "leave" -> leaveGame();
                        case "poista" -> c();
                        default -> help();
                    };
                } catch (ResponseException e) {
                    return e.getMessage();
                }
            } else {
                try {
                    var tokens = input.toLowerCase().split(" ");
                    var cmd = (tokens.length > 0) ? tokens[0] : "help";
                    var params = Arrays.copyOfRange(tokens, 1, tokens.length);
                    return switch (cmd) {
                        case "quit" -> quit();
                        case "login" -> login(params);
                        case "register" -> register(params);
                        case "poista" -> c();
                        default -> help();
                    };
                } catch (ResponseException e) {
                    return e.getMessage();
                }
            }
        }
    }

    public String help() {
        if(!sessionLogin) {
            return "register - <USERNAME> <PASSWORD> <EMAIL> - to create an account\n" +
                    "login <USERNAME> <PASSWORD> - to sign into an existing account\n" +
                    "quit - to quit playing chess\n" +
                    "help - to display possible commands\n";
        } else {
            if(joinedGame) {
                return "redraw - to redraw the chess board\n" +
                        "move <START[a-h][,][1-8]> <END[a-h][,][1-8]> - to move a piece from starting position to the ending position\n" +
                        "highlight <START[a-h][,][1-8]> - to highlight the possible legal moves from the given starting position\n" +
                        "leave - to stop playing this chess game\n" +
                        "resign - to forfeit the game\n" +
                        "help - to display possible commands\n";
            } else {
                return "create <NAME> - to create a new game\n" +
                        "list - to list all of the games available to join\n" +
                        "play <[WHITE|BLACK|<empty>]> <ID> - to join a game\n" +
                        "observe <ID> - to observe a game\n" +
                        "logout - to stop playing chess\n" +
                        "quit - to quit playing chess\n" +
                        "help - to display possible commands\n";
            }
        }
    }
    public String quit() throws ResponseException {
        sessionLogin = false;
        return "quit";
    }
    public String login(String...params) throws ResponseException {
        if(params.length == 2) {
            if(server.login(params[0], params[1])) {
                sessionLogin = true;
                return "User was successfully logged in as " + params[0] + ".\n";
            } else {
                throw new ResponseException("Login Failure");
            }
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String logout() throws ResponseException {
        if(server.logout()) {
            sessionLogin = false;
            return "User was successfully logged out\n";
        } else {
            throw new ResponseException("Logout Failure");
        }
    }
    public String register(String...params) throws ResponseException {
        if(params.length == 3) {
            if(server.register(params[0], params[1], params[2])) {
                sessionLogin = true;
                return "User was successfully logged in as " + params[0] + ".\n";
            } else {
                throw new ResponseException("Register Failure");
            }
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String createGame(String...params) throws ResponseException {
        if(params.length == 1) {
            if(server.createGame(params[0])) {

            } else {
                throw new ResponseException("Game Creation Failure");
            }

            return "Game was successfully created\n";
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String listGames() throws ResponseException {
        if(server.listGames()) {
            return "";
        } else {
            throw new ResponseException("List Games Failure");
        }
    }
    public String joinGame(String...params) throws ResponseException {
        if(params.length == 2) {
            if(server.joinPlayer(params[0], Integer.parseInt(params[1]))) {
                printChessBoard("WHITE");
                printChessBoard("BLACK");
                playerColor = params[0];
                return "Game " + params[1] + " was successfully joined as the " + params[0].toLowerCase() + " player.\n";
            } else {
                throw new ResponseException("Game Join Failure\n");
            }
        } else if (params.length == 1) {
            return joinObserver(params);
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String joinObserver(String...params) throws ResponseException {
        if(params.length == 1) {
            if(server.joinObserver(Integer.parseInt(params[0]))) {
                printChessBoard("WHITE");
                printChessBoard("BLACK");
                return "Game " + params[0] + " was successfully joined as the observer.\n";
            } else {
                throw new ResponseException("Game Observe Failure\n");
            }
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String leaveGame() throws ResponseException {

        return "";
    }
    public String makeMove(String...params) throws ResponseException {
        if(params.length == 2) {
            if(server.login(params[0], params[1])) {
                return "Move from " + params[0] + "to " + params[1] + "was succesfully made.\n";
            } else {
                throw new ResponseException("Move Failure");
            }
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String resign() throws ResponseException {
        return "";
    }
    public String redraw(String color) throws ResponseException {
        printChessBoard(color);
        return "";
    }
    public String highlight(String...params) throws ResponseException {
        return "";
    }
    public void printChessBoard(String color) {
        //TODO::get the actual board instead of a stand in board
        Board board = new Board();
        board.resetBoard();
        boolean b = true;
        System.out.print(SET_TEXT_COLOR_BLACK);
        if(color.equals("WHITE")) {
            System.out.println(SET_BG_COLOR_GREEN + "    a  b  c  d  e  f  g  h    " + RESET_BG_COLOR);
            for(int r = 7; r > -1; r--) {
                r++;
                System.out.print(SET_BG_COLOR_GREEN + " " + r + " " + RESET_BG_COLOR);
                r--;
                for(int c = 0; c < 8; c++) {
                    Piece piece = new Piece();
                    Position pos = new Position();
                    pos.setColumn(c+1);
                    pos.setRow(r+1);
                    piece = (Piece)board.getPiece(pos);
                    if(piece != null) {
                        if(piece.getPieceType() == KING) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " K ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " k ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " K ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " k ");
                                }
                            }
                        } else if(piece.getPieceType() == QUEEN) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " Q ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " q ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " Q ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " q ");
                                }
                            }
                        } else if(piece.getPieceType() == BISHOP) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " B ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " b ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " B ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " b ");
                                }
                            }
                        } else if(piece.getPieceType() == KNIGHT) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " N ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " n ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " N ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " n ");
                                }
                            }
                        } else if(piece.getPieceType() == ROOK) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " R ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " r ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " R ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " r ");
                                }
                            }
                        } else if (piece.getPieceType() == PAWN) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " P ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " p ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " P ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " p ");
                                }
                            }
                        }
                    } else {
                        if(b){
                            b = !b;
                            System.out.print(SET_BG_COLOR_MAGENTA + "   ");
                        } else {
                            b = !b;
                            System.out.print(SET_BG_COLOR_BLUE + "   ");
                        }
                    }
                }
                r++;
                System.out.println(SET_BG_COLOR_GREEN + " " + r + " " + RESET_BG_COLOR);
                r--;
                b = !b;
            }
            System.out.println(SET_BG_COLOR_GREEN + "    a  b  c  d  e  f  g  h    " + RESET_BG_COLOR);
            System.out.println(RESET_BG_COLOR + RESET_TEXT_COLOR);
        }
        else {
            System.out.println(SET_BG_COLOR_GREEN + "    h  g  f  e  d  c  b  a    " + RESET_BG_COLOR);
            for(int r = 0; r < 8; r++) {
                r++;
                System.out.print(SET_BG_COLOR_GREEN + " " + r + " " + RESET_BG_COLOR);
                r--;
                for(int c = 7; c > -1; c--) {
                    Piece piece = new Piece();
                    Position pos = new Position();
                    pos.setColumn(c+1);
                    pos.setRow(r+1);
                    piece = (Piece)board.getPiece(pos);
                    if(piece != null) {
                        if(piece.getPieceType() == KING) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " K ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " k ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " K ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " k ");
                                }
                            }
                        } else if(piece.getPieceType() == QUEEN) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " Q ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " q ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " Q ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " q ");
                                }
                            }
                        } else if(piece.getPieceType() == BISHOP) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " B ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " b ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " B ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " b ");
                                }
                            }
                        } else if(piece.getPieceType() == KNIGHT) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " N ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " n ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " N ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " n ");
                                }
                            }
                        } else if(piece.getPieceType() == ROOK) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " R ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " r ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " R ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " r ");
                                }
                            }
                        } else if (piece.getPieceType() == PAWN) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " P ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " p ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " P ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " p ");
                                }
                            }
                        }
                    } else {
                        if(b){
                            b = !b;
                            System.out.print(SET_BG_COLOR_MAGENTA + "   ");
                        } else {
                            b = !b;
                            System.out.print(SET_BG_COLOR_BLUE + "   ");
                        }
                    }
                }
                r++;
                System.out.println(SET_BG_COLOR_GREEN + " " + r + " " + RESET_BG_COLOR);
                r--;
                b = !b;
            }
            System.out.println(SET_BG_COLOR_GREEN + "    h  g  f  e  d  c  b  a    " + RESET_BG_COLOR);
            System.out.println(RESET_BG_COLOR + RESET_TEXT_COLOR);
        }
    }
    public void destroy(String p) throws ResponseException {
        if(p.equals("samplePassword")) {
            if(!server.clear()) {
                throw new ResponseException("Clear Failure");
            }
        } else {
            throw new ResponseException("You really thought that would work didn't you");
        }
    }
    private String c() {
        sessionLogin = false;
        joinedGame = false;
        try {
            destroy("samplePassword");
        } catch(Exception e) {

        }
        return "";
    }
}
