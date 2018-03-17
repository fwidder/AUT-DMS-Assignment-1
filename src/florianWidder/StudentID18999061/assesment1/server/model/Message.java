/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.server.model;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public abstract class Message {
	private String payload;

	public Message() {
		payload = "";
	}

	public Message(String payload) {
		this.payload = payload;
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
