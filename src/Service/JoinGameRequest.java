package Service;

import Model.AuthToken;
import chess.ChessGame;
import com.google.gson.annotations.SerializedName;
/** The parameter of the service classes that indicates the specific operation desired as well as the parameters and AuthToken needed to perform it
 *
 */
public class JoinGameRequest {
    /** the color that the user desires to play as */
    @SerializedName("playerColor")
    private String playerColor;
    /** the gameID of the game the player wants to join */
    @SerializedName("gameID")
    private int gameID;
    @SerializedName("Authorization")
    private AuthToken authToken;

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
