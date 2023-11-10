package Model;

import java.util.UUID;

/** Model class to contain elements of a Authentication Token
 *
 */
public class AuthToken {
    /** creates an instance of AuthToken ready to customized to a user */
    public AuthToken() {

    }
    /** the String representation of the Authentication Token */
    private String authToken;

    /** Creates a unique identifier for the User
     *
     */

    public void createUniqueAuthToken() {
        UUID id = UUID.randomUUID();
        authToken = id.toString();
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
