package Results;

public class RegisterResult {
    /** Creates an instance of RegisterResult and sets it up regarding the success or failure of a request */
    public RegisterResult() {

    }
    private String message = "";
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
     * @return
     */
    @Override
    public String toString() {
        return ("[" + responseCode + "]" + " " + message);
    }

}
