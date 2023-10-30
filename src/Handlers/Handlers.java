package Handlers;

import Model.AuthToken;
import com.google.gson.stream.JsonReader;
import spark.Request;
import spark.Response;
import Service.*;
import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//call the services
//what do the request and response contain are they JSON?
// is the decoding of the JSON that is the reason why there should be specific Request and result classes
//how exactly does curl factor into this
//does Services need to contain a class variable for each of the DAOs so that it is actually changed
//I think that my design has more flaws than previously thought
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
        Service.LogoutRequest req = r.fromJson(request.body(), LogoutRequest.class);
        Services.LoginService service = new Services.LoginService();
        LogoutResult result = service.logoutUser(req);
        response.status(result.getResponseCode());
        response.body(result.getMessage());
        return result.getMessage();
    }
    public static Object ListGamesHandler(Request request, Response response) {
        Gson r = new Gson();
        Service.ListGamesRequest req = r.fromJson(request.body(), ListGamesRequest.class);
        AuthToken a = new AuthToken();
        a.setAuthToken(request.headers("authorization"));
        req.setAuthToken(a);
        Services.JoinGameService service = new Services.JoinGameService();
        ListGamesResult result = service.getGamesList(req);
        ListGamesRequest lgr = r.fromJson(request.body(), ListGamesRequest.class);
        return result.getMessage();
    }
    public static Object CreateGameHandler(Request request, Response response) {
        return null;
    }
    public static Object JoinGameHandler(Request request, Response response) {
        return null;
    }

}
