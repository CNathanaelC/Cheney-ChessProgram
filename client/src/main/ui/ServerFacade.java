package ui;

import Model.GameData;
import chess.*;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import spark.Request;
import webSocketMessages.serverMessages.*;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.WebSocketContainer;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessPiece.PieceType.*;
import static chess.ChessPiece.PieceType.PAWN;
import static ui.EscapeSequences.*;

public class ServerFacade extends Endpoint {
    private static String userAuth;
    private Game game = new Game();
    private ChessGame.TeamColor color = null;
    private String serverURL = "http://localhost:8080";
    public javax.websocket.Session session;

    public MessageHandler messageHandler;
    public ServerFacade() {
//        game.official_board.resetBoard();
        try {
            URI uri = new URI("ws://localhost:8080/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);
            messageHandler = new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    ServerMessage msg = new Gson().fromJson(message, ServerMessage.class);
                    switch (msg.getServerMessageType()) {
                        case LOAD_GAME -> setGame(new Gson().fromJson(message, LoadGame.class).getGame());
                        case ERROR -> System.out.println(new Gson().fromJson(message, ErrorMessage.class).getErrorMessage());
                        case NOTIFICATION -> System.out.println(new Gson().fromJson(message, Notification.class).getMessage());
                    }
                }
            };
            this.session.addMessageHandler(messageHandler);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void send(String msg) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch(Exception e) {
            throw new ResponseException("Connection Send Failed");
        }
    }

