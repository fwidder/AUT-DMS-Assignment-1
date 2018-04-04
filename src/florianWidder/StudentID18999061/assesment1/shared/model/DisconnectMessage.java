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

    public static int logoutNormal = 0;
    public static int logoutError = 1;

    /**
     *
     */
    private final static long serialVersionUID = 3243825538764447513L;

    private int reason = -1;

    /**
     * @param user
     */
    public DisconnectMessage(User user, int reason) {
	setSender(user);
	this.reason = reason;
    }

    public int getReason() {
	return reason;
    }

    public void setReason(int reason) {
	this.reason = reason;
    }

}
