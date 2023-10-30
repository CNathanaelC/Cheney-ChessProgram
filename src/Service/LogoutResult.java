package Service;

public class LogoutResult implements Result {
    /** Creates an instance of LogoutResult */
    LogoutResult() {

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
    public String toString(boolean functionStatus) {
        return null;
    }

}
