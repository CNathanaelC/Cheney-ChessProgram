import org.junit.jupiter.api.*;
import Model.*;
import ui.ChessClient;
import ui.ServerFacade;

public class ClientTests {
    private static ChessClient client;

    @BeforeEach
    public void setup() {
         client = new ChessClient();
         try {
             client.destroy("samplePassword");
             client.register("LukeSky", "P0werCable$", "bluemilk@tatooine.com");
         } catch (Exception e) {

         }
    }
    @Test
    @Order(1)
    @DisplayName("Register Success")
    public void register() {
        Assertions.assertDoesNotThrow(() -> client.register("DarthVader", "Ih@teS4nd", "skyvader@darkside.org"), "Error was thrown");
    }
    @Test
    @Order(2)
    @DisplayName("login Success")
    public void login() {
        Assertions.assertDoesNotThrow(() -> client.login("LukeSky", "P0werCable$"), "Error was thrown");
    }
    @Test
    @Order(3)
    @DisplayName("Logout Success")
    public void logout() {
        Assertions.assertDoesNotThrow(() -> client.logout(), "Error was thrown");
    }
    @Test
    @Order(4)
    @DisplayName("List Games Success")
    public void list() {
        Assertions.assertDoesNotThrow(() -> client.listGames(), "Error was thrown");
    }

}
