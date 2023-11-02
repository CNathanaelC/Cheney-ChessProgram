package dataAccess;

import Model.User;

import java.util.HashMap;
import java.util.Map;

/** Retrieves and stores data regarding Users
 *
 */
public class UserDAO {
    /** Stores all the current users of the server */
    private static Map<String, User> allUsers = new HashMap<>();
    public Map<String, User> getAllUsers() {
        return allUsers;
    }
    /** Creates an Instance of UserDAO */
    public UserDAO() {

    }

    /** A method for creating a new user in the database
     *
     * @param user the proposed User and details
     * @throws DataAccessException if it is a bad request, the username is taken, or other error
     */
    public void createUser(User user) throws DataAccessException{
        if(find(user.getUserUsername()) != null) {
            throw new DataAccessException("{ \"message\": \"Error: already taken\" }");
        } else if(user.getUserPassword() == null || user.getUserPassword() == null || user.getUserEmail() == null) {
            throw new DataAccessException("{ \"message\": \"Error: bad request\" }");
        } else {
            allUsers.put(user.getUserUsername(), user);
        }
    }



    /** Clear: A method for clearing all users from the database
     *
     * @throws DataAccessException if data access fails
     */
    public void clear() throws DataAccessException {
        allUsers.clear();
        if(allUsers.size() != 0) {
            throw new DataAccessException("{ \"message\": \"Error: AuthTokens not cleared\" }");
        }
    }

    /** a method to find if a user with a specific username exists in a database
     *
     * @param username the name of a user
     * @return the user if found
     */
    public User find(String username) {
        if(allUsers.containsKey(username)) {
            return allUsers.get(username);
        } else {
            return null;
        }
    }

    /** a method to validate that the password provided matches the
     *
     * @param username the proposed username
     * @param password the password that may correspond to the username
     * @return true if the password matches the user in the database
     * @throws DataAccessException if the username does not match the password
     */
    public boolean validate(String username, String password) throws DataAccessException {
        if(allUsers.get(username).getUserPassword().equals(password)) {
            return true;
        } else {
            throw new DataAccessException("{ \"message\": \"Error: unauthorized\" }");
        }
    }
    public Boolean check(String username) {
        if(allUsers.containsKey(username)) {
            return true;
        } else {
            return false;
        }
    }
}
