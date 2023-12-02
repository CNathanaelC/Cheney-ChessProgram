package Server.webSocket;

import Model.AuthToken;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;
import java.io.IOException;

public class WSHandler {
    ConnectionContainer connections = new ConnectionContainer();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand cmd = new Gson().fromJson(message, UserGameCommand.class);
        switch (cmd.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(cmd.getAuthString(), session);
            case JOIN_OBSERVER -> joinObserver(cmd.getAuthString(), session);
            case MAKE_MOVE -> makeMove(cmd.getAuthString(), session);
            case LEAVE -> leave(cmd.getAuthString(), session);
            case RESIGN -> resign(cmd.getAuthString(), session);
        }
    }
    public void joinPlayer(String auth, Session session) throws IOException {
        connections.add(auth, session);
        AuthToken at = new AuthToken();
        at.setAuthToken(auth);
        var message = String.format("%s is in the shop", new AuthDAO().getUsername(at));
        LoadGame loadGame = new LoadGame(ServerMessage.ServerMessageType.LOAD_GAME);
        connections.broadcast(auth, loadGame);
        Notification notification = new Notification(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(auth, notification);
    }
    public void joinObserver(String auth, Session session) throws IOException {

    }
    public void makeMove(String auth, Session session) throws IOException {

    }
    public void leave(String auth, Session session) throws IOException {
        connections.remove(auth);
        AuthToken at = new AuthToken();
        at.setAuthToken(auth);
        String message = String.format("% has left", new AuthDAO().getUsername(at));
        ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(auth, notification);
    }
    public void resign(String auth, Session session) throws IOException {
        AuthToken at = new AuthToken();
        at.setAuthToken(auth);
        String message = String.format("% has forfeit", new AuthDAO().getUsername(at));
        ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        connections.broadcast(auth, notification);
    }
}
