package webSocketMessages.userCommands;

public class Resign extends UserGameCommand {
    public Resign(String authToken) {
        super(authToken);
        this.commandType = CommandType.RESIGN;
    }
    private Integer gameID;

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}
