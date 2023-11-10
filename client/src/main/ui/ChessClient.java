package ui;

import java.util.Arrays;
import static ui.EscapeSequences.*;

public class ChessClient {
    public boolean sessionLogin = false;
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
            //login operation
            sessionLogin = true;
            return "User was successfully logged in as " + params[0] + ".\n";
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String logout() throws ResponseException {
        //logout operation
        sessionLogin = false;
        return "User was successfully logged out\n";
    }
    public String register(String...params) throws ResponseException {
        if(params.length == 3) {
            //register operation
            sessionLogin = true;
            return "User was successfully registered and logged in as " + params[0] + ".\n";
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String createGame(String...params) throws ResponseException {
        if(params.length == 1) {
            //create operation
            return "Game was successfully created\n";
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public String listGames() throws ResponseException {
        //list game operation and return string edit
        return "ID: 1000, Name: Duel of the Fates, Black: available, White: unavailable\n";
    }
    public String joinGame(String...params) throws ResponseException {
        if(params.length == 2) {
            //join operation
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
            //join operation
            return "Game " + params[0] + " was successfully joined as an observer.\n";
        } else {
            throw new ResponseException("Incorrect number of elements\n");
        }
    }
    public void printChessBoard() {

    }
}
