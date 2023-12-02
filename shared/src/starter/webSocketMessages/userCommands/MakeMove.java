package webSocketMessages.userCommands;

import chess.Move;

public class MakeMove extends UserGameCommand{
    public MakeMove(String authToken) {
        super(authToken);
    }
    public Integer gameID;
    public Move move;
}
