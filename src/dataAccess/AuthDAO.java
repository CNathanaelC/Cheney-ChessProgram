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
        int bf = allAuths.size();
        allAuths.put(user.getUserUsername(), authToken);
    }

    /** Clear: A method for clearing all AuthTokens from the database
     *
     * @throws DataAccessException if data access fails
     */
    public void clear() throws DataAccessException{
        allAuths.clear();
//        if(allAuths.size() != 0) {
//            throw new DataAccessException("{ \"message\": \"Error: AuthTokens not cleared\" }");
//        }
    }
    /** deletes the authToken to logout a user
     *
     */
    public void logout(AuthToken authToken) throws DataAccessException {
        int bef = allAuths.size();
        for(Map.Entry<String, AuthToken> a : allAuths.entrySet()) {
            if(a.getValue().getAuthToken().equals(authToken.getAuthToken())) {
                allAuths.remove(a.getKey());
            }
        }
        if(bef == allAuths.size()) {
            throw new DataAccessException("{ \"message\": \"Error: User's AuthToken was never deleted\" }");
        }

    }
}