package dataAccess;


import chess.Game;

import javax.xml.crypto.Data;
import java.util.List;

/** provides a baseline of what DAO implementation might contain meant to be implemented by abstract classes
 *
 */
public interface IDAO {
    /** A method for inserting a new game into the database.
     *
     * @param game the game that is to be inserted into the database
     */
    void insert(Game game) throws DataAccessException;

    /** A method for retrieving a specified game from the database by gameID.
     *
     * @param gameID the number associated with the game to find
     * @throws DataAccessException if the access fails
     */
    void find (int gameID) throws DataAccessException;

    /** A method for retrieving all games from the database
     *
     * @return a list of all the games being played or which are available to play
     * @throws DataAccessException
     */
    List<Game> findAll() throws DataAccessException;

    /** ClaimSpot: A method/methods for claiming a spot in the game. The player's username is provided and should be saved as either the whitePlayer or blackPlayer in the database.
     *
     * @param username the username associated with the user trying to claim a team
     * @throws DataAccessException if data access fails
     */
    void claimSpot(String username) throws DataAccessException;

    /** UpdateGame: A method for updating a chessGame in the database. It should replace the chessGame string corresponding to a given gameID with a new chessGame string.
     *
     * @param gameID the number associated with the game that is to be updated
     * @throws DataAccessException if data access fails
     */
    void updateGame(int gameID) throws DataAccessException;

    /** Remove: A method for removing a game from the database
     *
     * @param gameID the number associated with the game you are trying to remove
     */
    void remove(int gameID);

    /** Clear: A method for clearing all data from the database
     *
     * @throws DataAccessException if data access fails
     */
    void clear() throws DataAccessException;
}
