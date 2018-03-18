/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.model;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class LogoutMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3243825538764447513L;
	private User user;

	/**
	 * @param user
	 */
	public LogoutMessage(User user) {
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

}
