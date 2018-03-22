/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.shared.model;

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
	private User sender;

	/**
	 */
	public Message() {
	}

	/**
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}

	public User getSender() {
		return sender;
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}
}
