/**
 *
 */
package florianWidder.StudentID18999061.assesment1.shared.model;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class ConnectMessage extends Message {
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
    /**
     * Return code for "loginBroadcast"
     */
    public static final int loginBroadcast = 3;
    private int code = -1;

    /**
     * @param user
     * @param code
     */
    public ConnectMessage(final User user, final int code) {
	setSender(user);
	setCode(code);
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
    public void setCode(final int code) {
	this.code = code;
    }

}
