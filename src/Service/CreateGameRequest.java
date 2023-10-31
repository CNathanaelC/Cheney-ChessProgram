package Service;

import Model.AuthToken;
import com.google.gson.annotations.SerializedName;

/** The parameter of the service classes that indicates the specific operation desired as well as the parameters and AuthToken needed to perform it
 *
 */
public class CreateGameRequest {
    @SerializedName("Authorization")
    /** the authToken needed to execute the associated request for a user */
    private AuthToken authToken;
    @SerializedName("gameName")
    /** the name of a game assigned by the user */
    private String gameName;

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
