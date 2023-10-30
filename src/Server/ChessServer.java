package Server;

import spark.Spark;
import Handlers.*;
/*TODO:: nothing for chess server
   for handlers make them take the spark request object and make it into a GSON and from GSON
   into a specific request by which I can call the associated service
   make specific request objects and make the result objects more specific to the API
   split the data access objects, the static map objects should be contained in each DAO
   the service classes should call the associated DAOs to store the objects
   */
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
