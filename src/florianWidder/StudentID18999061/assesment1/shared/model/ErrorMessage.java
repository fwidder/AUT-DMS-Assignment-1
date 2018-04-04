/**
 *
 */
package florianWidder.StudentID18999061.assesment1.shared.model;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class ErrorMessage extends Message {

    private final static long serialVersionUID = -3209420011193382698L;
    /**
     * Error Code for "userExists"
     */
    public static int userExists = 0;
    /**
     * Error Code for "wrongDataType"
     */
    public static int wrongDataType = 1;
    /**
     * Error Code for "wrongRequestType"
     */
    public static int wrongRequestType = 2;
    /**
     * Error Code for "userLimitReached"
     */
    public static int userLimitReached = 3;
    private static String[] errorText = { "User already exists. Please chosse a different Username.",
	    "Wrong Datatype submittet", "Wrong Requesttype",
	    "Maximum number of useres reached. Please try again later." };
    private int errorCode = -1;

    /**
     * @param errorCode
     *            the errorCode
     */
    public ErrorMessage(int errorCode) {
	setErrorCode(errorCode);
	setPayload(getErrorCode() + " - " + getErrorText());
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
	return errorCode;
    }

    /**
     * @return the errorText
     */
    public String getErrorText() {
	return ErrorMessage.errorText[errorCode];
    }

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(int errorCode) {
	this.errorCode = errorCode;
	setPayload(getErrorCode() + " - " + getErrorText());
    }

    @Override
    public String toString() {
	return super.toString() + " - " + getErrorCode() + ": " + getErrorText();
    }
}