    public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {
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
            boolean result = makeRequest("PUT", "/game", "{" +
                    "  \"playerColor\": \""+ playerColor.toUpperCase() +"\"," +
                    "  \"gameID\": " + gameID +
                    "}");
            JoinPlayer jp = new JoinPlayer(userAuth);
            jp.setGameID(gameID);
            if(playerColor.toUpperCase().equals("BLACK")) {
                jp.setPlayerColor(ChessGame.TeamColor.BLACK);
                color = ChessGame.TeamColor.BLACK;
            } else {
                jp.setPlayerColor(ChessGame.TeamColor.WHITE);
                color = ChessGame.TeamColor.WHITE;
            }
            send(new Gson().toJson(jp));
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Connection Failure");
            return false;
        }
    }

    public boolean joinObserver(int gameID) {
        try {
            boolean result = makeRequest("PUT", "/game", "{" +
                    "  \"gameID\": " + gameID +
                    "}");
            JoinObserver jo = new JoinObserver(userAuth);
            jo.setGameID(gameID);
            send(new Gson().toJson(jo));
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
    public boolean leaveGame(int gameID) {
        try {
            Leave l = new Leave(userAuth);
            l.setGameID(gameID);
            send(new Gson().toJson(l));
            game = null;
            color = null;
            return true;
        } catch(ResponseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean makeMove(int gameID, Move move) {
        try {
            MakeMove mm = new MakeMove(userAuth);
            mm.setGameID(gameID);
            mm.setMove(move);
            send(new Gson().toJson(mm));
            return true;
        } catch(ResponseException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean resign(int gameID) {
        try {
            Resign r = new Resign(userAuth);
            r.setGameID(gameID);
            send(new Gson().toJson(r));
            return true;
        } catch(ResponseException e) {
            System.out.println(e.getMessage());
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
            String message = "";
            try (Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8")) {
                message = scanner.nextLine();
            } catch (Exception e) {

            }
            if (responseCode == 200) {
                if(method.equals("POST") && (path.equals("/user") || path.equals("/session"))) {
                    userAuth = connection.getHeaderField("Authorization");
                }
                if(operation.equals("List Games")) {
                    //TODO::make the printed message look nicer
                    GameList gm = new Gson().fromJson(message, GameList.class);
                    System.out.println("Games:");
                    for(GameData gd : gm.getGames()) {
                        System.out.println(SET_TEXT_BOLD + gd.getGameName() + RESET_TEXT_BOLD_FAINT + "(id: " + gd.getGameID() + ")");
                        if(gd.getWhiteUsername() == null) {
                            System.out.println("White: Available");
                        } else {
                            System.out.println("White: " + gd.getWhiteUsername());
                        }
                        if (gd.getBlackUsername() == null) {
                            System.out.println("Black: Available");
                        } else {
                            System.out.println("Black: " + gd.getBlackUsername());
                        }
                        System.out.println();
                    }
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
    public void printChessBoard(String color) {
        Board board = (Board) getGame().getBoard();
        boolean b = true;
        System.out.print(SET_TEXT_COLOR_BLACK);
        if(color.equals("WHITE")) {
            System.out.println(SET_BG_COLOR_GREEN + "    a  b  c  d  e  f  g  h    " + RESET_BG_COLOR);
            for(int r = 7; r > -1; r--) {
                r++;
                System.out.print(SET_BG_COLOR_GREEN + " " + r + " " + RESET_BG_COLOR);
                r--;
                for(int c = 0; c < 8; c++) {
                    Piece piece = new Piece();
                    Position pos = new Position();
                    pos.setColumn(c+1);
                    pos.setRow(r+1);
                    piece = (Piece)board.getPiece(pos);
                    if(piece != null) {
                        if(piece.getPieceType() == KING) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " K ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " k ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " K ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " k ");
                                }
                            }
                        } else if(piece.getPieceType() == QUEEN) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " Q ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " q ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " Q ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " q ");
                                }
                            }
                        } else if(piece.getPieceType() == BISHOP) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " B ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " b ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " B ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " b ");
                                }
                            }
                        } else if(piece.getPieceType() == KNIGHT) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " N ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " n ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " N ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " n ");
                                }
                            }
                        } else if(piece.getPieceType() == ROOK) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " R ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " r ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " R ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " r ");
                                }
                            }
                        } else if (piece.getPieceType() == PAWN) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " P ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " p ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " P ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " p ");
                                }
                            }
                        }
                    } else {
                        if(b){
                            b = !b;
                            System.out.print(SET_BG_COLOR_MAGENTA + "   ");
                        } else {
                            b = !b;
                            System.out.print(SET_BG_COLOR_BLUE + "   ");
                        }
                    }
                }
                r++;
                System.out.println(SET_BG_COLOR_GREEN + " " + r + " " + RESET_BG_COLOR);
                r--;
                b = !b;
            }
            System.out.println(SET_BG_COLOR_GREEN + "    a  b  c  d  e  f  g  h    " + RESET_BG_COLOR);
            System.out.println(RESET_BG_COLOR + RESET_TEXT_COLOR);
        }
        else {
            System.out.println(SET_BG_COLOR_GREEN + "    h  g  f  e  d  c  b  a    " + RESET_BG_COLOR);
            for(int r = 0; r < 8; r++) {
                r++;
                System.out.print(SET_BG_COLOR_GREEN + " " + r + " " + RESET_BG_COLOR);
                r--;
                for(int c = 7; c > -1; c--) {
                    Piece piece = new Piece();
                    Position pos = new Position();
                    pos.setColumn(c+1);
                    pos.setRow(r+1);
                    piece = (Piece)board.getPiece(pos);
                    if(piece != null) {
                        if(piece.getPieceType() == KING) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " K ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " k ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " K ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " k ");
                                }
                            }
                        } else if(piece.getPieceType() == QUEEN) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " Q ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " q ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " Q ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " q ");
                                }
                            }
                        } else if(piece.getPieceType() == BISHOP) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " B ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " b ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " B ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " b ");
                                }
                            }
                        } else if(piece.getPieceType() == KNIGHT) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " N ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " n ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " N ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " n ");
                                }
                            }
                        } else if(piece.getPieceType() == ROOK) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " R ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " r ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " R ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " r ");
                                }
                            }
                        } else if (piece.getPieceType() == PAWN) {
                            if(b){
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " P ");
                                } else {
                                    System.out.print(SET_BG_COLOR_MAGENTA + " p ");
                                }
                            } else {
                                b = !b;
                                if(piece.getTeamColor() != BLACK) {
                                    System.out.print(SET_BG_COLOR_BLUE + " P ");
                                } else {
                                    System.out.print(SET_BG_COLOR_BLUE + " p ");
                                }
                            }
                        }
                    } else {
                        if(b){
                            b = !b;
                            System.out.print(SET_BG_COLOR_MAGENTA + "   ");
                        } else {
                            b = !b;
                            System.out.print(SET_BG_COLOR_BLUE + "   ");
                        }
                    }
                }
                r++;
                System.out.println(SET_BG_COLOR_GREEN + " " + r + " " + RESET_BG_COLOR);
                r--;
                b = !b;
            }
            System.out.println(SET_BG_COLOR_GREEN + "    h  g  f  e  d  c  b  a    " + RESET_BG_COLOR);
            System.out.println(RESET_BG_COLOR + RESET_TEXT_COLOR);
        }
    }
}
