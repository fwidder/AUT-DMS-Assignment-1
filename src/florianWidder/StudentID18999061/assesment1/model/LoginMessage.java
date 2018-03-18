/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.model;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class LoginMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5610444901054850422L;
	/**
	 * Return code for "loginRequest"
	 */
	public static final int loginRequest = 0;
	/**
	 * Return code for "loginAccept"
	 */
	public static final int loginAccept = 1;
	/**
	 * Return code for "loginDenied"
	 */
	public static final int loginDenied = 2;
	private User user;
	private int code;

	/**
	 * @param user
	 * @param code
	 */
	public LoginMessage(User user, int code) {
		this.setUser(user);
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

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
