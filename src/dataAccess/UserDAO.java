package dataAccess;

import Model.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/** Retrieves and stores data regarding Users
 *
 */
public class UserDAO {
    /** Stores all the current users of the server */
    private static Map<String, User> allUsers = new HashMap<>();
    public Map<String, User> getAllUsers() {
        Database db = new Database();
        Map<String, User> users = new HashMap<>();
        try (Connection connection = db.getConnection()) {
            String selectUsers = "SELECT * FROM allUsers";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectUsers)) {

                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");
                    User user = new User();
                    user.register(username, password, email);
                    users.put(username, user);
                }
            }
        } catch (DataAccessException | SQLException e) {
//            throw new DataAccessException("{ \"message\": \"Error: user could not be inserted into the database\" }");
        }
        return users;
//        return allUsers;
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
            Database db = new Database();
            try (Connection connection = db.getConnection()) {
                String insertGameData = "INSERT INTO allUsers (username, password, email) VALUES (?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertGameData)) {
                    preparedStatement.setString(1, user.getUserUsername());
                    preparedStatement.setString(2, user.getUserPassword());
                    preparedStatement.setString(3, user.getUserEmail());
                    preparedStatement.executeUpdate();
                }
            } catch (DataAccessException | SQLException e) {
                throw new DataAccessException("{ \"message\": \"Error: user could not be inserted into the database\" }");
            }
            allUsers.put(user.getUserUsername(), user);
        }
    }


    //TODO::needs to access database
    /** Clear: A method for clearing all users from the database
     *
     * @throws DataAccessException if data access fails
     */
    //TODO::needs to access database
    public void clear() throws DataAccessException {
        allUsers.clear();
        if(getAllUsers().size() != 0) {
            throw new DataAccessException("{ \"message\": \"Error: AuthTokens not cleared\" }");
        }
    }

    /** a method to find if a user with a specific username exists in a database
     *
     * @param username the name of a user
     * @return the user if found
     */
    public User find(String username) {
        if(getAllUsers().containsKey(username)) {
            return getAllUsers().get(username);
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
        if(getAllUsers().get(username).getUserPassword().equals(password)) {
            return true;
        } else {
            throw new DataAccessException("{ \"message\": \"Error: unauthorized\" }");
        }
    }
    public Boolean check(String username) {
        if(getAllUsers().containsKey(username)) {
            return true;
        } else {
            return false;
        }
    }
}
