package Model;

import Model.User;

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
    /** the String representation of the AuthToken's associated username */
    private String username;

    /** Creates a unique identifier for the User
     *
     * @param user the user to be associated with the AuthToken to be generated
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
