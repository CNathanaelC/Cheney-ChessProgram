package Server.webSocket;

import Model.AuthToken;
import Model.GameData;
import chess.ChessGame;
import chess.Game;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.userCommands.*;
import java.io.IOException;
@WebSocket
public class WebSocketHandler {
    ConnectionContainer connections = new ConnectionContainer();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
        switch (cmd.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(new Gson().fromJson(message, JoinPlayer.class), session);
            case JOIN_OBSERVER -> joinObserver(new Gson().fromJson(message, JoinObserver.class), session);
            case MAKE_MOVE -> makeMove(new Gson().fromJson(message, MakeMove.class), session);
            case LEAVE -> leave(new Gson().fromJson(message, Leave.class), session);
            case RESIGN -> resign(new Gson().fromJson(message, Resign.class), session);
        }
    }
    public void joinPlayer(JoinPlayer cmd, Session session) throws IOException {
        try {
            try {
                try {
                    AuthToken at = new AuthToken();
                    at.setAuthToken(cmd.getAuthString());
                    new AuthDAO().validate(at);
                } catch(Exception e) {
                    String message = String.format("AuthToken Invalid");
                    ErrorMessage error = new ErrorMessage();
                    error.setErrorMessage(message);
                    connections.notifyRoot(cmd.getAuthString(), error);
                }
                GameData gameData = connections.play(cmd.getAuthString(), cmd.getGameID(), cmd.getPlayerColor(), session);
                AuthToken at = new AuthToken();
                at.setAuthToken(cmd.getAuthString());
                if(cmd.getPlayerColor() == ChessGame.TeamColor.WHITE) {
                    if(gameData.getWhiteUsername() == null) {
                        String message = String.format("Joining an Empty Team is Prohibited");
                        ErrorMessage error = new ErrorMessage();
                        error.setErrorMessage(message);
                        connections.notifyRoot(cmd.getAuthString(), error);
                    } else if(gameData.getWhiteUsername().equals(new AuthDAO().getUsername(at))) {
                        //Server sends a LOAD_GAME message back to the root client.
                        String message = String.format("User %s joined a game as a player", new AuthDAO().getUsername(at));
                        LoadGame loadGame = new LoadGame();
                        loadGame.setGame(gameData.getGame());
                        connections.notifyRoot(cmd.getAuthString(), loadGame);
                        //Server sends a Notification message to all other clients in that game informing them what color the root client is joining as.
                        Notification notification = new Notification();
                        notification.message = message;
                        connections.notifyOthers(cmd.getAuthString(), notification);
                    } else {
                        String message = String.format("White team already chosen");
                        ErrorMessage error = new ErrorMessage();
                        error.setErrorMessage(message);
                        connections.notifyRoot(cmd.getAuthString(), error);
                    }
                } else {
                    if (gameData.getBlackUsername() == null) {
                        String message = String.format("Joining an Empty Team is Prohibited");
                        ErrorMessage error = new ErrorMessage();
                        error.setErrorMessage(message);
                        connections.notifyRoot(cmd.getAuthString(), error);
                    } else if (gameData.getBlackUsername().equals(new AuthDAO().getUsername(at))) {
                        //Server sends a LOAD_GAME message back to the root client.
                        String message = String.format("User %s joined a game as a player", new AuthDAO().getUsername(at));
                        LoadGame loadGame = new LoadGame();
                        loadGame.setGame(gameData.getGame());
                        connections.notifyRoot(cmd.getAuthString(), loadGame);
                        //Server sends a Notification message to all other clients in that game informing them what color the root client is joining as.
                        Notification notification = new Notification();
                        notification.message = message;
                        connections.notifyOthers(cmd.getAuthString(), notification);
                    } else {
                        String message = String.format("Black team already chosen");
                        ErrorMessage error = new ErrorMessage();
                        error.setErrorMessage(message);
                        connections.notifyRoot(cmd.getAuthString(), error);
                    }
                }
            } catch (Exception e) {
                String message = String.format("GameID invalid");
                ErrorMessage error = new ErrorMessage();
                error.setErrorMessage(message);
                connections.notifyRoot(cmd.getAuthString(), error);
            }
        } catch(Exception e) {
            throw new IOException(e.getMessage());
        }
    }
    public void joinObserver(JoinObserver cmd, Session session) throws IOException {
        try {
            AuthToken at = new AuthToken();
            at.setAuthToken(cmd.getAuthString());
            new AuthDAO().validate(at);
        } catch(Exception e) {
            String message = String.format("AuthToken Invalid");
            ErrorMessage error = new ErrorMessage();
            error.setErrorMessage(message);
            connections.notifyRoot(cmd.getAuthString(), error);
        }
        try {
            Game game = connections.observe(cmd.getAuthString(), cmd.getGameID(), session);
            AuthToken at = new AuthToken();
            at.setAuthToken(cmd.getAuthString());
            //Server sends a LOAD_GAME message back to the root client.
            String message = String.format("User %s joined a game as an observer", new AuthDAO().getUsername(at));
            LoadGame loadGame = new LoadGame();
            loadGame.setGame(game);
            connections.notifyRoot(cmd.getAuthString(), loadGame);
            //Server sends a Notification message to all other clients in that game informing them the root client joined as an observer.
            Notification notification = new Notification();
            notification.message = message;
            connections.notifyOthers(cmd.getAuthString(), notification);
        } catch(Exception e) {
            String message = String.format("GameID invalid");
            ErrorMessage error = new ErrorMessage();
            error.setErrorMessage(message);
            connections.notifyRoot(cmd.getAuthString(), error);
        }
    }
    public void makeMove(MakeMove cmd, Session session) throws IOException {
        try {
            Game game = connections.move(cmd.getAuthString(), cmd.getGameID(), cmd.getMove());
            if (game != null) {
                AuthToken at = new AuthToken();
                at.setAuthToken(cmd.getAuthString());
                //Server sends a LOAD_GAME message to all clients in the game (including the root client) with an updated game.
                var message = String.format("Player %s made a move", new AuthDAO().getUsername(at));
                LoadGame loadGame = new LoadGame();
                loadGame.setGame(game);
                connections.notifyAll(cmd.getAuthString(), loadGame);
                //Server sends a Notification message to all other clients in that game informing them what move was made.
                Notification notification = new Notification();
                notification.message = message;
                connections.notifyOthers(cmd.getAuthString(), notification);
            }
        } catch(Exception e) {
            throw new IOException(e.getMessage());
        }
    }
    public void leave(Leave cmd, Session session) throws IOException {
        //If a player is leaving, then the game is updated to remove the root client. Game is updated in the database.
        //Server sends a Notification message to all other clients in that game informing them that the root client left. This applies to both players and observers.
        connections.leave(cmd.getAuthString());
        AuthToken at = new AuthToken();
        at.setAuthToken(cmd.getAuthString());
        String message = String.format("Player %s left the game", new AuthDAO().getUsername(at));
        Notification notification = new Notification();
        notification.message = message;
        connections.notifyOthers(cmd.getAuthString(), notification);
    }
    public void resign(Resign cmd, Session session) throws IOException {
        connections.resign(cmd.getAuthString(), cmd.getGameID());
    }
}
