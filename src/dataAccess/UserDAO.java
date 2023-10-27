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
        } else {
            allUsers.put(user.getUserUsername(), user);
        }
    }



    /** Clear: A method for clearing all users from the database
     *
     * @throws DataAccessException if data access fails
     */
    public void clear() throws DataAccessException {

    }

    /** a method to find if a user with a specific username exists in a database
     *
     * @param username the name of a user
     * @return the user if found and null if not found
     */
    public User find(String username) {
        if(allUsers.containsKey(username)) {
            return allUsers.get(username);
        } else {
            return null;
        }
    }
}
