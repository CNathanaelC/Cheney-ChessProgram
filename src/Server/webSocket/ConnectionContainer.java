package Server.webSocket;
import Model.AuthToken;
import Model.GameData;
import chess.Board;
import chess.ChessGame;
import chess.Game;
import chess.Move;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;



public class ConnectionContainer {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public Game observe(String auth, Integer gameID, Session session) throws Exception {
        var connection = new Connection(auth, session);
        connections.put(auth, connection);
        return new GameDAO().find(gameID).getGame();
    }
    public GameData play(String auth, Integer gameID, ChessGame.TeamColor playerColor, Session session) throws Exception {
        var connection = new Connection(auth, session);
        connections.put(auth, connection);
        Game game = new Game();
        game.official_board.resetBoard();
        new GameDAO().update(gameID, game);
        return new GameDAO().find(gameID);
    }

    public void leave(String auth) {
        connections.remove(auth);
    }
    public void resign(String auth, Integer gameID) throws IOException {
        try {
            GameData gameData = new GameDAO().find(gameID);
            Game game = gameData.getGame();
            AuthToken at = new AuthToken();
            at.setAuthToken(auth);
            if(game.go()) {
                if(new AuthDAO().getUsername(at).equals(gameData.getBlackUsername()) || new AuthDAO().getUsername(at).equals(gameData.getWhiteUsername())) {
                    game.end();
                    new GameDAO().update(gameID, game);
                    String message = String.format("Player %s resigned from the game", new AuthDAO().getUsername(at));
                    Notification notification = new Notification();
                    notification.message = message;
                    notifyAll(auth, notification);
                } else {
                    String message = String.format("Observers cannot resign, just leave");
                    ErrorMessage error = new ErrorMessage();
                    error.setErrorMessage(message);
                    notifyRoot(auth, error);
                }
            } else {
                String message = String.format("Game is over, resignation prohibited");
                ErrorMessage error = new ErrorMessage();
                error.setErrorMessage(message);
                notifyRoot(auth, error);
            }
        } catch(Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public Game move(String auth, Integer gameID, Move move) throws Exception {
        //Server verifies the validity of the move.
        GameData gameData = new GameDAO().find(gameID);
        Game game = gameData.getGame();
        AuthToken at = new AuthToken();
        at.setAuthToken(auth);
        if(game.go()) {
            if(new AuthDAO().getUsername(at).equals(gameData.getBlackUsername())) {
                if(game.getBoard().getPiece(move.getStartPosition()).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    if(game.validMoves(move.getStartPosition()).contains(move)) {
                        if(game.getTeamTurn() == ChessGame.TeamColor.BLACK) {
                            Game gameSaveFile = game;
                            game.makeMove(move);
                            if(game.isInCheck(ChessGame.TeamColor.BLACK)) {
                                game = gameSaveFile;
                                ErrorMessage error = new ErrorMessage();
                                error.setErrorMessage("You moved into check");
                                notifyRoot(auth, error);
                                return null;
                            }
                            if(game.isInCheck(game.getTeamTurn())) {
                                if(game.isInCheckmate(game.getTeamTurn())) {
                                    Notification notification = new Notification();
                                    notification.message = "Player is in Checkmate";
                                    notifyAll(auth, notification);
                                    game.end();
                                } else if (game.isInStalemate(game.getTeamTurn())) {
                                    Notification notification = new Notification();
                                    notification.message = "Player is in Stalemate";
                                    notifyAll(auth, notification);
                                    game.end();
                                } else {
                                    Notification notification = new Notification();
                                    notification.message = "Player is in check";
                                    notifyAll(auth, notification);
                                }
                            }
                        } else {
                            String message = String.format("It is not Black's turn");
                            ErrorMessage error = new ErrorMessage();
                            error.setErrorMessage(message);
                            notifyRoot(auth, error);
                            return null;
                        }

                        //Game is updated to represent the move. Game is updated in the database.
                        new GameDAO().update(gameID, game);
                        return game;
                    } else {
                        String message = String.format("Invalid Move");
                        ErrorMessage error = new ErrorMessage();
                        error.setErrorMessage(message);
                        notifyRoot(auth, error);
                        return null;
                    }
                } else {
                    String message = String.format("You cannot move this color");
                    ErrorMessage error = new ErrorMessage();
                    error.setErrorMessage(message);
                    notifyRoot(auth, error);
                    return null;
                }
            } else if(new AuthDAO().getUsername(at).equals(gameData.getWhiteUsername())) {
                if(game.getBoard().getPiece(move.getStartPosition()).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    if(game.validMoves(move.getStartPosition()).contains(move)) {
                        if(game.getTeamTurn() == ChessGame.TeamColor.WHITE) {
                            Game gameSaveFile = game;
                            game.makeMove(move);
                            if(game.isInCheck(ChessGame.TeamColor.WHITE)) {
                                game = gameSaveFile;
                                ErrorMessage error = new ErrorMessage();
                                error.setErrorMessage("You moved into check");
                                notifyRoot(auth, error);
                                return null;
                            }
                            if(game.isInCheck(game.getTeamTurn())) {
                                if(game.isInCheckmate(game.getTeamTurn())) {
                                    Notification notification = new Notification();
                                    notification.message = "Player is in Checkmate";
                                    notifyAll(auth, notification);
                                    game.end();
                                } else if (game.isInStalemate(game.getTeamTurn())) {
                                    Notification notification = new Notification();
                                    notification.message = "Player is in Stalemate";
                                    notifyAll(auth, notification);
                                    game.end();
                                } else {
                                    Notification notification = new Notification();
                                    notification.message = "Player is in check";
                                    notifyAll(auth, notification);
                                }
                            }
                        } else {
                            String message = String.format("It is not White's turn");
                            ErrorMessage error = new ErrorMessage();
                            error.setErrorMessage(message);
                            notifyRoot(auth, error);
                            return null;
                        }
                        //Game is updated to represent the move. Game is updated in the database.
                        new GameDAO().update(gameID, game);
                        return game;
                    } else {
                        String message = String.format("Invalid Move");
                        ErrorMessage error = new ErrorMessage();
                        error.setErrorMessage(message);
                        notifyRoot(auth, error);
                        return null;
                    }
                } else {
                    String message = String.format("You cannot move this color");
                    ErrorMessage error = new ErrorMessage();
                    error.setErrorMessage(message);
                    notifyRoot(auth, error);
                    return null;
                }
            } else {
                String message = String.format("Observers cannot make moves");
                ErrorMessage error = new ErrorMessage();
                error.setErrorMessage(message);
                notifyRoot(auth, error);
                return null;
            }
        } else {
            String message = String.format("Game is over, all moves prohibited");
            ErrorMessage error = new ErrorMessage();
            error.setErrorMessage(message);
            notifyRoot(auth, error);
            return null;
        }
    }

    public void notifyOthers(String excludeAuth, ServerMessage message) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.auth.equals(excludeAuth)) {
                    c.send(new Gson().toJson(message));
                }
            } else {
                removeList.add(c);
            }
        }
        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.auth);
        }
    }
    public void notifyRoot(String excludeAuth, ServerMessage message) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (c.auth.equals(excludeAuth)) {
                    c.send(new Gson().toJson(message));
                }
            } else {
                removeList.add(c);
            }
        }
        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.auth);
        }
    }
    public void notifyAll(String excludeAuth, ServerMessage message) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                c.send(new Gson().toJson(message));
            } else {
                removeList.add(c);
            }
        }
        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.auth);
        }
    }
}
