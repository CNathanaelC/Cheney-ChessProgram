package Service;

public class JoinGameResult implements Result {
    /** Creates an instance of JoinGameResult and sets it up regarding the success or failure of a request */
    JoinGameResult(Request request, boolean result) {

    }
    private String message = "";
    private int responseCode = 0;
    public String getMessage() {
        return message;
    }
    public void setMessage(String m) {
        message = m;
    }
    public int getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(int code) {
        responseCode = code;
    }
    /**
     * returns the response to an API function depending on success or the different failures in the following format:
     * [responseCode] {message}
     *
     * @return
     */
    public String toString() {
        return ("[" + responseCode + "]" + " " + message);
    }

}
