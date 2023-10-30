package Service;


import com.google.gson.annotations.SerializedName;

/** The parameter of the service classes that provides the parameters to perform it
 *
 */
public class LoginRequest {
    @SerializedName("username")
    /** the proposed username */
    private String username;
    @SerializedName("password")
    /** the proposed password */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}