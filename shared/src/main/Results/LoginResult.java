package Results;

public class LoginResult {
    /** Creates an instance of LoginResult and sets it up regarding the success or failure of a request */
    public LoginResult() {

    }
    /** the message returned with the status code */
    private String message = "";
    /** the code that indicates the status: failure or success */
    private int responseCode = 0;
    private String authorization;
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
    public String getAuth() {
        return authorization;
    }
    public void setAuth(String auth) {
        authorization = auth;
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
