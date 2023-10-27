package dataAccess;

import Model.GameData;
import chess.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Retrieves and stores data regarding Games
 *
 */
public class GameDAO {
    /** Stores all the current games of the server */
    private static Map<String, GameData> allGames = new HashMap<>();

    public Map<String, GameData> getAllGames() {
        return allGames;
    }
    /** Creates an Instance of GameDAO*/
    public GameDAO() {

    }

    /** A method for inserting a new game into the database.
     *
     * @param game the game that is to be inserted into the database
     */
    public void insert(Game game) throws DataAccessException {

    }

    /** A method for retrieving a specified game from the database by gameID.
     *
     * @param gameID the number associated with the game to find
     * @throws DataAccessException if the access fails
     */
    public void find(int gameID) throws DataAccessException {

    }

    /** A method for retrieving all games from the database
     *
     * @return a list of all the games being played or which are available to play
     * @throws DataAccessException
     */
    public List<Game> findAll() throws DataAccessException {
        return null;
    }

    /** ClaimSpot: A method/methods for claiming a spot in the game. The player's username is provided and should be saved as either the whitePlayer or blackPlayer in the database.
     *
     * @param username the username associated with the user trying to claim a team
     * @throws DataAccessException if data access fails
     */
    public void claimSpot(String username) throws DataAccessException {

    }

    /** UpdateGame: A method for updating a chessGame in the database. It should replace the chessGame string corresponding to a given gameID with a new chessGame string.
     *
     * @param gameID the number associated with the game that is to be updated
     * @throws DataAccessException if data access fails
     */
    public void updateGame(int gameID) throws DataAccessException {

    }

    /** Remove: A method for removing a game from the database
     *
     * @param gameID the number associated with the game you are trying to remove
     */
    public void remove(int gameID) throws DataAccessException {

    }

    /** Clear: A method for clearing all games from the database
     *
     * @throws DataAccessException if data access fails
     */
    public void clear() throws DataAccessException{

    }
}
