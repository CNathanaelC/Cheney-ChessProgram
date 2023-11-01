package dataAccess;

import Model.AuthToken;
import Model.User;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

/** Retrieves and stores data regarding AuthTokens
 *
 */
public class AuthDAO {
    /** Stores all the current Authentication Tokens of the server */
    private static Map<String, String> allAuths = new HashMap<>();
    public Map<String, String> getAllAuths() {
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
        final int bf = allAuths.size();
        allAuths.put(authToken.getAuthToken(), user.getUserUsername());
        if(bf == allAuths.size()) {
            throw new DataAccessException("\"{ \"message\": \"Error: AuthToken never created\" }\"");
        }
    }

    /** Clear: A method for clearing all AuthTokens from the database
     *
     * @throws DataAccessException if data access fails
     */
    public void clear() throws DataAccessException{
        allAuths.clear();
        if(allAuths.size() != 0) {
            throw new DataAccessException("{ \"message\": \"Error: AuthTokens not cleared\" }");
        }
    }
    /** deletes the authToken to logout a user
     *
     */
    public void logout(AuthToken authToken) throws DataAccessException {
        final int bef = allAuths.size();
        for(String a : allAuths.keySet()) {
            if(a.equals(authToken.getAuthToken())) {
                allAuths.remove(a);
            }
        }
        if(bef == allAuths.size()) {
            throw new DataAccessException("{ \"message\": \"Error: unauthorized\" }");
        }
    }

    /** method to validate the authtoken exists
     *
     * @param authToken
     * @return
     * @throws DataAccessException
     */
    public boolean validate(AuthToken authToken) throws DataAccessException {
        for(String a : allAuths.keySet()) {
            if(a.equals(authToken.getAuthToken())) {
                return true;
            }
        }
        throw new DataAccessException("{ \"message\": \"Error: unauthorized\" }");
    }
    public String getUsername(AuthToken authToken) {
        return allAuths.get(authToken.getAuthToken());
    }
}