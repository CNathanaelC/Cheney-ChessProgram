package dataAccess;

import Model.AuthToken;
import Model.User;

import java.util.HashMap;
import java.util.Map;

/** Retrieves and stores data regarding AuthTokens
 *
 */
public class AuthDAO {
    /** Stores all the current Authentication Tokens of the server */
    private static Map<String, AuthToken> allAuths = new HashMap<>();
    public Map<String, AuthToken> getAllAuths() {
        return allAuths;
    }

    /** Creates an Instance of AuthDAO */
    public AuthDAO() {

    }
    /** Create authorization code in the database
     *
     */
    public void createAuth(AuthToken authToken, User user) throws DataAccessException{
        authToken.createUniqueAuthToken();
        allAuths.put(user.getUserUsername(), authToken);
    }

    /** Clear: A method for clearing all AuthTokens from the database
     *
     * @throws DataAccessException if data access fails
     */
    public void clear() throws DataAccessException{

    }
}