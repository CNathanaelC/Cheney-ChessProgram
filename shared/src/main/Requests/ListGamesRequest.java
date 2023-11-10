package Requests;

import Model.AuthToken;
import com.google.gson.annotations.SerializedName;

/** The parameter of the service classes that provides the parameters to perform it
 *
 */
public class ListGamesRequest {
    @SerializedName("Authorization")
    /** the authToken needed to execute the associated request for a user */
    private AuthToken authToken;

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
