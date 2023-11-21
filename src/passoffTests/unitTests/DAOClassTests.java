package passoffTests.unitTests;

import Model.AuthToken;
import Model.GameData;
import Model.User;
import chess.Game;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import org.junit.jupiter.api.*;

import java.util.*;

public class DAOClassTests {
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
    @DisplayName("Get All Users Success")
    public void getUsers() {
        List<User> test = new ArrayList<>();
        test.add(sampleUser1);
        test.add(sampleUser3);
        test.add(sampleUser2);
        Collection <User> users = new UserDAO().getAllUsers().values();
        int i = 0;
        Assertions.assertTrue(test.size() == users.size(), "List sizes were not the same");
        for(User u : users) {
            Assertions.assertEquals(u.getUserUsername(), test.get(i).getUserUsername(), "Username does not match expected");
            Assertions.assertEquals(u.getUserPassword(), test.get(i).getUserPassword(), "Password does not match expected");
            Assertions.assertEquals(u.getUserEmail(), test.get(i).getUserEmail(), "Email does not match expected");
            i++;
        }
    }
    @Test
    @Order(2)
    @DisplayName("Create User Success")
    public void createUser() {
        UserDAO ud = new UserDAO();
        User u = new User();
        u.register("YodaBomb","gr33nFr0g","theforceismyally@dagobah.io");
        final int bf = ud.getAllUsers().size();
        Assertions.assertDoesNotThrow(() -> ud.createUser(u));
        Assertions.assertNotEquals(bf, ud.getAllUsers().size(), "Number of users did not change");
    }
    @Test
    @Order(3)
    @DisplayName("Find User Success")
    public void findUser() {
        Assertions.assertNotNull(new UserDAO().find("DarthVader"), "User was not found");
    }
    @Test
    @Order(4)
    @DisplayName("Validate User Success")
    public void validateUser() {
        Assertions.assertDoesNotThrow(() ->new UserDAO().validate("DarthVader", "Ih@teS4nd"), "Validation failed");
    }
    @Test
    @Order(5)
    @DisplayName("Check User Success")
    public void checkUser() {
        Assertions.assertTrue(new UserDAO().check("DarthVader"), "Check failed to find username in database");
    }
    @Test
    @Order(6)
    @DisplayName("Clear Users Success")
    public void clearUsers() {
        Assertions.assertDoesNotThrow(() ->new UserDAO().clear(), "Clear failed");
    }
    @Test
    @Order(7)
    @DisplayName("Get All AuthToken Success")
    public void getAuths() {
        Map<String, String> test = new HashMap<>();
        test.put(authToken1.getAuthToken(), sampleUser1.getUserUsername());
        test.put(authToken2.getAuthToken(), sampleUser3.getUserUsername());
        Assertions.assertEquals(test, new AuthDAO().getAllAuths(), "User list does not match expected");
    }
    @Test
    @Order(8)
    @DisplayName("Create AuthToken Success")
    public void createAuth() {
        Assertions.assertDoesNotThrow(() -> new AuthDAO().createAuth(new AuthToken(), sampleUser2));
    }
    @Test
    @Order(9)
    @DisplayName("Find Username from AuthToken Success")
    public void getUsernameFromAuth() {
        Assertions.assertEquals("DarthVader", new AuthDAO().getUsername(authToken1), "Username found does not match expected");
    }
    @Test
    @Order(10)
    @DisplayName("Validate AuthToken Success")
    public void validateAuth() {
        Assertions.assertDoesNotThrow(() -> new AuthDAO().validate(authToken1), "Validation failed");
    }
    @Test
    @Order(11)
    @DisplayName("Check AuthToken Success")
    public void checkAuth() {
        Assertions.assertTrue(new AuthDAO().check("DarthVader"), "AuthToken apparently doesn't exist");
    }
    @Test
    @Order(12)
    @DisplayName("Logout (Delete AuthToken) Success")
    public void logoutAuth() {
        Assertions.assertDoesNotThrow(() -> new AuthDAO().logout(authToken1), "Logout failed");
    }
    @Test
    @Order(13)
    @DisplayName("Clear AuthToken Success")
    public void clearAuths() {
        Assertions.assertDoesNotThrow(() -> new AuthDAO().clear(), "Clear all Auths failed");
    }
    @Test
    @Order(14)
    @DisplayName("Get All Games Success")
    public void getGames() {
        List<GameData> test = new ArrayList<>();
        test.add(sampleGame);
        Collection<GameData> games = new GameDAO().getAllGames().values();
        Assertions.assertTrue(test.size() == games.size(), "List size does not match expected");
        int i = 0;
        for(GameData g : games) {
            Assertions.assertEquals(test.get(i).getGameID(), g.getGameID(), "GameID does not match expected");
            Assertions.assertEquals(test.get(i).getGameName(), g.getGameName(), "Game Name does not match expected");
            Assertions.assertEquals(test.get(i).getWhiteUsername(), g.getWhiteUsername(), "White Username does not match expected");
            Assertions.assertEquals(test.get(i).getBlackUsername(), g.getBlackUsername(), "Black Username does not match expected");
            i++;
        }
    }
    @Test
    @Order(15)
    @DisplayName("Create(Insert) Game Success")
    public void createGame() {
        GameData gd = new GameData(1002);
        gd.setGameName("Example Game");
        Assertions.assertDoesNotThrow(() -> new GameDAO().insert(gd));
    }
    @Test
    @Order(16)
    @DisplayName("Update Game Success")
    public void updateGame() {
        Assertions.assertDoesNotThrow(() -> new GameDAO().update(sampleGame.getGameID(), new Game()));
    }
    @Test
    @Order(17)
    @DisplayName("Find Game Success")
    public void findGame() {
        Assertions.assertDoesNotThrow(() -> new GameDAO().find(sampleGame.getGameID()));
    }
    @Test
    @Order(18)
    @DisplayName("Find All Games Success")
    public void findAllGames() {
        List<GameData> test = new ArrayList<>();
        test.add(sampleGame);
        List<GameData> games = new ArrayList<>();
        try {
            games = new GameDAO().findAll();
        } catch (DataAccessException e) {
            Assertions.assertTrue(false, "Exception was thrown");
        }
        Assertions.assertTrue(test.size() == games.size(), "List size does not match expected");
        for(int i = 0; i < test.size(); i++) {
            Assertions.assertEquals(test.get(i).getGameID(), games.get(i).getGameID(), "GameID does not match expected");
            Assertions.assertEquals(test.get(i).getGameName(), games.get(i).getGameName(), "Game Name does not match expected");
            Assertions.assertEquals(test.get(i).getWhiteUsername(), games.get(i).getWhiteUsername(), "White Username does not match expected");
            Assertions.assertEquals(test.get(i).getBlackUsername(), games.get(i).getBlackUsername(), "Black Username does not match expected");
        }
    }
    @Test
    @Order(19)
    @DisplayName("Claim Spot in Game Success")
    public void claimSpot() {
        Assertions.assertDoesNotThrow(() -> new GameDAO().claimSpot("Palps", "WHITE", sampleGame.getGameID()));
    }
    @Test
    @Order(20)
    @DisplayName("Clear Games Success")
    public void clearGames() {
        Assertions.assertDoesNotThrow(() -> new GameDAO().clear());
    }

