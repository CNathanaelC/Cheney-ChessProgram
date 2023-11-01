package dataAccess;

import Model.GameData;
import chess.ChessGame;
import chess.Game;

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
        int bf = allGames.size();
        allGames.put(game.getGameID(), game);
        if(allGames.size() == bf) {
            throw new DataAccessException("{ \"message\": \"Error: unauthorized\" }");
        }
    }

    /** A method for retrieving a specified game from the database by gameID.
     *
     * @param gameID the number associated with the game to find
     * @throws DataAccessException if the access fails
     * @return the GameData for the game associated with the gameID
     */
    public GameData find(int gameID) throws DataAccessException {
        if(allGames.containsKey(gameID)) {
            return allGames.get(gameID);
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
        return new ArrayList<>(allGames.values());
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
            if(allGames.get(gameID).getWhiteUsername() == null) {
                GameData game = allGames.get(gameID);
                game.setGameID(gameID);
                game.setWhiteUsername(username);
                allGames.put(gameID, game);
            } else {
                throw new DataAccessException("{ \"message\": \"Error: already taken\" }");
            }
        } else if (color.equals("BLACK")) {
            if(allGames.get(gameID).getBlackUsername() == null) {
                GameData game = allGames.get(gameID);
                game.setGameID(gameID);
                game.setBlackUsername(username);
                allGames.put(gameID, game);
            } else {
                throw new DataAccessException("{ \"message\": \"Error: already taken\" }");
            }
        } else {
            throw new DataAccessException("{ \"message\": \"Error: already taken\" }");
        }
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
        allGames.clear();
//        if(allGames.size() != 0) {
//            throw new DataAccessException("{ \"message\": \"Error: AuthTokens not cleared\" }");
//        }
    }
}
