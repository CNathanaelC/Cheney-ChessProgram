package Model;

import chess.Game;
/** Model class to contain elements of a Game
 *
 */
public class GameData {
    /** Creates an instance of GameData to be adapted to a game session */
    public GameData(int id) {
        setGameID(id);
    }
    /** the integer representation unique to the game session */
    private int gameID;
    /** the string representation of the user that has claimed the white team */
    private String whiteUsername;
    /** the string representation of the user that has claimed the black team */
    private String blackUsername;
    /** the string representation of the game session */
    private String gameName;
    /** the actual game with its associated board, pieces, moves, and status(ongoing, checkmate, or stalemate) */
    private Game game;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
