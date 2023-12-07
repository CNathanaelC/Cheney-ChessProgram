package webSocketMessages.userCommands;

public class Leave extends UserGameCommand{
    public Leave(String authToken) {
        super(authToken);
        this.commandType = CommandType.LEAVE;
    }
    private Integer gameID;

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}
