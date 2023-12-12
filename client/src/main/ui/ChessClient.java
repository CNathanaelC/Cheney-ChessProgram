package ui;

import chess.*;
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
    public Integer joinedGameID;
    private static ServerFacade server = new ServerFacade();

    public static void main(String[] args) {
        new Repl().run();
    }
    public String execute(String input) {
        if(sessionLogin) {
            if(joinedGame) {
                try {
                    var tokens = input.toLowerCase().split(" ");
                    var cmd = (tokens.length > 0) ? tokens[0] : "help";
                    var params = Arrays.copyOfRange(tokens, 1, tokens.length);
                    return switch (cmd) {
                        case "resign" -> resign();
                        case "move" -> makeMove(params);
                        case "highlight" -> highlight();
                        case "redraw" -> redraw();
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
        joinedGame = false;
        return "quit";
    }
    public String login(String...params) throws ResponseException {
        if(params.length == 2) {
            if(server.login(params[0], params[1])) {
                sessionLogin = true;
                return "User was successfully logged in as " + params[0] + ".\n";
            } else {
                throw new ResponseException("Login Failure\n");
            }
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String logout() throws ResponseException {
        if(server.logout()) {
            sessionLogin = false;
            joinedGame = false;
            leaveGame();
            return "User was successfully logged out\n";
        } else {
            throw new ResponseException("Logout Failure\n");
        }
    }
    public String register(String...params) throws ResponseException {
        if(params.length == 3) {
            if(server.register(params[0], params[1], params[2])) {
                sessionLogin = true;
                return "User was successfully logged in as " + params[0] + ".\n";
            } else {
                throw new ResponseException("Register Failure\n");
            }
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String createGame(String...params) throws ResponseException {
        if(params.length == 1) {
            if(server.createGame(params[0])) {

            } else {
                throw new ResponseException("Game Creation Failure\n");
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
            throw new ResponseException("List Games Failure\n");
        }
    }
    public String joinGame(String...params) throws ResponseException {
        if(params.length == 2) {
            if(server.joinPlayer(params[0], Integer.parseInt(params[1]))) {
                playerColor = params[0].toUpperCase();
                joinedGame = true;
                joinedGameID = Integer.parseInt(params[1]);
                server.printChessBoard(params[0].toUpperCase());
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
                server.printChessBoard("WHITE");
                joinedGame = true;
                joinedGameID = Integer.parseInt(params[0]);
                return "Game " + params[0] + " was successfully joined as the observer.\n";
            } else {
                throw new ResponseException("Game Observe Failure\n");
            }
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String leaveGame() throws ResponseException {
        joinedGame = false;
        joinedGameID = null;
        return "";
    }
    public String makeMove(String...params) throws ResponseException {
        if(params.length == 2) {
            Move move = new Move();
            Position sp = new Position();
            Position ep = new Position();
            var s = Arrays.copyOfRange(params[0].toLowerCase().split(","), 0, params[0].length());
            var e = Arrays.copyOfRange(params[1].toLowerCase().split(","), 0, params[1].length());
            sp.setRow(s[0].charAt(0)-'a'+1);
            sp.setColumn(Integer.parseInt(s[1]));
            ep.setRow(e[0].charAt(0)-'a'+1);
            ep.setColumn(Integer.parseInt(e[1]));
            if(server.makeMove(joinedGameID, move)) {
                return "Move from " + params[0] + " to " + params[1] + " was successfully made.\n";
            } else {
                throw new ResponseException("Move Failure\n");
            }
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String resign() throws ResponseException {
        if(server.resign(joinedGameID)) {

        } else {
            throw new ResponseException("Could not Resign");
        }

        return "";
    }
    public String redraw() throws ResponseException {

        server.printChessBoard(playerColor);
        return "";
    }
    public String highlight() throws ResponseException {
        //server.getGame().validMoves()
        server.printChessBoard(playerColor);
        return "";
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
