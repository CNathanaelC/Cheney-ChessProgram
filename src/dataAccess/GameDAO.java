package dataAccess;

import Model.GameData;
import chess.ChessGame;
import chess.Game;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Retrieves and stores data regarding Games
 *
 */
public class GameDAO {
    /** Stores all the current games of the server */
    private static Map<Integer, GameData> allGames = new HashMap<>();
    //TODO:: needs to access database
    public Map<Integer, GameData> getAllGames() {
        return allGames;
    }
    /** Creates an Instance of GameDAO*/
    public GameDAO() {

    }

    /** A method for inserting a new game into the database.
     *
     * @param game the game that is to be inserted into the database
     */
    public void insert(GameData game) throws DataAccessException {
        int bf = getAllGames().size();
        Database db = new Database();
        try (Connection connection = db.getConnection()) {
            String insertGameData = "INSERT INTO allGameData (id, name, blackUsername, whiteUsername, game) VALUES (?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertGameData)) {
                preparedStatement.setInt(1, game.getGameID());
                preparedStatement.setString(2, game.getGameName());
                preparedStatement.setString(3, game.getBlackUsername());
                preparedStatement.setString(4, game.getWhiteUsername());
                //TODO::replace with deserialized game string
                preparedStatement.setString(5, "JSON string");
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException("{ \"message\": \"Error: game data could not be inserted into the database\" }");
        }
        allGames.put(game.getGameID(), game);
        if(getAllGames().size() == bf) {
            throw new DataAccessException("{ \"message\": \"Error: unauthorized\" }");
        }
    }
    public void update(int gameID, Game game) throws DataAccessException{
        Database db = new Database();
        try (Connection connection = db.getConnection()) {
            String insertGameData = "UPDATE allGameData SET game = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertGameData)) {
                //TODO::replace with deserialized game string
                preparedStatement.setString(1, "JSON string");
                preparedStatement.setInt(2, gameID);
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException("{ \"message\": \"Error: game data could not be inserted into the database\" }");
        }
    }

    /** A method for retrieving a specified game from the database by gameID.
     *
     * @param gameID the number associated with the game to find
     * @throws DataAccessException if the access fails
     * @return the GameData for the game associated with the gameID
     */
    public GameData find(int gameID) throws DataAccessException {
        if(getAllGames().containsKey(gameID)) {
            return getAllGames().get(gameID);
        } else {
            throw new DataAccessException("{ \"message\": \"Error: bad request\" }");
        }
    }

    /** A method for retrieving all games from the database
     *
     * @return a list of all the games being played or which are available to play
     * @throws DataAccessException if it cannot be done
     */
    public List<GameData> findAll() throws DataAccessException {
        return new ArrayList<>(getAllGames().values());
    }

    /** ClaimSpot: A method/methods for claiming a spot in the game. The player's username is provided and should be saved as either the whitePlayer or blackPlayer in the database.
     *
     * @param username the username associated with the user trying to claim a team
     * @throws DataAccessException if data access fails
     */
    public void claimSpot(String username, String color, int gameID) throws DataAccessException {
        if(color == null) {
            return;
        } else if (color.equals("WHITE")) {
            if(getAllGames().get(gameID).getWhiteUsername() == null) {
                GameData game = getAllGames().get(gameID);
                game.setGameID(gameID);
                game.setWhiteUsername(username);
                Database db = new Database();
                try (Connection connection = db.getConnection()) {
                    String insertGameData = "UPDATE allGameData SET whiteUsername = ? WHERE id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertGameData)) {
                        preparedStatement.setString(1, game.getWhiteUsername());
                        preparedStatement.setInt(2, gameID);
                        preparedStatement.executeUpdate();
                    }
                } catch (DataAccessException | SQLException e) {
                    throw new DataAccessException("{ \"message\": \"Error: White username could not be updated in the database\" }");
                }
                allGames.put(gameID, game);
            } else {
                throw new DataAccessException("{ \"message\": \"Error: already taken\" }");
            }
        } else if (color.equals("BLACK")) {
            if(getAllGames().get(gameID).getBlackUsername() == null) {
                GameData game = getAllGames().get(gameID);
                game.setGameID(gameID);
                game.setBlackUsername(username);
                Database db = new Database();
                try (Connection connection = db.getConnection()) {
                    String insertGameData = "UPDATE allGameData SET blackUsername = ? WHERE id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertGameData)) {
                        preparedStatement.setString(1, game.getBlackUsername());
                        preparedStatement.setInt(2, gameID);
                        preparedStatement.executeUpdate();
                    }
                } catch (DataAccessException | SQLException e) {
                    throw new DataAccessException("{ \"message\": \"Error: Black username could not be updated in the database\" }");
                }
                allGames.put(gameID, game);
            } else {
                throw new DataAccessException("{ \"message\": \"Error: already taken\" }");
            }
        } else {
            throw new DataAccessException("{ \"message\": \"Error: already taken\" }");
        }
    }


    /** Clear: A method for clearing all games from the database
     *
     * @throws DataAccessException if data access fails
     */
    public void clear() throws DataAccessException{
        allGames.clear();
        if(getAllGames().size() != 0) {
            throw new DataAccessException("{ \"message\": \"Error: AuthTokens not cleared\" }");
        }
    }
}
