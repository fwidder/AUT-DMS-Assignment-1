/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.model;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class RequestMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4903842640515693924L;
	private int code;
	public static final int request = 0;
	public static final int denied = 1;
	public static final int acceptet = 2;
	public static final int malformed = 3;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
}
