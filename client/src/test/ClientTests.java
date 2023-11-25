import org.junit.jupiter.api.*;
import Model.*;
import ui.ChessClient;
import ui.ResponseException;
import ui.ServerFacade;

public class ClientTests {
    private static ChessClient client;

    @BeforeEach
    public void setup() {
         client = new ChessClient();
         try {
             client.destroy("samplePassword");
             client.register("LukeSky", "P0werCable$", "bluemilk@tatooine.com");
             client.createGame("The Force Cave");
             client.joinGame("WHITE", "1000");
             client.logout();
             client.register("DarthVader", "Ih@teS4nd", "skyvader@darkside.org");
         } catch (Exception e) {

         }
    }
    @Test
    @Order(0)
    @DisplayName("Destroy Success")
    public void destroy() {
        Assertions.assertDoesNotThrow(() -> client.destroy("samplePassword"), "Error was thrown");
    }
    @Test
    @Order(1)
    @DisplayName("Register Success")
    public void register() {
        Assertions.assertDoesNotThrow(() -> client.register("Palps", "JustD3wIt!", "notfromajedi@darkside.org"), "Error was thrown");
    }
    @Test
    @Order(2)
    @DisplayName("Login Success")
    public void login() {
        Assertions.assertDoesNotThrow(() -> client.login("LukeSky", "P0werCable$"), "Error was thrown");
    }
    @Test
    @Order(3)
    @DisplayName("List Games Success")
    public void list() {
        Assertions.assertDoesNotThrow(() -> client.listGames(), "Error was thrown");
    }
    @Order(4)
    @DisplayName("Logout Success")
    public void logout() {
        Assertions.assertDoesNotThrow(() -> client.logout(), "Error was thrown");
    }
    @Test
    @Order(5)
    @DisplayName("Create Game Success")
    public void create() {
        Assertions.assertDoesNotThrow(() -> client.createGame("Trap at Cloud City"), "Error was thrown");
    }
    @Test
    @Order(6)
    @DisplayName("Join as Player Success")
    public void play() {
        Assertions.assertDoesNotThrow(() -> client.joinGame("BLACK", "1000"), "Error was thrown");
    }
    @Test
    @Order(6)
    @DisplayName("Join as Observer Success")
    public void observe() {
        Assertions.assertDoesNotThrow(() -> client.joinObserver("1000"), "Error was thrown");
    }
    @Test
    @Order(7)
    @DisplayName("Register Failure")
    public void badRegister() {
        Assertions.assertThrows(Exception.class, () -> client.register("LukeSky", "P0werCable$", "bluemilk@tatooine.com"), "Error was not thrown");
    }
    @Test
    @Order(8)
    @DisplayName("Login Failure")
    public void badLogin() {
        Assertions.assertThrows(Exception.class, () -> client.login("LukeSky", "P0werCables"), "Error was not thrown");
    }
    @Test
    @Order(9)
    @DisplayName("Logout Failure")
    public void badLogout() {
        Assertions.assertDoesNotThrow(() -> client.logout(), "Error was thrown");
        Assertions.assertThrows(Exception.class, () -> client.logout(), "Error was not thrown");
    }
    @Test
    @Order(10)
    @DisplayName("List Games Failure")
    public void badList() {
        Assertions.assertDoesNotThrow(() -> client.logout(), "Error was thrown");
        Assertions.assertThrows(Exception.class, () -> client.listGames(), "Error was not thrown");
    }
    @Test
    @Order(11)
    @DisplayName("Create Game Failure")
    public void badCreate() {
        Assertions.assertDoesNotThrow(() -> client.logout(), "Error was thrown");
        Assertions.assertThrows(Exception.class,() -> client.createGame("Trap at Cloud City"), "Error was not thrown");
    }
    @Test
    @Order(12)
    @DisplayName("Join as Player Failure")
    public void badPlay() {
        Assertions.assertThrows(ResponseException.class, () -> client.joinGame("WHITE", "1000"), "Error was not thrown");
    }
    @Test
    @Order(13)
    @DisplayName("Join as Observer Failure")
    public void badObserve() {
        Assertions.assertThrows(ResponseException.class, () -> client.joinObserver("2000"), "Error was not thrown");
    }
}
