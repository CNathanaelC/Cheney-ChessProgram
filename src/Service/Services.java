package Service;
import Model.AuthToken;
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
        public LoginResult loginUser(Request request) {
            return null;
        }

        /** Logs out the user represented by the authToken.
         *
         * @param request contains the needed objects, authentication code, and request associated code
         * @return a result that contains the associated code regarding failure or success and message dependent on the operation and success
         */
        public LogoutResult logoutUser(Request request) {
            return null;
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
        public ListGamesResult getGamesList(Request request) {
            return null;
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
                car.setMessage("");
            } catch (DataAccessException e) {
                car.setResponseCode(500);
                car.setMessage("{\"message\": \"Error: description\"}");
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
