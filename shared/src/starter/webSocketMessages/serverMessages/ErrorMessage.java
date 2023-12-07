package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage {
    public ErrorMessage() {
        super(ServerMessageType.ERROR);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorMessage;
}
