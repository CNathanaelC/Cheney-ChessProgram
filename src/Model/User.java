package Model;

import java.util.UUID;

/** Model class to contain elements of a user
 *
 */
public class User {
    /** Creates an instance of User class that is ready to be customized according to a user */
    public User() {

    }
    /** the String representation of the User's username and how they will be recognized by the system */
    private String userUsername;
    /** the String representation of the User's password that will allow them to access this User again */
    private String userPassword;
    /** the String representation of the User's email that is associated with the account */
    private String userEmail;
    /** the User's associated AuthToken that will authorize actions taken during the session under the User's username */
    private AuthToken userAuthToken;

    /** set all the values of the User class except for the AuthToken which is done elsewhere
     *
     * @param username
     * @param password
     * @param email
     */
    public void register(String username, String password, String email) {
        userUsername = username;
        userPassword = password;
        userEmail = email;
    }



    public String getUserUsername() {
        return userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
