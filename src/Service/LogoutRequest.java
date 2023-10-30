package Service;

import Model.AuthToken;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/** The parameter of the service classes that indicates the specific operation desired as well as the parameters and AuthToken needed to perform it
 *
 */
public class LogoutRequest {
    @SerializedName("authorization")
    /** the authToken needed to execute the associated request for a user */
    private AuthToken authToken = new AuthToken();

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
