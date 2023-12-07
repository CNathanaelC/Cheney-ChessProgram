package webSocketMessages.userCommands;

import chess.Move;

public class MakeMove extends UserGameCommand{
    public MakeMove(String authToken) {
        super(authToken);
        this.commandType = CommandType.MAKE_MOVE;
    }
    private Integer gameID;
    private Move move;

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}
