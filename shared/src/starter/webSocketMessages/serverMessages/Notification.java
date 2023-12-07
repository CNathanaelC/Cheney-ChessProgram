package webSocketMessages.serverMessages;

public class Notification extends ServerMessage {
    public Notification() {
        super(ServerMessageType.NOTIFICATION);
    }
    public String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
