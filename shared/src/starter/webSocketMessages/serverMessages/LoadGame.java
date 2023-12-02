package webSocketMessages.serverMessages;

import chess.Game;

public class LoadGame extends ServerMessage {
    public LoadGame(ServerMessageType type) {
        super(type);
    }
    public Game game;
}
