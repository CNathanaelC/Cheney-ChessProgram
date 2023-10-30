package Service;

public class ListGamesResult {
    /** Creates an instance of ListGamesResult and sets it up regarding the success or failure of a request */
    ListGamesResult() {

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
     * @param functionStatus
     * @return
     */
    /**
     * returns the response to an API function depending on success or the different failures in the following format:
     * [responseCode] {message}
     *
     * @return a string that follows the following format: [responseCode] {message}
     */
    public String toString() {
        return ("[" + responseCode + "]" + " " + message);
    }
}
