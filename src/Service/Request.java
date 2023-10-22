package Service;

import Model.AuthToken;

import java.util.ArrayList;
import java.util.List;

/** The parameter of the service classes that indicates the specific operation desired as well as the parameters and AuthToken needed to perform it
 *
 */
public class Request {
    /** the parameters needed to execute the associated request */
    List<Object> parameters = new ArrayList();
    /** the authToken needed to execute the associated request for a user */
    AuthToken authToken = null;

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
