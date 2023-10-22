package Service;

/** The return of the service classes that indicates the success or failure of a specific operation using a responseCode and message
 *
 */
public interface Result {
    /** the code corresponding to the type of failure or success associated with the result */
    int responseCode = 0;
    /** the return message corresponding to the type of failure or success associated with the result
     *
     */
    String message = "";

    /** returns the response to an API function depending on success or the different failures in the following format:
     * [responseCode] {message}
     * @param functionStatus
     * @return
     */
    public String toString(boolean functionStatus);


}
