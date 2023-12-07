package webSocketMessages.serverMessages;

import chess.Game;

public class LoadGame extends ServerMessage {
    public LoadGame() {
        super(ServerMessageType.LOAD_GAME);
    }
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
