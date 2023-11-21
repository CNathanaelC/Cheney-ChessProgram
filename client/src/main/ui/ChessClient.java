package ui;

import java.io.IOException;
import java.util.Arrays;
import static ui.EscapeSequences.*;

public class ChessClient {
    public boolean sessionLogin = false;
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
            return "create <NAME> - to create a new game\n" +
                    "list - to list all of the games available to join\n" +
                    "play <ID> <[WHITE|BLACK|<empty>]> - to join a game\n" +
                    "observe <ID> - to observe a game\n" +
                    "logout - to stop playing chess\n" +
                    "quit - to quit playing chess\n" +
                    "help - to display possible commands\n";
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
            //FIXME::have it actually print out the game list
            return "/Gamelist/";
        } else {
            throw new ResponseException(" Failure");
        }
    }
    public String joinGame(String...params) throws ResponseException {
        if(params.length == 2) {
            server.joinPlayer(params[0], Integer.parseInt(params[1]));
            printChessBoard();
            return "Game " + params[0] + " was successfully joined as the " + params[1].toLowerCase() + " player.\n";
        } else if (params.length == 1) {
            return joinObserver(params);
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String joinObserver(String...params) throws ResponseException {
        if(params.length == 1) {
            server.joinObserver(Integer.parseInt(params[0]));
            return "Game " + params[0] + " was successfully joined as an observer.\n";
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public void printChessBoard() {

    }
    public void destroy(String p) throws ResponseException {
        if(p.equals("samplePassword")) {
            server.clear();
        } else {
            throw new ResponseException("You really thought that would work didn't you");
        }
    }
}
