package dataAccess;

import Model.AuthToken;
import Model.GameData;
import Model.User;
import chess.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/** Class that contains all of the different Data Access objects */
public class DataAccess {
    /** Creates an instance of DataAccess */
    DataAccess() {

    }
    /** Stores all the current users of the server */
    private Map<String, User> allUsers = new HashMap<>();
    /** Stores all the current Authentication Tokens of the server */
    private Map<String, AuthToken> allAuths = new HashMap<>();
    /** Stores all the current games of the server */
    private Map<String, GameData> allGames = new HashMap<>();

    public Map<String, User> getAllUsers() {
        return allUsers;
    }

    public Map<String, AuthToken> getAllAuths() {
        return allAuths;
    }

    public Map<String, GameData> getAllGames() {
        return allGames;
    }

    /** Retrieves and stores data regarding AuthTokens
     *
     */
    public static abstract class AuthDAO implements IDAO{
        /** Creates an Instance of AuthDAO */
        AuthDAO() {

        }
        /** Create authorization code in the database
         *
         */
        void createAuth() {

        }

        /** Clear: A method for clearing all AuthTokens from the database
         *
         * @throws DataAccessException if data access fails
         */
        @Override
        public void clear() {

        }
    }
    /** Retrieves and stores data regarding Users
     *
     */
    public static abstract class UserDAO implements IDAO {
        /** Creates an Instance of UserDAO */
        UserDAO() {

        }

        /** A method for creating a new user in the database
         *
         * @param user
         */
        public void createUser(User user) {

        }



        /** Clear: A method for clearing all users from the database
         *
         * @throws DataAccessException if data access fails
         */
        @Override
        public void clear() {

        }
    }
    /** Retrieves and stores data regarding Games
     *
     */
    public static abstract class GameDAO implements IDAO {
        /** Creates an Instance of GameDAO*/
        GameDAO() {

        }

        /** A method for inserting a new game into the database.
         *
         * @param game the game that is to be inserted into the database
         */
        @Override
        public void insert(Game game) throws DataAccessException {

        }

        /** A method for retrieving a specified game from the database by gameID.
         *
         * @param gameID the number associated with the game to find
         * @throws DataAccessException if the access fails
         */
        @Override
        public void find(int gameID) throws DataAccessException {

        }

        /** A method for retrieving all games from the database
         *
         * @return a list of all the games being played or which are available to play
         * @throws DataAccessException
         */
        @Override
        public List<Game> findAll() throws DataAccessException {
            return null;
        }

        /** ClaimSpot: A method/methods for claiming a spot in the game. The player's username is provided and should be saved as either the whitePlayer or blackPlayer in the database.
         *
         * @param username the username associated with the user trying to claim a team
         * @throws DataAccessException if data access fails
         */
        @Override
        public void claimSpot(String username) throws DataAccessException {

        }

        /** UpdateGame: A method for updating a chessGame in the database. It should replace the chessGame string corresponding to a given gameID with a new chessGame string.
         *
         * @param gameID the number associated with the game that is to be updated
         * @throws DataAccessException if data access fails
         */
        @Override
        public void updateGame(int gameID) throws DataAccessException {

        }

        /** Remove: A method for removing a game from the database
         *
         * @param gameID the number associated with the game you are trying to remove
         */
        @Override
        public void remove(int gameID) {

        }

        /** Clear: A method for clearing all games from the database
         *
         * @throws DataAccessException if data access fails
         */
        @Override
        public void clear() {

        }
    }
}
