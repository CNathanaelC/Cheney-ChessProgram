package Service;
import Model.AuthToken;
import Model.GameData;
import Model.User;
import dataAccess.*;
//call the DAOs
/** Class that contains all of the different types of Service classes */
public class Services {
    /** Creates an instance of Services */
    public Services() {

    }
    /** Manages functions regarding logging in */
    public static class LoginService {
        /** Creates an instance of the LoginService class
         *
         */
        public LoginService() {

        }
        /** Logs in an existing user (returns a new authToken).
         *
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public LoginResult loginUser(LoginRequest request) {
            LoginResult lr = new LoginResult();
            try {
                UserDAO ud = new UserDAO();
                AuthDAO ad = new AuthDAO();
                User u = ud.find(request.getUsername());
                if(u != null) {
                    ud.validate(u.getUserUsername(), request.getPassword());
                } else {
                    lr.setResponseCode(401);
                    lr.setMessage("{ \"message\": \"Error: unauthorized\" }");
                    return lr;
                }
                AuthToken a = new AuthToken();
                ad.createAuth(a, u);
                lr.setResponseCode(200);
                lr.setMessage("{ \"username\":\"" + u.getUserUsername() + "\", \"authToken\":\"" + a.getAuthToken() + "\" }");
            } catch (DataAccessException e) {
                lr.setMessage(e.getMessage());
                if(e.getMessage() == "{ \"message\": \"Error: unauthorized\" }") {
                    lr.setResponseCode(401);
                } else {
                    lr.setResponseCode(500);
                }
            }
            return lr;
        }

        /** Logs out the user represented by the authToken.
         *
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public LogoutResult logoutUser(LogoutRequest request) {
            LogoutResult lr = new LogoutResult();
            try {
                AuthToken a = request.getAuthToken();
                AuthDAO ad = new AuthDAO();
                ad.logout(a);
                lr.setResponseCode(200);
                lr.setMessage("{}");
            } catch(DataAccessException e) {
                lr.setMessage(e.getMessage());
                if(e.getMessage() == "{ \"message\": \"Error: unauthorized\" }") {
                    lr.setResponseCode(401);
                } else {
                    lr.setResponseCode(500);
                }
            }
            return lr;
        }
    }
    /** Manages functions regarding registering */
    public static class RegisterService {
        /** Creates an instance of the RegisterService class */
        public RegisterService() {

        }
        /** Register a new user.
         *
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public RegisterResult registerNewUser(RegisterRequest request) {
            RegisterResult rr = new RegisterResult();
            try {
                UserDAO ud = new UserDAO();
                AuthDAO ad = new AuthDAO();
                User u = new User();
                AuthToken a = new AuthToken();
                a.createUniqueAuthToken();
                u.register(request.getUsername(), request.getPassword(), request.getEmail());
                ud.createUser(u);
                rr.setResponseCode(200);
                rr.setMessage("{ \"username\":\"" + u.getUserUsername() + "\", \"authToken\":\"" + a.getAuthToken() + "\" }");
            } catch (DataAccessException e) {
                rr.setMessage(e.getMessage());
                if(e.getMessage() == "{ \"message\": \"Error: bad request\" }") {
                    rr.setResponseCode(400);
                } else if(e.getMessage() == "{ \"message\": \"Error: already taken\" }") {
                    rr.setResponseCode(403);
                } else {
                    rr.setResponseCode(500);
                }
            }

            return rr;
        }
    }
    /** Manages functions regarding joining a game */
    public static class JoinGameService {
        /** Creates an instance of the JoinGameService class */
        public JoinGameService() {

        }
        /** Gives a list of all games.
         *
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public ListGamesResult getGamesList(ListGamesRequest request) {
            ListGamesResult lgr = new ListGamesResult();
            try {
                StringBuilder messageToBe = new StringBuilder();
                messageToBe.append("{ \"games\": [{");
                GameDAO gd = new GameDAO();

                for(GameData g : gd.findAll()) {
                    messageToBe.append("\"gameID\": "+g.getGameID()+", ");
                    messageToBe.append("\"whiteUsername\":\"");
                    if(g.getWhiteUsername() != null) {
                        messageToBe.append(g.getWhiteUsername()+"\", ");
                    } else {
                        messageToBe.append("null"+"\", ");
                    }
                    messageToBe.append("\"blackUsername\":\"");
                    if(g.getBlackUsername() != null) {
                        messageToBe.append(g.getBlackUsername()+"\", ");
                    } else {
                        messageToBe.append("null"+"\", ");
                    }
                    messageToBe.append("\"gameName:\""+ g.getGameName() +"\"} \n");
                }
                messageToBe.append("]}");
                lgr.setMessage(messageToBe.toString());
                lgr.setResponseCode(200);
            } catch(DataAccessException e) {
                lgr.setMessage(e.getMessage());
                if(e.getMessage() == "{ \"message\": \"Error: unauthorized\" }") {
                    lgr.setResponseCode(401);
                } else {
                    lgr.setResponseCode(500);
                }

            }
            return lgr;
        }

        /** Creates a new game
         *
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public CreateGameResult createGame(Request request) {
            return null;
        }
        /** Verifies that the specified game exists, and,
         * if a color is specified, adds the caller as the requested color to the game.
         * If no color is specified the user is joined as an observer.
         * This request is idempotent.
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public JoinGameResult joinGame(Request request) {
            return null;
        }

        /** exits a gamed that a user has joined canceling the game
         *
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public ExitGameResult exitGame(Request request) {
            return null;
        }
    }
    /** Manages functions regarding deleting data of various forms both incompletely and completely */
    public static class DestroyDataService {
        /** Creates an instance of the DestroyDataService class */
        public DestroyDataService() {

        }
        /** Clears the database. Removes all users, games, and authTokens
         *
         * @param request  contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public ClearApplicationResult clearApp(ClearApplicationRequest request) {
            ClearApplicationResult car = new ClearApplicationResult();
            try {
                UserDAO ud = new UserDAO();
                ud.clear();
                AuthDAO ad = new AuthDAO();
                ad.clear();
                GameDAO gd = new GameDAO();
                gd.clear();
                car.setResponseCode(200);
                car.setMessage("{}");
            } catch (DataAccessException e) {
                car.setResponseCode(500);
                car.setMessage(e.getMessage());
            }
            return car;
        }

        /** Removes a user
         *
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public DeleteUserResult deleteUser(Request request) { return  null;}
        /** Removes a Game
         *
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public Result deleteGame(Request request) { return null;}
        /** Removes an Authentication Token
         *
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public Result deleteAuthToken(Request request) { return  null;}
    }
}
