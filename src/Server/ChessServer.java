package Server;

import spark.Spark;
import Handlers.*;

public class ChessServer {
    public static void main(String[] args) {
        new ChessServer().run();

    }
    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);
        // Register a directory for hosting static files
        Spark.externalStaticFileLocation("web");
        // Register handlers for each endpoint using the method reference syntax
        Spark.delete("/db", Handlers::ClearApplicationHandler);
        Spark.post("/user", Handlers::RegisterHandler);
        Spark.post("/session", Handlers::LoginHandler);
        Spark.delete("/session", Handlers::LogoutHandler);
        Spark.get("/game", Handlers::ListGamesHandler);
        Spark.post("/game", Handlers::CreateGameHandler);
        Spark.put("/game", Handlers::JoinGameHandler);
    }
}
