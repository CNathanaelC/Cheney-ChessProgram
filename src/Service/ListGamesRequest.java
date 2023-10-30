package Service;

import Model.AuthToken;

/** The parameter of the service classes that provides the parameters to perform it
 *
 */
public class ListGamesRequest {
    /** the authToken needed to execute the associated request for a user */
    private AuthToken authToken = null;

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