    @Test
    @Order(21)
    @DisplayName("Create User Failure")
    public void badCreateUser() {
        UserDAO ud = new UserDAO();
        User u = new User();
        u.register("DarthVader","BOOlukeface","imnotadarksideghost@dagobah.io");
        final int bf = ud.getAllUsers().size();
        Assertions.assertThrows(DataAccessException.class, () -> ud.createUser(u));
        Assertions.assertEquals(bf, ud.getAllUsers().size(), "Number of users did change");
    }
    @Test
    @Order(22)
    @DisplayName("Find User Failure")
    public void badFindUser() {
        Assertions.assertNull(new UserDAO().find("AnakinSky"), "User was found");
    }
    @Test
    @Order(23)
    @DisplayName("Validate User Failure")
    public void badValidateUser() {
        Assertions.assertThrows(DataAccessException.class, () -> new UserDAO().validate("DarthVader", "Il0veS4nd"), "Validation succeeded");
    }
    @Test
    @Order(24)
    @DisplayName("Check User Failure")
    public void badCheckUser() {
        Assertions.assertFalse(new UserDAO().check("AnakinSky"), "Check found username in database");
    }
    @Test
    @Order(26)
    @DisplayName("Find Username from AuthToken Failure")
    public void badGetUsernameFromAuth() {
        Assertions.assertNull(new AuthDAO().getUsername(badAuthToken), "Username was found");
    }
    @Test
    @Order(27)
    @DisplayName("Validate AuthToken Failure")
    public void badValidateAuth() {
        Assertions.assertThrows(DataAccessException.class, () -> new AuthDAO().validate(badAuthToken), "Validation succeeded");
    }
    @Test
    @Order(28)
    @DisplayName("Check AuthToken Failure")
    public void badCheckAuth() {
        Assertions.assertFalse(new AuthDAO().check("AnakinSky"), "AuthToken apparently exists");
    }
    @Test
    @Order(29)
    @DisplayName("Logout (Delete AuthToken) Failure")
    public void badLogoutAuth() {
        Assertions.assertDoesNotThrow(() -> new AuthDAO().logout(authToken1), "Logout failed");
        Assertions.assertThrows(DataAccessException.class, () -> new AuthDAO().logout(authToken1), "Logout succeeded");
    }
    @Test
    @Order(30)
    @DisplayName("Create(Insert) Game Failure")
    public void badCreateGame() {
        GameData gd = new GameData(sampleGame.getGameID());
        gd.setGameName("Example Game");
        Assertions.assertThrows(DataAccessException.class, () -> new GameDAO().insert(gd));
    }
    @Test
    @Order(31)
    @DisplayName("Update Game Failure")    public void badUpdateGame() {
        Assertions.assertThrows(DataAccessException.class, () -> new GameDAO().update(999, new Game()));
    }
    @Test
    @Order(32)
    @DisplayName("Find Game Failure")
    public void badFindGame() {
        Assertions.assertThrows(DataAccessException.class, () -> new GameDAO().find(999));
    }
    @Test
    @Order(33)
    @DisplayName("Claim Spot in Game Failure")
    public void badClaimSpot() {
        Assertions.assertThrows(DataAccessException.class, () -> new GameDAO().claimSpot("Palps", "BLACK", sampleGame.getGameID()));
    }
}
