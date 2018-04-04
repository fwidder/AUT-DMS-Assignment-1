/**
 *
 */
package florianWidder.StudentID18999061.assesment1.shared.model;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class RequestMessage extends Message {
    /**
     *
     */
    private final static long serialVersionUID = 4903842640515693924L;
    public static int request = 0;
    public static int denied = 1;
    public static int acceptet = 2;
    public static int malformed = 3;
    private int code = -1;

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
