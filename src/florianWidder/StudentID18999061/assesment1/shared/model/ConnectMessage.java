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
    private final static long serialVersionUID = 5610444901054850422L;
    /**
     * Return code for "loginRequest"
     */
    public static int loginRequest = 0;
    /**
     * Return code for "loginAccept"
     */
    public static int loginAccept = 1;
    /**
     * Return code for "loginDenied"
     */
    public static int loginDenied = 2;
    /**
     * Return code for "loginBroadcast"
     */
    public static int loginBroadcast = 3;
    private int code = -1;

    /**
     * @param user
     * @param code
     */
    public ConnectMessage(User user, int code) {
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
    public void setCode(int code) {
	this.code = code;
    }

}
