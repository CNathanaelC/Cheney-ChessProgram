package ui;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import org.eclipse.jetty.websocket.api.Session;
import spark.Request;

public class ServerFacade {
    private Session session;
    private static String userAuth;
    public String output;
    private String userName;
    private String serverURL = "http://localhost:8080";

    public ServerFacade() {

    }

    public boolean register(String username, String password, String email) {
        try {
            return makeRequest("POST", "/user", "{" +
                    "  \"username\": \""+ username +"\"," +
                    "  \"password\": \"" + password + "\"," +
                    "  \"email\": \""+ email +"\"" +
                    "}");
        } catch (IOException e) {
            System.out.println("Connection Failure");
            return false;
        }
    }

    public boolean login(String username, String password) {
        try {
            return makeRequest("POST", "/session", "{" +
                    "  \"username\": \""+ username +"\"," +
                    "  \"password\": \"" + password + "\"" +
                    "}");

        } catch (IOException e) {
            System.out.println("Connection Failure");
            return false;
        }
    }

    public boolean logout() {
        try {
            return makeRequest("DELETE", "/session");
        } catch (IOException e) {
            System.out.println("Connection Failure");
            return false;
        }
    }

    public boolean listGames() {
        try {
            return makeRequest("GET", "/game");
        } catch (IOException e) {
            System.out.println("Connection Failure");
            return false;
        }
    }

    public boolean createGame(String gameName) {
        try {
            return makeRequest("POST", "/game", "{" +
                    "  \"gameName\": \""+ gameName +"\"" +
                    "}");
        } catch (IOException e) {
            System.out.println("Connection Failure");
            return false;
        }
    }

    public boolean joinPlayer(String playerColor, int gameID) {
        try {
            return makeRequest("PUT", "/game", "{" +
                    "  \"playerColor\": \""+ playerColor +"\"," +
                    "  \"gameID\": " + gameID +
                    "}");
        } catch (IOException e) {
            System.out.println("Connection Failure");
            return false;
        }
    }

    public boolean joinObserver(int gameID) {
        try {
            return makeRequest("PUT", "/game", "{" +
                    "  \"gameID\": " + gameID +
                    "}");
        } catch (IOException e) {
            System.out.println("Connection Failure");
            return false;
        }
    }

    public boolean clear() {
        try {
            return makeRequest("DELETE", "/db");
        } catch (IOException e) {
            System.out.println("Connection Failure");
            return false;
        }
    }

    private boolean makeRequest(String method, String path, String... body) throws IOException {
        String operation = "";
        if(method.equals("POST")) {
            if(path.equals("/user")) {
                operation = "Register";
            } else if(path.equals("/session")) {
                operation = "Login";
            } else if(path.equals("/game")) {
                operation = "Create Game";
            } else {
                operation = "Invalid: " + method + path;
            }
        } else if (method.equals("PUT")) {
            if(path.equals("/game")) {
                operation = "Join Game";
            } else {
                operation = "Invalid: " + method + path;
            }
        } else if (method.equals("GET")) {
            if(path.equals("/game")) {
                operation = "List Games";
            } else {
                operation = "Invalid: " + method + path;
            }
        } else if (method.equals("DELETE")) {
            if(path.equals("/session")) {
                operation = "Logout";
            } else if(path.equals("/db")) {
                operation = "Clear";
            } else {
                operation = "Invalid: " + method + path;
            }
        } else {
            operation = "Invalid: " + method + path;
        }
        boolean returnVal = false;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(serverURL + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.addRequestProperty("Accept", "application/json");
            connection.addRequestProperty("Authorization", userAuth);
            connection.connect();

            if(body.length >= 1) {
                connection.getOutputStream().write(body[0].getBytes("UTF-8"));
            }
            int responseCode = connection.getResponseCode();
            String message;
            try (Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8")) {
                message = scanner.nextLine();
            }
            if (responseCode == 200) {
                if(method.equals("POST") && (path.equals("/user") || path.equals("/session"))) {
                    userAuth = connection.getHeaderField("Authorization");
                }
                if(operation.equals("List Games")) {
                    System.out.println(message);
                }
                System.out.println(operation + " Operation Success");
                returnVal = true;
            } else {
                System.out.println(operation + " Operation Failure: " + message);
                returnVal = false;
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            return returnVal;
        }
    }
}
