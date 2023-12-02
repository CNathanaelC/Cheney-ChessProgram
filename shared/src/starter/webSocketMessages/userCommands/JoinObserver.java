package webSocketMessages.userCommands;

import Model.User;

public class JoinObserver extends UserGameCommand {
    public JoinObserver(String authToken) {
        super(authToken);
    }
    public Integer gameID;
}
