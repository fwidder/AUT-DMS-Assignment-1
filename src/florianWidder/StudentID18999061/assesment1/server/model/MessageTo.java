/**
 * 
 */
package florianWidder.StudentID18999061.assesment1.server.model;

/**
 * @author Florian Widder
 * @author Student ID 18999061
 *
 */
public class MessageTo extends Message {
	private User receiver;

	public MessageTo(User receiver) {
		super();
		this.receiver = receiver;
	}

	public MessageTo(User receiver, String payload) {
		super(payload);
		this.receiver = receiver;
	}

	/**
	 * @return the receiver
	 */
	public User getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver
	 *            the receiver to set
	 */
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
}
