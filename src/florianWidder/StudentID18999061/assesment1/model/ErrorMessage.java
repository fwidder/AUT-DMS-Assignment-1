/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.model;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class ErrorMessage extends Message {

	private static final long serialVersionUID = -3209420011193382698L;
	public static final int userExists = 0;
	public static final int wrongDataType = 1;
	public static final int wrongRequestType = 2;
	public static final int userLimitReached = 3;
	private int errorCode;
	private static final String[] errorText = { "User already exists. Please chosse a different Username.",
			"Wrong Datatype submittet", "Wrong Requesttype",
			"Maximum number of useres reached. Please try again later." };

	/**
	 * @param errorCode
	 *            the errorCode
	 */
	public ErrorMessage(int errorCode) {
		this.setErrorCode(errorCode);
		this.setPayload(getErrorCode() + " - " + getErrorText());
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		this.setPayload(getErrorCode() + " - " + getErrorText());
	}

	/**
	 * @return the errorText
	 */
	public String getErrorText() {
		return errorText[errorCode];
	}

	@Override
	public String toString() {
		return super.toString() + " - " + getErrorCode() + ": " + getErrorText();
	}
}
