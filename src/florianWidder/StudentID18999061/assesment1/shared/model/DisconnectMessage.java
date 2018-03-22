/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.shared.model;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class DisconnectMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3243825538764447513L;

	/**
	 * @param user
	 */
	public DisconnectMessage(User user) {
		this.setSender(user);
	}

}
