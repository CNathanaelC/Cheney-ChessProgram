package Service;

public class ClearApplicationResult implements Result {
    /** Creates an instance of ClearApplicationResult and sets it up regarding the success or failure of a request */
    ClearApplicationResult(Request request, boolean result) {

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
    @Override
    public String toString(boolean functionStatus) {
        return null;
    }

}
