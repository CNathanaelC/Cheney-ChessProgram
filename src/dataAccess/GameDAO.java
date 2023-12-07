package dataAccess;

import Model.GameData;
import chess.Game;
import com.google.gson.Gson;

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
//    private static Map<Integer, GameData> allGames = new HashMap<>();
    public Map<Integer, GameData> getAllGames() {
        Database db = new Database();
        Map<Integer, GameData> games = new HashMap<>();
        try (Connection connection = db.getConnection()) {
            String selectGameData = "SELECT * FROM allGameData";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectGameData)) {
                while (resultSet.next()) {
                    /*id INT NOT NULL,
                    name VARCHAR(255) NOT NULL,
                    blackUsername VARCHAR(255),
                    whiteUsername VARCHAR(255),
                    game JSON;
                    PRIMARY KEY (id)*/
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String blackUsername = resultSet.getString("blackUsername");
                    String whiteUsername = resultSet.getString("whiteUsername");
                    String json = resultSet.getString("game");
                    Gson gson = new Gson();
                    Game game = gson.fromJson(json, Game.class);
                    GameData gameData = new GameData(id);
                    gameData.setGameName(name);
                    gameData.setGame(game);
                    gameData.setWhiteUsername(whiteUsername);
                    gameData.setBlackUsername(blackUsername);
                    games.put(id, gameData);
                }
            }
            db.closeConnection(connection);
        } catch (DataAccessException | SQLException e) {
//            throw new DataAccessException("{ \"message\": \"Error: user could not be inserted into the database\" }");
        }
        return games;
//        return allGames;
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
                game.setGame(new Game());
                Gson gson = new Gson();
                String json = gson.toJson(game.getGame());
                preparedStatement.setString(5, json);
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException("{ \"message\": \"Error: game data could not be inserted into the database\" }");
        }
//        allGames.put(game.getGameID(), game);
        if(getAllGames().size() == bf) {
            throw new DataAccessException("{ \"message\": \"Error: unauthorized\" }");
        }
    }
    public void update(int gameID, Game game) throws DataAccessException{
        if(!getAllGames().containsKey(gameID)) {
            throw new DataAccessException("{ \"message\": \"Error: provided gameID does not exist\" }");
        }
        Database db = new Database();
        try (Connection connection = db.getConnection()) {
            String updateGameData = "UPDATE allGameData SET game = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateGameData)) {
                Gson gson = new Gson();
                String json = gson.toJson(game);
                preparedStatement.setString(1, json);
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
    public void remove(int gameID) throws DataAccessException {
        if(getAllGames().containsKey(gameID)) {
            Database db = new Database();
            try (Connection connection = db.getConnection()) {
                String deleteGameData = "DELETE FROM allGameData WHERE gameID = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteGameData)) {
                    preparedStatement.setInt(1, gameID);
                    preparedStatement.executeUpdate();
                }
                db.closeConnection(connection);
            } catch (DataAccessException | SQLException e) {
                throw new DataAccessException("{ \"message\": \"Error: authTokens could not be cleared from the database\" }");
            }
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
//                GameData game = getAllGames().get(gameID);
//                game.setGameID(gameID);
//                game.setWhiteUsername(username);
                Database db = new Database();
                try (Connection connection = db.getConnection()) {
                    String updateGameData = "UPDATE allGameData SET whiteUsername = ? WHERE id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateGameData)) {
                        preparedStatement.setString(1, username);
                        preparedStatement.setInt(2, gameID);
                        preparedStatement.executeUpdate();
                    }
                    db.closeConnection(connection);
                } catch (DataAccessException | SQLException e) {
                    throw new DataAccessException("{ \"message\": \"Error: White username could not be updated in the database\" }");
                }
//                allGames.put(gameID, game);
            } else {
                throw new DataAccessException("{ \"message\": \"Error: already taken\" }");
            }
        } else if (color.equals("BLACK")) {
            if(getAllGames().get(gameID).getBlackUsername() == null) {
//                GameData game = getAllGames().get(gameID);
//                game.setGameID(gameID);
//                game.setBlackUsername(username);
                Database db = new Database();
                try (Connection connection = db.getConnection()) {
                    String updateGameData = "UPDATE allGameData SET blackUsername = ? WHERE id = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateGameData)) {
                        preparedStatement.setString(1, username);
                        preparedStatement.setInt(2, gameID);
                        preparedStatement.executeUpdate();
                    }
                    db.closeConnection(connection);
                } catch (DataAccessException | SQLException e) {
                    throw new DataAccessException("{ \"message\": \"Error: Black username could not be updated in the database\" }");
                }
//                allGames.put(gameID, game);
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
        Database db = new Database();
        try (Connection connection = db.getConnection()) {
            String deleteGameData = "DELETE FROM allGameData";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteGameData)) {
                preparedStatement.executeUpdate();
            }
            db.closeConnection(connection);
        } catch (DataAccessException | SQLException e) {
            throw new DataAccessException("{ \"message\": \"Error: GameData could not be cleared from the database\" }");
        }
//        allGames.clear();
        if(getAllGames().size() != 0) {
            throw new DataAccessException("{ \"message\": \"Error: GameData not cleared\" }");
        }
    }
}
