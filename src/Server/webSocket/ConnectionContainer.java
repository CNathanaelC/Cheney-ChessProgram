package Server.webSocket;
import Model.GameData;
import chess.ChessGame;
import chess.Game;
import chess.Move;
import com.google.gson.Gson;
import dataAccess.GameDAO;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;



public class ConnectionContainer {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void observe(String auth, Session session) {
        var connection = new Connection(auth, session);
        connections.put(auth, connection);
    }
    public GameData play(String auth, Integer gameID, ChessGame.TeamColor playerColor, Session session) throws Exception {
        var connection = new Connection(auth, session);
        connections.put(auth, connection);
        return new GameDAO().find(gameID);
    }

    public void leave(String auth) {
        connections.remove(auth);
    }
    public void resign(String auth) {
        connections.remove(auth);
    }

    public Game move(String auth, Integer gameID, Move move) throws Exception {
        //Server verifies the validity of the move.
        Game game = new GameDAO().find(gameID).getGame();
        game.makeMove(move);
        if(game.isInCheck(game.getTeamTurn())) {
            if(game.isInCheckmate(game.getTeamTurn())) {
                Notification notification = new Notification();
                notification.message = "Player is in Checkmate";
                notifyAll(auth, notification);
            } else if (game.isInStalemate(game.getTeamTurn())) {
                Notification notification = new Notification();
                notification.message = "Player is in Stalemate";
                notifyAll(auth, notification);
            } else {
                Notification notification = new Notification();
                notification.message = "Player is in check";
                notifyAll(auth, notification);
            }
        } else if (game.isInStalemate(game.getTeamTurn())) {
            Notification notification = new Notification();
            notification.message = "Player is in Stalemate";
            notifyAll(auth, notification);
        }
        //Game is updated to represent the move. Game is updated in the database.
        new GameDAO().update(gameID, game);
        return game;
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
