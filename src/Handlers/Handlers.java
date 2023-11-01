package Handlers;

import Model.AuthToken;
import com.google.gson.stream.JsonReader;
import dataAccess.AuthDAO;
import spark.Request;
import spark.Response;
import Service.*;
import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Handlers {
    public static Object RegisterHandler(Request request, Response response) {
        Gson r = new Gson();
        Service.RegisterRequest req = r.fromJson(request.body(), Service.RegisterRequest.class);
        Services.RegisterService service = new Services.RegisterService();
        RegisterResult result = service.registerNewUser(req);
        response.status(result.getResponseCode());
        response.body(result.getMessage());
        return result.getMessage();
    }
    public static Object ClearApplicationHandler(Request request, Response response) {
        Gson r = new Gson();
        Service.ClearApplicationRequest req =  r.fromJson(request.body(), ClearApplicationRequest.class);
        Services.DestroyDataService service = new Services.DestroyDataService();
        ClearApplicationResult result = service.clearApp(req);
        response.status(result.getResponseCode());
        response.body(result.getMessage());
        return result.getMessage();
    }
    public static Object LoginHandler(Request request, Response response) {
        Gson r = new Gson();
        Service.LoginRequest req = r.fromJson(request.body(), LoginRequest.class);
        Services.LoginService service = new Services.LoginService();
        LoginResult result = service.loginUser(req);
        response.status(result.getResponseCode());
        response.body(result.getMessage());
        return result.getMessage();
    }
    public static Object LogoutHandler(Request request, Response response) {
        Gson r = new Gson();
        Service.LogoutRequest req = new LogoutRequest();
        AuthToken a = new AuthToken();
        a.setAuthToken(request.headers("Authorization"));
        req.setAuthToken(a);
        Services.LoginService service = new Services.LoginService();
        LogoutResult result = service.logoutUser(req);
        response.status(result.getResponseCode());
        response.body(result.getMessage());
        return result.getMessage();
    }
    public static Object ListGamesHandler(Request request, Response response) {
        Gson r = new Gson();
        Service.ListGamesRequest req = new ListGamesRequest();
        AuthToken a = new AuthToken();
        a.setAuthToken(request.headers("Authorization"));
        req.setAuthToken(a);
        Services.JoinGameService service = new Services.JoinGameService();
        ListGamesResult result = service.getGamesList(req);
        response.status(result.getResponseCode());
        response.body(result.getMessage());
//        Gson json = new Gson();
        return result.getMessage();
    }
    public static Object CreateGameHandler(Request request, Response response) {
        Gson r = new Gson();
        Service.CreateGameRequest req = r.fromJson(request.body(), CreateGameRequest.class);
        AuthToken a = new AuthToken();
        a.setAuthToken(request.headers("Authorization"));
        req.setAuthToken(a);
        Services.JoinGameService service = new Services.JoinGameService();
        CreateGameResult result = service.createGame(req);
        response.status(result.getResponseCode());
        response.body(result.getMessage());
        return result.getMessage();
    }
    public static Object JoinGameHandler(Request request, Response response) {
        Gson r = new Gson();
        Service.JoinGameRequest req = r.fromJson(request.body(), JoinGameRequest.class);
        AuthToken a = new AuthToken();
        a.setAuthToken(request.headers("Authorization"));
        req.setAuthToken(a);
        Services.JoinGameService service = new Services.JoinGameService();
        JoinGameResult result = service.joinGame(req);
        response.status(result.getResponseCode());
        response.body(result.getMessage());
        return result.getMessage();
    }

}
