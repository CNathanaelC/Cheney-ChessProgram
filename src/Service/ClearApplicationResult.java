package Service;

public class ClearApplicationResult {
    /** Creates an instance of ClearApplicationResult */
    ClearApplicationResult() {

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
     * @return a string that follows the following format: [responseCode] {message}
     */
    public String toString() {
        return ("[" + responseCode + "]" + " " + message);
    }

}
