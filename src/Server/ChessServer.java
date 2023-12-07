package Server;

import Server.webSocket.WebSocketHandler;
import dataAccess.DataAccessException;
import dataAccess.Database;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.Session;
import spark.Spark;
import Handlers.*;

import java.sql.SQLException;

public class ChessServer {
    public static void main(String[] args) {
        new ChessServer().run();
    }
    private void run() {
        Spark.port(8080);
        Spark.externalStaticFileLocation("web");
        Database db = new Database();
        try {
            db.configureDatabase();
        } catch (SQLException e) {
            System.out.println("SQL failed");
            e.printStackTrace();
        }
        Spark.webSocket("/connect", WebSocketHandler.class);
        Spark.delete("/db", Handlers::ClearApplicationHandler);
        Spark.post("/user", Handlers::RegisterHandler);
        Spark.post("/session", Handlers::LoginHandler);
        Spark.delete("/session", Handlers::LogoutHandler);
        Spark.get("/game", Handlers::ListGamesHandler);
        Spark.post("/game", Handlers::CreateGameHandler);
        Spark.put("/game", Handlers::JoinGameHandler);
    }
}
