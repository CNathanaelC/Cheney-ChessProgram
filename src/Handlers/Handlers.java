package Handlers;

import spark.Request;
import spark.Response;
import Service.*;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
//call the services
//what do the request and response contain are they JSON?
// is the decoding of the JSON that is the reason why there should be specific Request and result classes
//how exactly does curl factor into this
//does Services need to contain a class variable for each of the DAOs so that it is actually changed
//I think that my design has more flaws than previously thought
public class Handlers {
    public static Object RegisterHandler(Request request, Response response) {
        return null;
    }
    public static Object ClearApplicationHandler(Request request, Response response) {
        Gson r = new Gson();
        System.out.println(r.toJson(request));
        Service.ClearApplicationRequest req = new Service.ClearApplicationRequest();
        req = r.fromJson(String.valueOf(r), ClearApplicationRequest.class);
        Services.DestroyDataService service = new Services.DestroyDataService();
        service.clearApp(req);
        ClearApplicationResult result = service.clearApp(req);
        Gson s = new Gson();
        response.status(result.getResponseCode());
        return s.toJson(result.getMessage());
    }
    public static Object LoginHandler(Request request, Response response) {
        return null;
    }
    public static Object LogoutHandler(Request request, Response response) {
        return null;
    }
    public static Object ListGamesHandler(Request request, Response response) {
        return null;
    }
    public static Object CreateGameHandler(Request request, Response response) {
        return null;
    }
    public static Object JoinGameHandler(Request request, Response response) {
        return null;
    }

}
