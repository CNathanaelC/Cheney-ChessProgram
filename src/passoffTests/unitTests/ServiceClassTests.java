package passoffTests.unitTests;
import Service.Services.*;
import Service.*;
import chess.Game;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import org.junit.jupiter.api.*;
import Model.*;

public class ServiceClassTests {
    private User sampleUser1;
    private User sampleUser2;
    private User sampleUser3;
    private AuthToken authToken1;
    private AuthToken authToken2;
    private AuthToken badAuthToken;
    private GameData sampleGame;

    @BeforeEach
    public void setup() {
        UserDAO ud = new UserDAO();
        AuthDAO ad = new AuthDAO();
        GameDAO gd = new GameDAO();
        try {
            ad.clear();
            ud.clear();
            gd.clear();
        } catch (DataAccessException e) {
            System.out.println("Something in the clears sucks");
        }
        sampleUser1 = new User();
        sampleUser1.register("DarthVader", "Ih@teS4nd", "skyvader@darkside.org");
        authToken1 = new AuthToken();
        authToken1.createUniqueAuthToken();
        authToken2 = new AuthToken();
        authToken2.createUniqueAuthToken();
        badAuthToken = new AuthToken();
        badAuthToken.createUniqueAuthToken();
        sampleUser2 = new User();
        sampleUser2.register("LukeSky", "P0werCable$", "bluemilk@tatooine.com");
        sampleUser3 = new User();
        sampleUser3.register("Palps", "JustD3wIt!", "notfromajedi@darkside.org");
        sampleGame = new GameData(1000);
        sampleGame.setBlackUsername("DarthVader");
        sampleGame.setGameName("Now, I am the Master");
        try {
            ud.createUser(sampleUser1);
            ud.createUser(sampleUser2);
            ud.createUser(sampleUser3);
        } catch (DataAccessException e) {
            System.out.println("Something in UserDAO sucks");
        }
        try {
            ad.createAuth(authToken1, sampleUser1);
            ad.createAuth(authToken2, sampleUser3);
        } catch (DataAccessException e) {
            System.out.println("Something in AuthDAOcreateAuth sucks");
        }
        try {
            gd.insert(sampleGame);
        } catch (DataAccessException e) {
            System.out.println("Something in GameDAO sucks");
        }
    }
    @Test
    @Order(1)
    @DisplayName("Login Success")
    public void login() {
        LoginService service = new LoginService();
        LoginRequest request = new LoginRequest();
        request.setUsername("LukeSky");
        request.setPassword("P0werCable$");
        LoginResult result = service.loginUser(request);
        Assertions.assertEquals(200, result.getResponseCode(), "Response code was not 200");
        AuthDAO ad = new AuthDAO();
        Assertions.assertTrue(ad.check("LukeSky"), "Username did not receive authToken");
    }
    @Test
    @Order(2)
    @DisplayName("Logout Success")
    public void logout() {
        LoginService service = new LoginService();
        LogoutRequest request = new LogoutRequest();
        request.setAuthToken(authToken1);
        LogoutResult result = service.logoutUser(request);
        Assertions.assertEquals(200, result.getResponseCode(), "Response code was not 200");
        AuthDAO ad = new AuthDAO();
        Assertions.assertFalse(ad.check("DarthVader"), "User's authToken was not deleted");
    }
    @Test
    @Order(3)
    @DisplayName("Register Success")
    public void register() {
        RegisterService service = new RegisterService();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("YodaBomb");
        request.setPassword("gr33nFr0g");
        request.setEmail("theforceismyally@dagobah.io");
        RegisterResult result = service.registerNewUser(request);
        Assertions.assertEquals(200, result.getResponseCode(), "Response code was not 200");
        UserDAO ud = new UserDAO();
        Assertions.assertTrue(ud.check("YodaBomb"),"User was never added");
    }
    @Test
    @Order(4)
    @DisplayName("Get Game List Success")
    public void gamesList() {
        JoinGameService service = new JoinGameService();
        ListGamesRequest request = new ListGamesRequest();
        request.setAuthToken(authToken1);
        ListGamesResult result = service.getGamesList(request);
        Assertions.assertEquals(200, result.getResponseCode(), "Response code was not 200");
        Assertions.assertEquals((String)"{ \"games\": [{\"gameID\": 1000, \"blackUsername\":\"DarthVader\", \"gameName\":\"Now, I am the Master\"}]}", (String)result.getMessage(),"Expected Message was not generated");
    }
    @Test
    @Order(5)
    @DisplayName("Create Game Success")
    public void creategame() {
        JoinGameService service = new JoinGameService();
        CreateGameRequest request = new CreateGameRequest();
        GameData game = new GameData(1001);
        request.setAuthToken(authToken1);
        request.setGameName("Cloud City Trap");
        game.setGameName("Cloud City Trap");
        CreateGameResult result = service.createGame(request);
        GameDAO gd = new GameDAO();
        Assertions.assertEquals(200, result.getResponseCode(), "Response code was not 200");
        Assertions.assertEquals("Cloud City Trap", gd.getAllGames().get(1001).getGameName(), "GameName does not match");
        Assertions.assertEquals(null, gd.getAllGames().get(1001).getWhiteUsername(), "White username does not match");
        Assertions.assertEquals(null, gd.getAllGames().get(1001).getBlackUsername(), "Black username does not match");
    }
    @Test
    @Order(6)
    @DisplayName("Join Game Success")
    public void joingame() {
        JoinGameService service = new JoinGameService();
        JoinGameRequest request = new JoinGameRequest();
        GameDAO gd = new GameDAO();
        request.setAuthToken(authToken2);
        request.setPlayerColor("WHITE");
        request.setGameID(1000);
        JoinGameResult result = service.joinGame(request);
        Assertions.assertEquals(200, result.getResponseCode(), "Response code was not 200");
        Assertions.assertEquals("Now, I am the Master", gd.getAllGames().get(1000).getGameName(), "GameName does not match");
        Assertions.assertEquals("Palps", gd.getAllGames().get(1000).getWhiteUsername(), "White username does not match");
        Assertions.assertEquals("DarthVader", gd.getAllGames().get(1000).getBlackUsername(), "Black username does not match");
    }
    @Test
    @Order(7)
    @DisplayName("Clear Success")
    public void clear() {
        DestroyDataService service = new DestroyDataService();
        ClearApplicationRequest request = new ClearApplicationRequest();
        ClearApplicationResult result = service.clearApp(request);
        Assertions.assertEquals(200, result.getResponseCode(), "Response code was not 200");
        Assertions.assertNull(new GameDAO().getAllGames().get(sampleGame.getGameID()), "Games were not cleared");
        Assertions.assertFalse(new UserDAO().check(sampleUser1.getUserUsername()), "Users were not cleared");
        Assertions.assertFalse(new AuthDAO().check(authToken1.getAuthToken()), "AuthTokens were not cleared");
    }
    @Test
    @Order(8)
    @DisplayName("Login Failure")
    public void badLogin() {
        LoginService service = new LoginService();
        LoginRequest request = new LoginRequest();
        request.setUsername("LukeSky");
        request.setPassword("P0werCables");
        LoginResult result = service.loginUser(request);
        Assertions.assertEquals(401, result.getResponseCode(), "Response code was not 401");
    }
    @Test
    @Order(9)
    @DisplayName("Logout Failure")
    public void badLogout() {
        LoginService service = new LoginService();
        LogoutRequest request = new LogoutRequest();
        request.setAuthToken(badAuthToken);
        LogoutResult result = service.logoutUser(request);
        Assertions.assertEquals(401, result.getResponseCode(), "Response code was not 401");
    }
    @Test
    @Order(10)
    @DisplayName("Register Failure")
    public void badRegister() {
        RegisterService service = new RegisterService();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("DarthVader");
        request.setPassword("BOOlukeface");
        request.setEmail("imnotadarksideghost@dagobah.io");
        RegisterResult result = service.registerNewUser(request);
        Assertions.assertEquals(403, result.getResponseCode(), "Response code was not 403");
    }
    @Test
    @Order(11)
    @DisplayName("Get Game List Failure")
    public void badGameList() {
        JoinGameService service = new JoinGameService();
        ListGamesRequest request = new ListGamesRequest();
        request.setAuthToken(badAuthToken);
        ListGamesResult result = service.getGamesList(request);
        Assertions.assertEquals(401, result.getResponseCode(), "Response code was not 401");
    }
    @Test
    @Order(12)
    @DisplayName("Create Game Failure")
    public void badCreateGame() {
        JoinGameService service = new JoinGameService();
        CreateGameRequest request = new CreateGameRequest();
        request.setAuthToken(badAuthToken);
        request.setGameName("Cloud City Trap");
        CreateGameResult result = service.createGame(request);
        Assertions.assertEquals(401, result.getResponseCode(), "Response code was not 401");
    }
    @Test
    @Order(13)
    @DisplayName("Join Game Failure")
    public void badjoingame() {
        JoinGameService service = new JoinGameService();
        JoinGameRequest request = new JoinGameRequest();
        request.setAuthToken(badAuthToken);
        request.setPlayerColor("WHITE");
        request.setGameID(sampleGame.getGameID());
        JoinGameResult result = service.joinGame(request);
        Assertions.assertEquals(401, result.getResponseCode(), "Response code was not 401");
    }
}
