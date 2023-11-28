import org.junit.jupiter.api.*;
import ui.ChessClient;
import ui.ResponseException;
import ui.ServerFacade;

import java.rmi.ServerError;

public class FacadeTests {
    private static ServerFacade server;

    @BeforeEach
    public void setup() {
        server = new ServerFacade();
        server.clear();
        server.register("LukeSky", "P0werCable$", "bluemilk@tatooine.com");
        server.createGame("The Force Cave");
        server.joinPlayer("WHITE", 1000);
        server.logout();
        server.register("DarthVader", "Ih@teS4nd", "skyvader@darkside.org");
    }
    @AfterAll
    public static void end() {
        server.clear();
    }
    @Test
    @Order(1)
    @DisplayName("Clear Success")
    public void destroy() {
        Assertions.assertTrue(server.clear());
        Assertions.assertFalse(server.listGames());
    }
    @Test
    @Order(2)
    @DisplayName("Register Success")
    public void register() {
        Assertions.assertTrue(server.register("Palps", "JustD3wIt!", "notfromajedi@darkside.org"));
        Assertions.assertTrue(server.listGames());
    }
    @Test
    @Order(3)
    @DisplayName("Login Success")
    public void login() {
        Assertions.assertTrue(server.login("LukeSky", "P0werCable$"));
        Assertions.assertTrue(server.listGames());
    }
    @Test
    @Order(4)
    @DisplayName("List Games Success")
    public void list() {
        Assertions.assertTrue(server.listGames());
    }
    @Test
    @Order(5)
    @DisplayName("Logout Success")
    public void logout() {
        Assertions.assertTrue(server.logout());
        Assertions.assertFalse(server.listGames());
    }
    @Test
    @Order(6)
    @DisplayName("Create Game Success")
    public void create() {
        Assertions.assertTrue(server.createGame("Trap at Cloud City"));
        Assertions.assertTrue(server.joinObserver(1001));
    }
    @Test
    @Order(7)
    @DisplayName("Join as Player Success")
    public void play() {
        Assertions.assertTrue(server.joinPlayer("BLACK", 1000));
        Assertions.assertFalse(server.joinPlayer("BLACK", 1000));
    }
    @Test
    @Order(8)
    @DisplayName("Join as Observer Success")
    public void observe() {
        Assertions.assertTrue(server.joinObserver(1000));
    }
    @Test
    @Order(9)
    @DisplayName("Register Failure")
    public void badRegister() {
        server.logout();
        Assertions.assertFalse(server.register("LukeSky", "P0werCable$", "bluemilk@tatooine.com"));
        Assertions.assertFalse(server.listGames());
    }
    @Test
    @Order(10)
    @DisplayName("Login Failure")
    public void badLogin() {
        server.logout();
        Assertions.assertFalse(server.login("LukeSky", "P0werCables"));
        Assertions.assertFalse(server.listGames());
    }
    @Test
    @Order(11)
    @DisplayName("Logout Failure")
    public void badLogout() {
        server.logout();
        Assertions.assertFalse(server.logout());
        Assertions.assertFalse(server.listGames());
    }
    @Test
    @Order(12)
    @DisplayName("List Games Failure")
    public void badList() {
        server.logout();
        Assertions.assertFalse(server.listGames());
    }
    @Test
    @Order(13)
    @DisplayName("Create Game Failure")
    public void badCreate() {
        server.logout();
        Assertions.assertFalse(server.createGame("Trap at Cloud City"));
        Assertions.assertFalse(server.joinObserver(1001));
    }
    @Test
    @Order(14)
    @DisplayName("Join as Player Failure")
    public void badPlay() {
        Assertions.assertFalse(server.joinPlayer("WHITE", 1000));
    }
    @Test
    @Order(15)
    @DisplayName("Join as Observer Failure")
    public void badObserve() {
        server.logout();
        Assertions.assertFalse(server.joinObserver(1000));
    }
}
