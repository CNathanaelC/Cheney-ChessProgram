package dataAccess;

import Model.AuthToken;
import Model.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/** Retrieves and stores data regarding AuthTokens
 *
 */
public class AuthDAO {
    /** Stores all the current Authentication Tokens of the server */
//    private static Map<String, String> allAuths = new HashMap<>();
    public Map<String, String> getAllAuths() {
        Database db = new Database();
        Map<String, String> auths = new HashMap<>();
        try (Connection connection = db.getConnection()) {
            String selectAuths = "SELECT * FROM allAuths";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectAuths)) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String authToken = resultSet.getString("authToken");
                    auths.put(authToken, username);
                }
            }
            db.closeConnection(connection);
        } catch (DataAccessException | SQLException e) {
//            throw new DataAccessException("{ \"message\": \"Error: user could not be inserted into the database\" }");
        }
        return auths;
//        return allAuths;
    }

    /** Creates an Instance of AuthDAO */
    public AuthDAO() {

    }

    /** Create authorization code in the database
     *
     * @param authToken
     * @param user
     * @throws DataAccessException
     */
    public void createAuth(AuthToken authToken, User user) throws DataAccessException{
        authToken.createUniqueAuthToken();
        final int bf = getAllAuths().size();
        Database db = new Database();
        try (Connection connection = db.getConnection()) {
            String insertUser = "INSERT INTO allAuths (authToken, username) VALUES (?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertUser)) {
                preparedStatement.setString(1, authToken.getAuthToken());
                preparedStatement.setString(2, user.getUserUsername());
                preparedStatement.executeUpdate();
            }
            db.closeConnection(connection);
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException("{ \"message\": \"Error: user could not be inserted into the database\" }");
        }
//        allAuths.put(authToken.getAuthToken(), user.getUserUsername());
        if(bf == getAllAuths().size()) {
            throw new DataAccessException("\"{ \"message\": \"Error: AuthToken never created\" }\"");
        }
    }

    /** Clear: A method for clearing all AuthTokens from the database
     *
     * @throws DataAccessException if data access fails
     */
    public void clear() throws DataAccessException{
        Database db = new Database();
        try (Connection connection = db.getConnection()) {
            String deleteAuths = "DELETE FROM allAuths";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteAuths)) {
                preparedStatement.executeUpdate();
            }
            db.closeConnection(connection);
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException("{ \"message\": \"Error: authTokens could not be cleared from the database\" }");
        }
//        allAuths.clear();
        if(getAllAuths().size() != 0) {
            throw new DataAccessException("{ \"message\": \"Error: AuthTokens not cleared\" }");
        }
    }

    /** deletes the authToken to logout a user
     *
     * @param authToken
     * @throws DataAccessException
     */
    public void logout(AuthToken authToken) throws DataAccessException {
        final int bef = getAllAuths().size();
        Database db = new Database();
        try (Connection connection = db.getConnection()) {
            String deleteAuths = "DELETE FROM allAuths WHERE authToken = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteAuths)) {
                preparedStatement.setString(1, authToken.getAuthToken());
                preparedStatement.executeUpdate();
            }
            db.closeConnection(connection);
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException("{ \"message\": \"Error: authToken associated with user could not be cleared from the database\" }");
        }
//        for(String a : getAllAuths().keySet()) {
//            if(a.equals(authToken.getAuthToken())) {
//                allAuths.remove(a);
//            }
//        }
        if(bef == getAllAuths().size()) {
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
        for(String a : getAllAuths().keySet()) {
            if(a.equals(authToken.getAuthToken())) {
                return true;
            }
        }
        throw new DataAccessException("{ \"message\": \"Error: unauthorized\" }");
    }
    public String getUsername(AuthToken authToken) {
        Database db = new Database();
        Map<String, String> auths = new HashMap<>();
        try (Connection connection = db.getConnection()) {
            String selectAuths = "SELECT username FROM allAuths WHERE authToken = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectAuths)) {
                preparedStatement.setString(1, authToken.getAuthToken());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {

                        return resultSet.getString("username");
                    } else {
                        return null;
                    }
                }
            }
        } catch (DataAccessException | SQLException e) {
//            throw new DataAccessException("{ \"message\": \"Error: user could not be inserted into the database\" }");
        }
        return null;
    }

    public Boolean check(String username) {
        for(String u : getAllAuths().values()) {
            if(username.equals(u)) {
                return true;
            }
        }
        return false;
    }
    public String getAuth(String username) {
        for(String u : getAllAuths().keySet()) {
            if(username.equals(getAllAuths().get(u))) {
                return u;
            }
        }
        return "";
    }
}