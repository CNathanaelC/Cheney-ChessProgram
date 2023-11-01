package Service;

import Model.GameData;
import com.google.gson.annotations.SerializedName;

public class GamesListClass {
    public GamesListClass(GameData gd) {
        setGameID(gd.getGameID());
        setGameName(gd.getGameName());
        setBlackUsernameVal(gd.getBlackUsername());
        setWhiteUsernameVal(gd.getWhiteUsername());
    }
    @SerializedName("gameID")
    private int gameID;
    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    @SerializedName("whiteUsername")
    private String whiteUsernameVal;
    @SerializedName("blackUsername")
    private String blackUsernameVal;
    @SerializedName("gameName")
    private String gameName;

    public String getWhiteUsernameVal() {
        return whiteUsernameVal;
    }

    public void setWhiteUsernameVal(String whiteUsernameVal) {
        this.whiteUsernameVal = whiteUsernameVal;
    }

    public String getBlackUsernameVal() {
        return blackUsernameVal;
    }

    public void setBlackUsernameVal(String blackUsernameVal) {
        this.blackUsernameVal = blackUsernameVal;
    }
}
