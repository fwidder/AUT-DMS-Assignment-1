/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.model;

import java.io.Serializable;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public abstract class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7395957707850011524L;
	private String payload;

	/**
	 * @param payload
	 */
	public Message() {
	}

	/**
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}
}
