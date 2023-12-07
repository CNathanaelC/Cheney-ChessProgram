package webSocketMessages.userCommands;

import Model.User;

public class JoinObserver extends UserGameCommand {
    public JoinObserver(String authToken) {
        super(authToken);
        this.commandType = CommandType.JOIN_OBSERVER;
    }
    private Integer gameID;

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }
}
